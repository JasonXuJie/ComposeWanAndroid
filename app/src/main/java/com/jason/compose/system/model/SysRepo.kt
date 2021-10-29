package com.jason.compose.system.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jason.compose.system.api.SystemService
import com.lib.net.RetrofitManager
import com.lib.net.bean.ResState
import com.module.common.base.BaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SysRepo : BaseRepository() {

    private val pageSize = 20

    suspend fun loadSysTree(): ResState<List<SysTree>> {
        return execute(RetrofitManager.retrofit.create(SystemService::class.java).getSysTree())
    }


    fun loadArtList(cid:Int):Flow<PagingData<SysArticle>>{
        val config = PagingConfig(
            pageSize = pageSize
        )
        return Pager(
         config = config,
         pagingSourceFactory = {SysArtDataSource(cid,RetrofitManager.retrofit.create(SystemService::class.java))}
        ).flow
    }


}