package com.jason.compose.project.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.os.bundleOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.jason.common.bean.WebContent
import com.jason.compose.project.model.Project
import com.jason.compose.project.vm.ProjectViewModel
import com.lib.router.Navigator
import com.lib.router.Routes
import com.like.IconType
import com.like.LikeButton
import com.module.common.config.BundleKey
import com.module.common.ui.theme.*
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun ProjectFragment(){
    val viewModel: ProjectViewModel = viewModel()
    LaunchedEffect(Unit){
        viewModel.requestTab()
    }
    val tabState  = viewModel.tabList.observeAsState()
    val scope = rememberCoroutineScope()
    val pageState = rememberPagerState(initialPage = 0)
    Column {
        tabState.value?.let {
            ScrollableTabRow(selectedTabIndex = pageState.currentPage,modifier = Modifier.wrapContentSize(),
                edgePadding = 16.dp,backgroundColor = ColorPrimary) {
                it.forEachIndexed{index,tab->
                    run {
                        Tab(selected = pageState.currentPage == index, selectedContentColor = White,unselectedContentColor = ColorD3,onClick = {
                            scope.launch { pageState.scrollToPage(index) }
                        },text = { Text(text = tab.name) })
                    }
                }
            }
            HorizontalPager(count = it.size,state = pageState,modifier = Modifier.weight(1f)) { index->
                ListView(viewModel = viewModel,cid = it[index].id)
            }
        }
    }
}


@Composable
fun ListView(viewModel:ProjectViewModel,cid:Int){
    val lazyPagingItems = viewModel.requestList(cid = cid).collectAsLazyPagingItems()
    LazyColumn{
        items(lazyPagingItems){itemData->
            ItemView(data = itemData!!)
        }
    }
}



@Composable
fun ItemView(data: Project){
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .padding(12.dp)
        .clickable {
            val bundle = bundleOf()
            bundle.putParcelable(BundleKey.WEB_CONTENT,
                WebContent(data.author,data.projectLink))
            Navigator.pushByParams(Routes.WEB,bundle)
        },verticalArrangement = Arrangement.Center) {
        Row(modifier = Modifier.weight(1f).fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
            Column() {
                Text(text = "作者:${data.author}",style = blackNormal12)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = data.desc,style = darkGreyNormal14,maxLines = 3,modifier = Modifier.fillMaxWidth(0.7f))
                Spacer(modifier = Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AndroidView(factory = {context -> LikeButton(context).apply {
                        setIcon(IconType.Heart)
                        setIconSizeDp(20)
                        isEnabled = false
                        isLiked = true
                    } },modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(text = data.zan.toString(),style = d3Normal14)
                }
            }
            Image(painter = rememberCoilPainter(request = data.envelopePic), contentDescription = "",modifier = Modifier.width(80.dp).height(100.dp))
        }
        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(0.5.dp),color = Color.Black)
    }
}