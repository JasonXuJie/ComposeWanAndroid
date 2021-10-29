package com.jason.compose.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.os.bundleOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jason.common.bean.WebContent
import com.jason.compose.R
import com.jason.compose.home.model.Article
import com.jason.compose.home.model.TopData
import com.jason.home.vm.HomeViewModel
import com.lib.router.Navigator
import com.lib.router.Routes
import com.like.IconType
import com.like.LikeButton
import com.module.common.config.BundleKey
import com.module.common.ui.theme.*
import kotlinx.coroutines.delay

@ExperimentalPagerApi
@Composable
fun HomeFragment() {
    val vm: HomeViewModel = viewModel()
    LaunchedEffect(Unit) {
        vm.loadBanner()
        delay(1500)
        vm.loadTopList()
    }
    val bannerState = vm.bannerList.observeAsState()
    val bannerList = arrayListOf<BannerData>()
    val topState = vm.topList.observeAsState()
    val lazyPagingItems = vm.loadArtList().collectAsLazyPagingItems()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        //banner
        bannerState.value?.let {
            it.forEach { bannerData ->
                val banner = BannerData(bannerData.imagePath, bannerData.url)
                bannerList.add(banner)
            }
            item { BannerView(bannerList) }
        }
        //置顶文章
        item {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 8.dp, bottom = 8.dp)) {
                Text(text = "---置顶文章---", style = d3Normal14)
            }
        }
        topState.value?.let {
            items(it) { data ->
                TopListItem(data = data)
            }
        }
        //文章列表
        item {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 8.dp, bottom = 8.dp)) {
                Text(text = "---更多好文---", style = d3Normal14)
            }
        }
        items(lazyPagingItems) { itemData ->
            ArtListItem(data = itemData!!)
        }
    }

}

@ExperimentalPagerApi
@Composable
fun BannerView(dataList: List<BannerData>) {
    Banner(list = dataList, timeMillis = 2500, onClick = { url ->
        val bundle = bundleOf()
        bundle.putParcelable(BundleKey.WEB_CONTENT, WebContent(null,url))
        Navigator.pushByParams(Routes.WEB,bundle)
    })
}

@Composable
fun TopListItem(data: TopData) {
    Column(modifier = Modifier.padding(12.dp).clickable {
        val bundle = bundleOf()
        bundle.putParcelable(BundleKey.WEB_CONTENT, WebContent(data.shareUser,data.link))
        Navigator.pushByParams(Routes.WEB,bundle)
    }) {
        Row(verticalAlignment = Alignment.Bottom) {
            if (data.fresh) {
                Text(text = stringResource(id = R.string.text_new),
                    style = primaryNormal13,
                    modifier = Modifier.padding(end = 5.dp))
            }
            Text(text = data.author, style = blackNormal12, modifier = Modifier.padding(end = 5.dp))
            Text(text = if (data.tags.isEmpty()) "" else data.tags[0].name, style = primaryNormal13)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = data.niceDate, style = darkGreyNormal13)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = data.title, style = blackNormal16, maxLines = 2)
        Spacer(modifier = Modifier.height(10.dp))
        Row() {
            Text(text = stringResource(id = R.string.top), style = f4Normal12)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "${data.superChapterName}.${data.chapterName}", style = blackNormal13)
            Spacer(modifier = Modifier.weight(1f))
            AndroidView(factory = { context ->
                LikeButton(context).apply {
                    setIconSizeDp(20)
                    setIcon(IconType.Heart)
                }
            }, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun ArtListItem(data:Article){
    Column(modifier = Modifier.padding(12.dp).clickable {
        val bundle = bundleOf()
        bundle.putParcelable(BundleKey.WEB_CONTENT, WebContent(data.shareUser,data.link))
        Navigator.pushByParams(Routes.WEB,bundle)
    }) {
        Row(verticalAlignment = Alignment.Bottom) {
            if (data.fresh) {
                Text(text = stringResource(id = R.string.text_new),
                    style = primaryNormal13,
                    modifier = Modifier.padding(end = 5.dp))
            }
            Text(text = data.author, style = blackNormal12, modifier = Modifier.padding(end = 5.dp))
            Spacer(modifier = Modifier.weight(1f))
            Text(text = data.niceDate, style = darkGreyNormal13)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = data.title, style = blackNormal16, maxLines = 2)
        Spacer(modifier = Modifier.height(10.dp))
        Row() {
            Text(text = "${data.superChapterName}.${data.chapterName}", style = blackNormal13)
            Spacer(modifier = Modifier.weight(1f))
            AndroidView(factory = { context ->
                LikeButton(context).apply {
                    setIconSizeDp(20)
                    setIcon(IconType.Heart)
                }
            }, modifier = Modifier.size(20.dp))
        }
    }
}




