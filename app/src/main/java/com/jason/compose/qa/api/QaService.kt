package com.jason.compose.qa.api

import com.jason.compose.qa.model.QaData
import com.lib.net.bean.BaseResult
import retrofit2.http.GET
import retrofit2.http.Path

interface QaService {


    @GET("wenda/list/{page}/json ")
    suspend fun loadList(@Path("page") page:Int): BaseResult<QaData>

}