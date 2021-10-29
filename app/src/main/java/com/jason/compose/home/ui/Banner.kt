package com.jason.compose.home.ui

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.jason.compose.R
import kotlinx.coroutines.delay

/**
 * 轮播图数据
 */
data class BannerData(
    val imageUrl: String,
    val linkUrl: String,
)


/**
 * 轮播图
 * [timeMillis] 停留时间
 * [loadImage] 加载中显示的布局
 * [indicatorAlignment] 指示点的的位置,默认是轮播图下方的中间,带一点padding
 * [onClick] 轮播图点击事件
 */
@ExperimentalPagerApi
@Composable
fun Banner(
    list: List<BannerData>?,
    timeMillis: Long = 3000,
    @DrawableRes loadImage: Int = R.drawable.app_icon,
    indicatorAlignment: Alignment = Alignment.BottomCenter,
    onClick: (link: String) -> Unit = {},
) {
    Box(modifier = Modifier
        .background(MaterialTheme.colors.background)
        .fillMaxWidth()
        .height(220.dp)) {
        if (list == null) {
            Image(painter = painterResource(id = loadImage),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop)
        } else {
            val pagerState = rememberPagerState(
                initialPage = 0
            )
            //监听动画执行
            var executeChangePage by remember { mutableStateOf(false) }
            var currentPageIndex = 0
            //自动滚动
            LaunchedEffect(pagerState.currentPage, executeChangePage) {
                if (pagerState.pageCount > 0) {
                    delay(timeMillis)
                    //这里直接+1就可以循环，前提是infiniteLoop == true
                    if(pagerState.currentPage == pagerState.pageCount -1){
                        pagerState.animateScrollToPage(0)
                    }else{
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }

                }
            }
            HorizontalPager(count = list.size, state = pagerState, modifier = Modifier
                .pointerInput(pagerState.currentPage) {
                    awaitPointerEventScope {
                        while (true) {
                            //PointerEventPass.Initial - 本控件优先处理手势，处理后再交给子组件
                            val event = awaitPointerEvent(PointerEventPass.Initial)
                            //获取到第一根按下的手指
                            val dragEvent = event.changes.firstOrNull()
                            when {
                                //当前移动手势是否已被消费
                                dragEvent!!.positionChangeConsumed() -> {
                                    return@awaitPointerEventScope
                                }
                                //是否已经按下(忽略按下手势已消费标记)
                                dragEvent.changedToDownIgnoreConsumed() -> {
                                    //记录下当前的页面索引值
                                    currentPageIndex = pagerState.currentPage
                                }
                                //是否已经抬起(忽略按下手势已消费标记)
                                dragEvent.changedToUpIgnoreConsumed() -> {
                                    //当页面没有任何滚动/动画的时候pagerState.targetPage为null，这个时候是单击事件
                                    if (pagerState.targetPage == null) return@awaitPointerEventScope
                                    //当pageCount大于1，且手指抬起时如果页面没有改变，就手动触发动画
                                    if (currentPageIndex == pagerState.currentPage && pagerState.pageCount > 1) {
                                        executeChangePage = !executeChangePage
                                    }
                                }
                            }
                        }
                    }
                }
                .clickable { onClick(list[pagerState.currentPage].linkUrl) }
                .fillMaxSize()) { page ->
                Image(painter = rememberCoilPainter(request = list[page].imageUrl),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop)
            }
            Box(modifier = Modifier.align(indicatorAlignment).padding(bottom = 6.dp, start = 6.dp, end = 6.dp)){
                for (i in list.indices){
                    //大小
                    var size by remember { mutableStateOf(5.dp) }
                    size = if (pagerState.currentPage == i) 7.dp else 5.dp
                    //颜色
                    val color =
                        if (pagerState.currentPage == i) MaterialTheme.colors.primary else Color.Gray
                    Box(
                        modifier = Modifier.clip(CircleShape).background(color)
                            //当size改变的时候以动画的形式改变
                            .animateContentSize().size(size)
                    )
                    //指示点间的间隔
                    if (i != list.lastIndex) Spacer(
                        modifier = Modifier.height(0.dp).width(4.dp)
                    )
                }
            }

        }
    }
}