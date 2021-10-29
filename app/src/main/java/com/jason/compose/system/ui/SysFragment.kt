package com.jason.compose.system.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.jason.compose.project.vm.ProjectViewModel
import com.jason.compose.system.vm.NavViewModel
import com.jason.compose.system.vm.SystemViewModel
import com.module.common.ui.theme.*
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun SysFragment() {
    val titles = listOf("体系", "导航")
    val scope = rememberCoroutineScope()
    val pageState = rememberPagerState(initialPage = 0)
    Column() {
        ScrollableTabRow(selectedTabIndex = pageState.currentPage,
            backgroundColor = ColorPrimary,
            modifier = Modifier.wrapContentWidth(), indicator = {}) {
            titles.forEachIndexed { index, title ->
                run {
                    Tab(selected = pageState.currentPage == index, selectedContentColor = White,unselectedContentColor = ColorD3,onClick = {
                        scope.launch { pageState.scrollToPage(index) }
                    }, text = { Text(text = title) })
                }
            }
        }
        HorizontalPager(count = titles.size,state = pageState,modifier = Modifier.weight(1f)) {index->
                if (index == 0)
                SysView()
                else
                NavView()
        }
    }

}


@Composable
fun SysView(){
    val sysVm:SystemViewModel = viewModel()
    LaunchedEffect(Unit){
        sysVm.loadSysTree()
    }
    val dataState  = sysVm.sysTree.observeAsState()
    dataState.value?.let {
        LazyColumn(modifier = Modifier.fillMaxSize()){
            items(it){ itemData ->
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)) {
                    Text(text = itemData.name,style = blackBold16)
                    Spacer(modifier = Modifier.height(15.dp))
                    FlowRow(mainAxisSpacing = 5.dp,crossAxisSpacing = 10.dp) {
                        itemData.children.forEach {children ->
                            Text(text = children.name,style = blackNormal14,
                                modifier = Modifier
                                    .background(
                                        ColorD3)
                                    .border(1.dp, ColorD3, RoundedCornerShape(20.dp))
                                    .padding(6.dp))
                        }
                    }    
                }
            }       
        }
    }

}



@Composable
fun NavView(){
    val navVm:NavViewModel = viewModel()
    LaunchedEffect(Unit){
        navVm.loadNavList()
    }
    val dataState  = navVm.data.observeAsState()
    dataState.value?.let {
        LazyColumn(modifier = Modifier.fillMaxSize()){
            items(it){ itemData ->
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)) {
                    Text(text = itemData.name,style = blackBold16)
                    Spacer(modifier = Modifier.height(15.dp))
                    FlowRow(mainAxisSpacing = 5.dp,crossAxisSpacing = 10.dp) {
                        itemData.articles.forEach {children ->
                            Text(text = children.title,style = blackNormal14,
                                modifier = Modifier
                                    .background(
                                        ColorD3)
                                    .border(1.dp, ColorD3, RoundedCornerShape(20.dp))
                                    .padding(6.dp))
                        }
                    }
                }
            }
        }
    }
    LazyColumn(modifier = Modifier.fillMaxSize()){

    }
}