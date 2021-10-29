package com.jason.compose.project.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.jason.compose.project.model.Project
import com.jason.compose.project.model.ProjectRepository
import com.jason.compose.project.model.ProjectTab
import com.lib.net.bean.ResState
import com.module.common.base.BaseViewModel
import kotlinx.coroutines.flow.Flow


class ProjectViewModel : BaseViewModel() {


     val repository: ProjectRepository by lazy(mode = LazyThreadSafetyMode.NONE) { ProjectRepository() }

    val tabList = MutableLiveData<List<ProjectTab>>()
    val errMsg = MutableLiveData<String>()

    fun requestTab(){
        launch({
            val state = repository.loadTab()
            if (state is ResState.Success){
                tabList.postValue(state.data)
            }else if(state is ResState.Fail){
                errMsg.postValue(state.errorMsg)
            }
        })
    }

    fun requestList(cid: Int): Flow<PagingData<Project>> {
        return repository.loadList(cid).cachedIn(viewModelScope)
    }

}