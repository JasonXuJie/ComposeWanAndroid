package com.jason.compose.home.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jason.compose.home.api.HomeService
import com.lib.net.RetrofitManager
import com.lib.net.bean.ResState
import com.module.common.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class HomeRepo : BaseRepository(){

    private val pageSize  = 15

    suspend fun loadBanner(): ResState<List<BannerData>> {
        return execute(RetrofitManager.retrofit.create(HomeService::class.java).getBanner())
    }


    suspend fun loadTopList():ResState<List<TopData>>{
        return execute(RetrofitManager.retrofit.create(HomeService::class.java).getTopList())
    }

    fun loadArtList():Flow<PagingData<Article>>{
        val config = PagingConfig(
            pageSize = pageSize
        )
        return Pager(
            config = config,
            pagingSourceFactory = {ArtDataSource(RetrofitManager.retrofit.create(HomeService::class.java))}
        ).flow
    }
}