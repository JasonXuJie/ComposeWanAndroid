package com.jason.compose.system.api

import com.jason.compose.system.model.NavData
import com.jason.compose.system.model.SysArticleData
import com.jason.compose.system.model.SysTree
import com.lib.net.bean.BaseResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SystemService {


    @GET("navi/json")
    suspend fun getNavList(): BaseResult<List<NavData>>


    @GET("tree/json")
    suspend fun getSysTree():BaseResult<List<SysTree>>

    @GET("article/list/{page}/json")
    suspend fun getArtList(@Path("page") page:Int,@Query("cid") cid:Int):BaseResult<SysArticleData>
}