package com.jason.compose.system.vm

import androidx.lifecycle.MutableLiveData
import com.jason.compose.system.model.SysRepo
import com.jason.compose.system.model.SysTree
import com.lib.net.bean.ResState
import com.module.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


class SystemViewModel : BaseViewModel() {


    val repo: SysRepo by lazy(mode = LazyThreadSafetyMode.NONE) {  SysRepo() }

    val sysTree = MutableLiveData<List<SysTree>>()
    val errMsg = MutableLiveData<String>()


    fun loadSysTree(){
        launch({
           val state =  repo.loadSysTree()
           if (state is ResState.Success){
               sysTree.postValue(state.data)
           }else if (state is ResState.Fail){
               errMsg.postValue(state.errorMsg)
           }
        })
    }
}