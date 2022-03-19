package com.jordroid.showcase.quotes.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordroid.showcase.quotes.domain.model.QuoteEntity
import com.jordroid.showcase.quotes.domain.usecase.FetchNewQuoteUseCase
import com.jordroid.showcase.quotes.domain.usecase.GetQuotesUseCase
import com.jordroid.showcase.quotes.domain.usecase.RemoveQuoteUseCase
import com.jordroid.showcase.quotes.presenter.model.QuoteStatisticUi
import com.jordroid.showcase.quotes.presenter.model.QuoteUi
import com.jordroid.showcase.quotes.presenter.model.QuoteUi.QuoteHeaderUi
import com.jordroid.showcase.quotes.presenter.model.QuoteUi.QuoteItemUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class QuoteViewModel(
    private val getQuotesUseCase: GetQuotesUseCase,
    private val fetchNewQuoteUseCase: FetchNewQuoteUseCase,
    private val removeQuoteUseCase: RemoveQuoteUseCase,
) : ViewModel() {

    /**
     * Immutable flow contains [QuoteStatisticUi] object
     */
    private val _quoteStatistic = MutableSharedFlow<QuoteStatisticUi>()
    val quoteStatisticUi: Flow<QuoteStatisticUi> = _quoteStatistic

    /**
     * Get the list of quote
     */
    fun getQuotes(): Flow<List<QuoteUi>> =
        getQuotesUseCase.readAll().flowOn(Dispatchers.IO).map { list ->
            // Add new statistic value to the right flow
            _quoteStatistic.emit(
                QuoteStatisticUi(
                    list.size, list.distinctBy { it.anime }.size, SimpleDateFormat(
                        "k:mm:ss",
                        Locale.getDefault()
                    ).format(System.currentTimeMillis())
                )
            )
            // Then map all result to ui object and return to the ui
            list.toUi()
        }

    /**
     * Filter the list of quote by matching textValue
     * @param textValue String that must be contained in anime name
     */
    fun searchWith(textValue: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getQuotesUseCase.searchAnimeWithName(textValue)
        }
    }

    /**
     * Retrieve a new quote
     */
    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            fetchNewQuoteUseCase.fetch()
        }
    }

    /**
     * Remove the given quote by id
     */
    fun remove(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            removeQuoteUseCase.delete(id = id)
        }
    }
}

/**
 * Map entity to ui object
 */
private fun QuoteEntity.toUi() = QuoteItemUi(
    id = id,
    anime = anime,
    character = character,
    quote = quote
)

/**
 * Map List of entity to ui object
 */
private fun List<QuoteEntity>.toUi(): List<QuoteUi> {
    val result = mutableListOf<QuoteUi>()
    asSequence().map {
        it.toUi()
    }.groupBy {
        it.label
    }.toList().forEach { (anime, items) ->
        // For each anime create header with anime name
        result.add(QuoteHeaderUi(anime))
        // Add all quote for the given anime
        result.addAll(items)
    }
    return result
}
