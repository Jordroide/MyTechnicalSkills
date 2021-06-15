package com.jordroid.showcase.quote.presenter.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordroid.showcase.quote.domain.model.QuoteEntity
import com.jordroid.showcase.quote.domain.usecase.QuoteUseCase
import com.jordroid.showcase.quote.presenter.model.QuoteItem
import com.jordroid.showcase.quote.presenter.model.QuoteItemHeader
import com.jordroid.showcase.quote.presenter.model.QuoteGenericItem
import com.jordroid.showcase.quote.presenter.model.QuoteStatistic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class QuoteViewModel(
    private val quoteUseCase: QuoteUseCase
) : ViewModel() {

    private val _quoteStatistic = MutableSharedFlow<QuoteStatistic>()
    val quoteStatistic : SharedFlow<QuoteStatistic> = _quoteStatistic

    fun getQuote(): Flow<List<QuoteGenericItem>> {
        return quoteUseCase.readAll().map { list ->
            _quoteStatistic.emit(QuoteStatistic(list.size, list.distinctBy { it.anime }.size))
            list.toUi()
        }
    }

    fun searchWith(textValue: String) {
        viewModelScope.launch(Dispatchers.IO) {
            quoteUseCase.searchWith(textValue)
        }
    }

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            quoteUseCase.fetch()
        }
    }

    private fun QuoteEntity.toUi() = QuoteItem(
        anime = anime,
        character = character,
        quote = quote
    )

    private fun List<QuoteEntity>.toUi(): List<QuoteGenericItem> {
        val result = mutableListOf<QuoteGenericItem>()
        map {
            it.toUi()
        }.groupBy {
            it.anime
        }.forEach { (anime, items) ->
            result.add(QuoteItemHeader(anime))
            result.addAll(items)
        }
        return result
    }
}