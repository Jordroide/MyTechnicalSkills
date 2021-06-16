package com.jordroid.showcase.quote.all.presenter.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordroid.showcase.quote.all.domain.model.QuoteEntity
import com.jordroid.showcase.quote.all.domain.usecase.QuoteUseCase
import com.jordroid.showcase.quote.all.presenter.model.QuoteStatistic
import com.jordroid.showcase.quote.all.presenter.model.QuoteUi
import com.jordroid.showcase.quote.all.presenter.model.QuoteUi.QuoteHeaderUi
import com.jordroid.showcase.quote.all.presenter.model.QuoteUi.QuoteItemUi
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
    val quoteStatistic: SharedFlow<QuoteStatistic> = _quoteStatistic

    fun getQuote(): Flow<List<QuoteUi>> {
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

    private fun QuoteEntity.toUi() = QuoteItemUi(
        anime = anime,
        character = character,
        quote = quote
    )

    private fun List<QuoteEntity>.toUi(): List<QuoteUi> {
        val result = mutableListOf<QuoteUi>()
        map {
            it.toUi()
        }.groupBy {
            it.label
        }.forEach { (anime, items) ->
            result.add(QuoteHeaderUi(anime))
            result.addAll(items)
        }
        return result
    }
}