package com.jason.compose.project.api

import com.jason.compose.project.model.ProjectData
import com.jason.compose.project.model.ProjectTab
import com.lib.net.bean.BaseResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProjectService {

    @GET("project/tree/json")
    suspend fun loadTab(): BaseResult<List<ProjectTab>>

    @GET("project/list/{page}/json")
    suspend fun loadProjectList(@Path("page") page: Int,@Query("cid") cid: Int):BaseResult<ProjectData>
}