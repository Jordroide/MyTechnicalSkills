package com.jordroid.showcase.quote.all.domain.usecase

import com.jordroid.showcase.quotes.data.repository.QuoteRepositoryImpl
import com.jordroid.showcase.quotes.domain.model.QuoteEntity
import com.jordroid.showcase.quotes.domain.usecase.GetQuotesUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GetQuotesUseCaseTest {

    private lateinit var getQuotesUseCase: GetQuotesUseCase

    @RelaxedMockK
    private lateinit var quoteRepositoryImpl: QuoteRepositoryImpl

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this)
        getQuotesUseCase = GetQuotesUseCase(quoteRepositoryImpl)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `check list if ordered by alpha and`() = runBlockingTest {
        every { quoteRepositoryImpl.readAll() } returns flowOf(
            listOf(
                QuoteEntity("Dragon Ball Z", "San-Goku", "See you soon"),
                QuoteEntity(
                    "Spider-Man",
                    "Uncle Ben",
                    "With great power comes great responsibility"
                ),
                QuoteEntity(
                    "Hunter X Hunter",
                    "Kurapika",
                    "The only principle is that there is no principles"
                )
            )
        )

        getQuotesUseCase.readAll().first().let {
            assertEquals(3, it.size)
            assertEquals("Dragon Ball Z", it[0].anime)
            assertEquals("Hunter X Hunter", it[1].anime)
            assertEquals("Spider-Man", it[2].anime)
        }
    }
}
