package com.jason.home.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jason.compose.home.model.Article
import com.jason.compose.home.model.BannerData
import com.jason.compose.home.model.HomeRepo
import com.jason.compose.home.model.TopData
import com.lib.net.bean.ResState
import com.module.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow

class HomeViewModel : BaseViewModel() {

    val repo: HomeRepo by lazy(mode = LazyThreadSafetyMode.NONE) { HomeRepo() }

    val bannerList = MutableLiveData<List<BannerData>>()

    val topList = MutableLiveData<List<TopData>>()

    val bannerErrMsg = MutableLiveData<String>()

    val topErrMsg = MutableLiveData<String>()


    fun loadBanner(){
        launch({
            val state = repo.loadBanner()
            if (state is ResState.Success){
                bannerList.postValue(state.data)
            }else if (state is ResState.Fail){
                bannerErrMsg.postValue(state.errorMsg)
            }
        })
    }


    fun loadTopList(){
        launch({
            val state = repo.loadTopList()
            if (state is ResState.Success){
                topList.postValue(state.data)
            }else{
                topErrMsg.postValue((state as ResState.Fail).errorMsg)
            }
        })
    }


    fun loadArtList():Flow<PagingData<Article>>{
        return repo.loadArtList().cachedIn(viewModelScope)
    }



}