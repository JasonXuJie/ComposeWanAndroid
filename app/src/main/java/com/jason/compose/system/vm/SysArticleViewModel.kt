package com.jason.compose.system.vm

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jason.compose.system.model.SysArticle
import com.jason.compose.system.model.SysRepo
import com.module.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SysArticleViewModel : BaseViewModel() {

     val repo: SysRepo by lazy(mode = LazyThreadSafetyMode.NONE) { SysRepo() }



    fun loadArtList(cid:Int): Flow<PagingData<SysArticle>> {
        return repo.loadArtList(cid).cachedIn(viewModelScope)
    }
}