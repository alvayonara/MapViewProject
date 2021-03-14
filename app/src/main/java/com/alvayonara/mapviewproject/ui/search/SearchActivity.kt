package com.alvayonara.mapviewproject.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.alvayonara.mapviewproject.MyApplication
import com.alvayonara.mapviewproject.R
import com.alvayonara.mapviewproject.core.base.BaseActivity
import com.alvayonara.mapviewproject.core.data.source.remote.network.Result
import com.alvayonara.mapviewproject.core.ui.ViewModelFactory
import com.alvayonara.mapviewproject.databinding.ActivitySearchBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: SearchViewModel by viewModels {
        factory
    }

    override val bindingInflater: (LayoutInflater) -> ActivitySearchBinding
        get() = ActivitySearchBinding::inflate

    override fun loadInjector() {
        (application as MyApplication).appComponent.inject(this)
    }

    override fun setup() {
        binding.edPlace.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s.toString().let {
                    viewModel.setSearchQuery(it)
                }
            }
        })

        viewModel.setMovieCategory("ChIJJU30a47zaS4R4B4EXFWW8Uw")
        viewModel.getMovie.observe(this, {
            when(it.status) {
                Result.Status.LOADING -> {}
                Result.Status.SUCCESS -> {
                }
                Result.Status.ERROR -> {}
            }
        })

        viewModel.searchResult.observe(this, {
            when(it.status) {
                Result.Status.LOADING -> {}
                Result.Status.SUCCESS -> {
                }
                Result.Status.ERROR -> {}
            }
        })
    }
}