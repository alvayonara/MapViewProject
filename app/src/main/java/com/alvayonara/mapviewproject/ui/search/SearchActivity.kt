package com.alvayonara.mapviewproject.ui.search

import android.app.Activity
import android.text.TextUtils
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvayonara.mapviewproject.MyApplication
import com.alvayonara.mapviewproject.core.base.BaseActivity
import com.alvayonara.mapviewproject.core.data.source.remote.network.Result.Status.*
import com.alvayonara.mapviewproject.core.ui.SearchAdapter
import com.alvayonara.mapviewproject.core.ui.ViewModelFactory
import com.alvayonara.mapviewproject.core.utils.ConstUiState.UI_STATE_SEARCH_ERROR
import com.alvayonara.mapviewproject.core.utils.ConstUiState.UI_STATE_SEARCH_INITIAL
import com.alvayonara.mapviewproject.core.utils.ConstUiState.UI_STATE_SEARCH_LOADING
import com.alvayonara.mapviewproject.core.utils.ConstUiState.UI_STATE_SEARCH_NOT_FOUND
import com.alvayonara.mapviewproject.core.utils.ConstUiState.UI_STATE_SEARCH_SUCCESS
import com.alvayonara.mapviewproject.core.utils.EditTextStream
import com.alvayonara.mapviewproject.databinding.ActivitySearchBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val searchViewModel: SearchViewModel by viewModels {
        factory
    }

    private lateinit var searchAdapter: SearchAdapter
    private var search: String = ""

    companion object {
        const val EXTRA_SEARCH_RESULT = "extra_search_result"
    }

    override val bindingInflater: (LayoutInflater) -> ActivitySearchBinding
        get() = ActivitySearchBinding::inflate

    override fun loadInjector() = (application as MyApplication).appComponent.inject(this)

    override fun setup() {
        setupView()
        setupRecyclerView()
        subscribeViewModel()
    }

    override fun setupView() {
        binding.btnCloseSearch.setOnClickListener {
            onBackPressed()
        }

        binding.viewSearchError.btnRetry.setOnClickListener {
            searchViewModel.setSelectedSearch(search)
        }

        binding.edtSearchAddress.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || keyEvent.action == KeyEvent.ACTION_DOWN
                || keyEvent.action == KeyEvent.KEYCODE_ENTER
            ) {
                hideKeyboard(this)
                return@OnEditorActionListener true
            }
            false
        })

        val searchStream = object : EditTextStream() {
            override fun onChanged(text: String) {
                if (!TextUtils.isEmpty(text)) {
                    lifecycleScope.launch {
                        search = text
                        searchViewModel.setSelectedSearch(text)
                    }
                } else {
                    binding.viewFlipperSearchAddress.displayedChild = UI_STATE_SEARCH_INITIAL
                }
            }
        }

        binding.edtSearchAddress.addTextChangedListener(searchStream)
    }

    override fun setupRecyclerView() {
        searchAdapter = SearchAdapter().apply {
            onItemClick = { data ->
                intent.putExtra(EXTRA_SEARCH_RESULT, data.place_id)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

        with(binding.rvSearchAddress) {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            setHasFixedSize(true)
            adapter = searchAdapter
        }
    }

    override fun subscribeViewModel() {
        searchViewModel.searchResult.onLiveDataResult {
            binding.viewFlipperSearchAddress.displayedChild = when (it.status) {
                LOADING -> UI_STATE_SEARCH_LOADING
                SUCCESS -> {
                    val data = it.body
                    searchAdapter.submitList(data)

                    // Set state list.
                    if (data?.isNotEmpty()!!) UI_STATE_SEARCH_SUCCESS else UI_STATE_SEARCH_NOT_FOUND
                }
                ERROR -> UI_STATE_SEARCH_ERROR
            }
        }
    }

    override fun releaseData() {
        binding.rvSearchAddress.adapter = null
    }
}