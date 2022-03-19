package com.jordroid.showcase.quotes.domain.usecase

import com.jordroid.showcase.quotes.data.repository.QuoteRepositoryImpl
import com.jordroid.showcase.quotes.domain.model.QuoteEntity
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.spekframework.spek2.Spek

@ExperimentalCoroutinesApi
class GetQuotesUseCaseTest : Spek({
    group("get quotes use case") {

        lateinit var quoteRepositoryImpl: QuoteRepositoryImpl

        beforeEachTest {
            quoteRepositoryImpl = mockk()
        }

        val getQuotesUseCase by memoized { GetQuotesUseCase(quoteRepositoryImpl) }

        test("verify the order is correct") {
            runTest {
                every { quoteRepositoryImpl.readAll() } returns flowOf(
                    listOf(
                        QuoteEntity(
                            2L,
                            "Dragon Ball Z",
                            "San-Goku",
                            "See you soon"
                        ),
                        QuoteEntity(
                            1L,
                            "Spider-Man",
                            "Uncle Ben",
                            "With great power comes great responsibility"
                        ),
                        QuoteEntity(
                            0L,
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
    }
})