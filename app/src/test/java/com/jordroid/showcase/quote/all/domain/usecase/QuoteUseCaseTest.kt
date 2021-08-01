package com.jordroid.showcase.quote.all.domain.usecase

import com.jordroid.showcase.quote.random.domain.model.QuoteEntity
import com.jordroid.showcase.quote.random.domain.repository.QuoteRepository
import com.jordroid.showcase.quote.random.domain.usecase.QuoteUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class QuoteUseCaseTest {

    private lateinit var quoteUseCase: QuoteUseCase

    @RelaxedMockK
    private lateinit var quoteRepository: QuoteRepository

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this)
        quoteUseCase = QuoteUseCase(quoteRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `check list if ordered by alpha and`() = runBlockingTest {
        every { quoteRepository.readAll() }  returns flowOf(
            listOf(
                QuoteEntity("Dragon Ball Z", "San-Goku", "See you soon"),
                QuoteEntity("Spider-Man", "Uncle Ben", "With great power comes great responsibility"),
                QuoteEntity("Hunter X Hunter", "Kurapika", "The only principle is that there is no principles")
            )
        )

        quoteUseCase.readAll().first().let {
            assertEquals(3, it.size)
            assertEquals("Dragon Ball Z", it[0].anime)
            assertEquals("Hunter X Hunter", it[1].anime)
            assertEquals("Spider-Man", it[2].anime)
        }
    }
}
