package com.jordroid.showcase.gallery.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jordroid.showcase.application.remote.Endpoints
import com.jordroid.showcase.gallery.data.model.DogPictureDataSource
import com.jordroid.showcase.gallery.data.remote.DogUrl.GET_DOG_URL
import retrofit2.HttpException
import java.io.IOException

object DogUrl {
    const val GET_DOG_URL = "${Endpoints.BASE_URL_DOGS}/v1/images/search"
}

class DoggyPagingSource(private val doggyApi: DoggyApi) : PagingSource<Int, DogPictureDataSource>() {

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, DogPictureDataSource>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DogPictureDataSource> {
        // by default key is null then go to the first page
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = doggyApi.getDoggoImages(
                GET_DOG_URL,
                page,
                params.loadSize
            )
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
