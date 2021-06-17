package com.jordroid.showcase.quote.all.domain.repository

import com.jordroid.showcase.quote.all.data.localdatasource.database.QuoteRoom
import com.jordroid.showcase.quote.all.data.localdatasource.database.QuoteRoomDao
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit

internal class QuoteRepositoryTest {

    private lateinit var repository: QuoteRepository

    @RelaxedMockK
    private lateinit var quoteRoomDao: QuoteRoomDao

    @RelaxedMockK
    private lateinit var retrofit: Retrofit

    @BeforeEach
    fun before() {
        MockKAnnotations.init(this)
        repository = QuoteRepository(quoteRoomDao, retrofit)
    }

    @AfterEach
    fun after() {
        clearAllMocks()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun readAll() = runBlockingTest {
        every { quoteRoomDao.selectAll() } returns flowOf(
            listOf(
                QuoteRoom("Dragon Ball Z", "San-Goku", "See you soon"),
                QuoteRoom("Spider-Man", "Uncle Ben", "With great power comes great responsibility"),
                QuoteRoom(
                    "Hunter X Hunter",
                    "Kurapika",
                    "The only principle is that there is no principles"
                ),
            )
        )
        repository.readAll().first().let {
            assertEquals(3, it.size)
            assertEquals("San-Goku", it[0].character)
            assertEquals("With great power comes great responsibility", it[1].quote)
            assertEquals("Hunter X Hunter", it[2].anime)
        }

    }
}