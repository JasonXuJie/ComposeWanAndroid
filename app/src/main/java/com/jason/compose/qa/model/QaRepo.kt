package com.jason.compose.qa.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jason.compose.qa.api.QaService
import com.lib.net.RetrofitManager
import kotlinx.coroutines.flow.Flow

class QaRepo{

    val pageSize = 15

    fun loadList():Flow<PagingData<Data>>{
        val config = PagingConfig(
            pageSize = pageSize
        )
        return Pager(
            config  = config,
            pagingSourceFactory = { QaDataSource(RetrofitManager.retrofit.create(QaService::class.java)) }
        ).flow
    }
}