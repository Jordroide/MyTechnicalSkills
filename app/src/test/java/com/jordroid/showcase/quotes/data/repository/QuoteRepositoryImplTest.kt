package com.jordroid.showcase.quotes.data.repository

import com.jordroid.showcase.quotes.data.source.local.QuoteRoom
import com.jordroid.showcase.quotes.data.source.local.QuoteRoomDao
import com.jordroid.showcase.quotes.data.source.remote.QuoteApi
import com.jordroid.showcase.quotes.data.source.remote.QuoteDto
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.spekframework.spek2.Spek
import retrofit2.Response

@ExperimentalCoroutinesApi
class QuoteRepositoryImplTest : Spek({

    group("receive data from room") {

        lateinit var quoteRoomDao: QuoteRoomDao
        lateinit var quoteApi: QuoteApi

        beforeEachTest {
            quoteRoomDao = mockk(relaxed = true)
            quoteApi = mockk()
        }
        val quoteRepository by memoized { QuoteRepositoryImpl(quoteApi, quoteRoomDao) }

        test("check list is correctly converted from room to entity") {
            runBlockingTest {
                every { quoteRoomDao.selectAll() } returns flowOf(
                    listOf(
                        QuoteRoom("Dragon Ball Z", "San-Goku", "See you soon"),
                        QuoteRoom(
                            "Spider-Man",
                            "Uncle Ben",
                            "With great power comes great responsibility"
                        ),
                        QuoteRoom(
                            "Hunter X Hunter",
                            "Kurapika",
                            "The only principle is that there is no principles"
                        ),
                    )
                )
                quoteRepository.readAll().first().let {
                    assertEquals(3, it.size)
                    assertEquals("San-Goku", it[0].character)
                    assertEquals("With great power comes great responsibility", it[1].quote)
                    assertEquals("Hunter X Hunter", it[2].anime)
                }
            }
        }

        test("receive a new quote and does not exists in room yet") {
            runTest {
                coEvery { quoteApi.getRandomQuote() } returns Response.success(
                    QuoteDto(
                        "Batman",
                        "Robbin",
                        "Again a great mission ended"
                    )
                )

                quoteRepository.fetchData()

                verify(exactly = 1) {
                    quoteRoomDao.insert(
                        QuoteRoom(
                            "Batman",
                            "Robbin",
                            "Again a great mission ended",
                            0L
                        )
                    )
                }
            }
        }
    }
})