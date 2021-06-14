package com.jordroid.sampleinjection.quote.presenter.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordroid.sampleinjection.quote.domain.model.QuoteEntity
import com.jordroid.sampleinjection.quote.domain.usecase.QuoteUseCase
import com.jordroid.sampleinjection.quote.presenter.model.QuoteItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class QuoteViewModel(
    private val quoteUseCase: QuoteUseCase
) : ViewModel() {

    fun getVehicles(): Flow<List<QuoteItem>> {
        return quoteUseCase.readAll().map { list ->
            list.map {
                it.toUi()
            }
        }
    }

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            quoteUseCase.fetch()
        }
    }

    private fun QuoteEntity.toUi() = QuoteItem(content = quote, category = category)
}