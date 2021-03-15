package com.alvayonara.mapviewproject.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.alvayonara.mapviewproject.MyApplication
import com.alvayonara.mapviewproject.R
import com.alvayonara.mapviewproject.core.base.BaseActivity
import com.alvayonara.mapviewproject.core.data.source.remote.network.Result.Status.*
import com.alvayonara.mapviewproject.core.data.source.remote.response.ResultDetails
import com.alvayonara.mapviewproject.core.data.source.remote.response.ResultsItem
import com.alvayonara.mapviewproject.core.ui.ViewModelFactory
import com.alvayonara.mapviewproject.core.utils.gone
import com.alvayonara.mapviewproject.core.utils.visible
import com.alvayonara.mapviewproject.databinding.ActivityMapsBinding
import com.alvayonara.mapviewproject.ui.search.SearchActivity
import com.alvayonara.mapviewproject.ui.search.SearchActivity.Companion.EXTRA_SEARCH_RESULT
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class MapsActivity : BaseActivity<ActivityMapsBinding>(), OnMapReadyCallback,
    GoogleMap.OnMapClickListener {

    @Inject
    lateinit var factory: ViewModelFactory
    private val mapsViewModel: MapsViewModel by viewModels {
        factory
    }

    var markerPoints: ArrayList<LatLng> = ArrayList()

    // Data from search address result.
    private var placeId: String? = null
    private var map: GoogleMap? = null

    private lateinit var mapFragment: SupportMapFragment
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationPermissionGranted = false

    // A default location in Jakarta when location permission is not granted.
    private val defaultLocation = LatLng(-6.229728, 106.6894312)
    private var lastKnownLocation: Location? = null

    companion object {
        private const val DEFAULT_ZOOM = 15
    }

    override val bindingInflater: (LayoutInflater) -> ActivityMapsBinding
        get() = ActivityMapsBinding::inflate

    override fun loadInjector() = (application as MyApplication).appComponent.inject(this)

    override fun setup() {
        setupView()
        subscribeViewModel()
    }

    override fun setupView() {
        binding.btnSearch.setOnClickListener {
            startForResult.launch(
                Intent(this, SearchActivity::class.java)
            )
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun subscribeViewModel() {
        mapsViewModel.getGeocode.onLiveDataResult {
            when (it.status) {
                LOADING -> binding.addressDetail.gone()
                SUCCESS -> populateAddressFromGeocode(it.body)
                ERROR -> binding.addressDetail.gone()
            }
        }

        mapsViewModel.getDetail.onLiveDataResult {
            when (it.status) {
                LOADING -> binding.addressDetail.gone()
                SUCCESS -> populateMapFromSearchAddress(it.body)
                ERROR -> binding.addressDetail.gone()
            }
        }
    }

    private fun populateAddressFromGeocode(data: List<ResultsItem?>?) {
        data?.get(0)?.let {
            binding.tvSearchMainAddress.text = it.formatted_address
            binding.addressDetail.visible()
        }
    }

    private fun populateMapFromSearchAddress(data: ResultDetails?) {
        data?.let {
            val latLng = LatLng(
                it.geometry?.location?.lat ?: 0.0,
                it.geometry?.location?.lng ?: 0.0
            )
            setMarker(latLng)
            setMoveCamera(latLng)

            binding.tvSearchMainAddress.text = it.formatted_address
            binding.addressDetail.visible()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.map = googleMap
        updateLocationUI()
        if (placeId == "") getDeviceLocation()
        map?.setOnMapClickListener(this)
    }

    override fun onMapClick(p0: LatLng) {
        setMarker(p0)
        mapsViewModel.setSelectedLatLng(p0)
    }

    @SuppressLint("MissingPermission")
    private fun requestPermission() {
        requestPermissions(listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ),
            action = {
                locationPermissionGranted = true
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
                initViewMyLocation()
            }, actionDeny = {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                showDenyPermission()
            })
    }

    private fun initViewMyLocation() {
        val locationButton =
            (mapFragment.view?.findViewById<View>(Integer.parseInt("1"))?.parent as View).findViewById<View>(
                Integer.parseInt("2")
            )

        (locationButton.layoutParams as RelativeLayout.LayoutParams).apply {
            addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
            addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
            setMargins(0, 0, 30, 600)
        }
    }

    private fun updateLocationUI() {
        if (map == null) return
        requestPermission()
    }

    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            LatLng(
                                lastKnownLocation!!.latitude,
                                lastKnownLocation!!.longitude
                            ).let {
                                setMarker(it)
                                setMoveCamera(it)
                                mapsViewModel.setSelectedLatLng(it)
                            }
                        }
                    } else {
                        setMoveCamera(defaultLocation)
                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            setLog("Exception: ${e.message}")
        }
    }

    private fun setMarker(latLng: LatLng) {
        if (markerPoints.size > 0) {
            markerPoints.clear()
            map?.clear()
        }

        markerPoints.add(latLng)
        map?.addMarker(
            MarkerOptions()
                .position(latLng)
        )
    }

    private fun setMoveCamera(latLng: LatLng) {
        map?.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(latLng, DEFAULT_ZOOM.toFloat())
        )
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                placeId = intent?.getStringExtra(EXTRA_SEARCH_RESULT)!!

                if (placeId != "") mapsViewModel.setSelectedPlaceId(placeId.orEmpty())
            }
        }
}