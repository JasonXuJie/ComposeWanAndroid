package com.jason.compose.system.model

import com.jason.compose.system.api.SystemService
import com.lib.net.RetrofitManager
import com.lib.net.bean.ResState
import com.module.common.base.BaseRepository

class NavRepo: BaseRepository() {

    suspend fun loadNavTree(): ResState<List<NavData>> {
        return execute(RetrofitManager.retrofit.create(SystemService::class.java).getNavList())
    }
}