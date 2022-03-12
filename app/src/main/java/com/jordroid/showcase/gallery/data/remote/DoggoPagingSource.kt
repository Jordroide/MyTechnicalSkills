package com.jordroid.showcase.gallery.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jordroid.showcase.gallery.data.model.DoggoDataSource
import retrofit2.HttpException
import java.io.IOException

class DoggoPagingSource(private val doggyApi: DoggyApi) :
    PagingSource<Int, DoggoDataSource>() {

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, DoggoDataSource>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DoggoDataSource> {
        //for first case it will be null, then we can pass some default value, in our case it's 1
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = doggyApi.getDoggoImages(page, params.loadSize)
            LoadResult.Page(
                response, prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}
