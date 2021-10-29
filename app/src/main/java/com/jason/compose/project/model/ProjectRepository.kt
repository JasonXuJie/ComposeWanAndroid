package com.jason.compose.project.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jason.compose.project.api.ProjectService
import com.lib.net.RetrofitManager
import com.lib.net.bean.ResState
import com.module.common.base.BaseRepository
import kotlinx.coroutines.flow.Flow

class ProjectRepository: BaseRepository() {

    private val pageSize = 20

    suspend fun loadTab(): ResState<List<ProjectTab>> {
       return execute(RetrofitManager.retrofit.create(ProjectService::class.java).loadTab())
    }

    fun loadList(cid:Int):Flow<PagingData<Project>>{
        return Pager(config = PagingConfig(
            pageSize = pageSize,
            prefetchDistance = 5,
            initialLoadSize = 10,
            maxSize = pageSize * 3
        )){
            ProjectDataSource(RetrofitManager.retrofit.create(ProjectService::class.java),cid)
        }.flow
    }

}