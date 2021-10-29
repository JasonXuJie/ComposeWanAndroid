package com.jason.compose.system.vm

import androidx.lifecycle.MutableLiveData
import com.jason.compose.system.model.NavData
import com.jason.compose.system.model.NavRepo
import com.lib.net.bean.ResState
import com.module.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


class NavViewModel: BaseViewModel() {

     val repo: NavRepo by lazy(mode = LazyThreadSafetyMode.NONE) { NavRepo() }

    val data = MutableLiveData<List<NavData>>()
    val errMsg = MutableLiveData<String>()


    fun loadNavList(){
        launch({
            val state = repo.loadNavTree()
            if (state is ResState.Success){
                data.postValue(state.data)
            }else if (state is ResState.Fail){
                errMsg.postValue(state.errorMsg)
            }
        })
    }


}