package com.jordroid.showcase.browsing.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordroid.showcase.browsing.domain.model.AnimeEntity
import com.jordroid.showcase.browsing.domain.usecase.FetchAnimeListUseCase
import com.jordroid.showcase.browsing.domain.usecase.GetAnimeListUseCase
import com.jordroid.showcase.browsing.presenter.model.AnimeUi
import com.jordroid.showcase.browsing.presenter.model.AnimeUi.AnimeHeaderUi
import com.jordroid.showcase.browsing.presenter.model.AnimeUi.AnimeItemUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class BrowsingAnimeViewModel(
    private val getAnimeListUseCase: GetAnimeListUseCase,
    private val fetchAnimeListUseCase: FetchAnimeListUseCase,
) : ViewModel() {

    fun getQuoteAnimeName(): Flow<List<AnimeUi>> =
        getAnimeListUseCase.readAll().flowOn(Dispatchers.IO).map { list ->
            list.toUi()
        }

    fun fetchAnimeData() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchAnimeListUseCase.fetchAnimeList()
        }
    }
}

private fun AnimeEntity.toUi() = AnimeItemUi(
    animeName = animeName
)

private fun List<AnimeEntity>.toUi(): List<AnimeUi> {
    val result = mutableListOf<AnimeUi>()
    asSequence().map {
        it.toUi()
    }.groupBy {
        it.label[0]
    }.toList().forEach { (firstLetter, animeList) ->
        result.add(AnimeHeaderUi(title = "$firstLetter", amount = animeList.size))
        result.addAll(animeList)
    }
    return result
}
