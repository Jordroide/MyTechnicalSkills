package com.jordroid.showcase.quote.anime.presenter.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordroid.showcase.quote.anime.domain.model.QuoteAnimeEntity
import com.jordroid.showcase.quote.anime.domain.usecase.QuoteAnimeUseCase
import com.jordroid.showcase.quote.anime.presenter.model.QuoteAnimeItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class QuoteAnimeViewModel(
    private val quoteAnimeUseCase: QuoteAnimeUseCase
) : ViewModel() {

    fun getQuoteAnimeName() = quoteAnimeUseCase.readAll().map { list ->
        list.map { item ->
            item.toUi()
        }
    }

    fun fetchAnimeData() {
        viewModelScope.launch(Dispatchers.IO) {
            quoteAnimeUseCase.fetch()
        }
    }

    private fun QuoteAnimeEntity.toUi() = QuoteAnimeItem(
        animeName = animeName
    )
}
