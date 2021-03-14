package com.alvayonara.mapviewproject.ui.search

import androidx.lifecycle.*
import com.alvayonara.mapviewproject.domain.usecase.MapViewUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.switchMap
import javax.inject.Inject

@ExperimentalCoroutinesApi
class SearchViewModel @Inject constructor(private val mapViewUseCase: MapViewUseCase): ViewModel() {

    private val queryChannel = ConflatedBroadcastChannel<String>()

    fun setSearchQuery(search: String) {
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

    val geocode = liveData {
        emitSource(mapViewUseCase.getGeocode("-6.1228457", "106.1539273").asLiveData())
    }

    private val movieCategory = MutableLiveData<String>()

    fun setMovieCategory(movieCategory: String) {
        this.movieCategory.value = movieCategory
    }

    val getMovie = movieCategory.switchMap {
        liveData {
            emitSource(mapViewUseCase.getDetails(it).asLiveData())
        }
    }
}