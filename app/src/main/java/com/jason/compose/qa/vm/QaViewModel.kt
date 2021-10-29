package com.jason.compose.qa.vm

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jason.compose.qa.model.Data
import com.jason.compose.qa.model.QaRepo
import com.module.common.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

class QaViewModel : BaseViewModel() {

    val repo: QaRepo by lazy(mode = LazyThreadSafetyMode.NONE) { QaRepo() }

    fun loadList():Flow<PagingData<Data>>{
        return repo.loadList().cachedIn(viewModelScope)
    }


}