package com.alvayonara.mapviewproject.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alvayonara.mapviewproject.domain.usecase.MapViewUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
class SearchViewModel @Inject constructor(private val mapViewUseCase: MapViewUseCase): ViewModel() {

    private val queryChannel = ConflatedBroadcastChannel<String>()

    fun setSelectedSearch(search: String) {
        queryChannel.offer(search)
    }

    @FlowPreview
    val searchResult = queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest {
            mapViewUseCase.getAutocomplete(it, "")
        }
        .asLiveData()
}