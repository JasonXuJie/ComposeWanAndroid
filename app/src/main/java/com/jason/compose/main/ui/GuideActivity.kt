package com.jason.compose.main.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.jason.compose.R
import com.lib.router.Navigator.push
import com.lib.router.Routes
import com.module.common.config.SpKey
import com.module.common.ui.theme.ColorPrimary
import com.module.common.ui.theme.whiteBold14
import com.module.common.utils.SpUtil


@ExperimentalPagerApi
@Route(path = Routes.GUIDE)
class GuideActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuidePager { finish() }
        }
    }
}


@ExperimentalPagerApi
@Composable
fun GuidePager(call:()->Unit) {
    val pagerState = rememberPagerState(initialPage = 0)
    val pics = arrayOf(R.drawable.img_guide_one,
        R.drawable.img_guide_two,
        R.drawable.img_guide_three,
        R.drawable.img_guide_four)
    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(count = pics.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()) { index ->
            if (index == 3) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(painter = painterResource(id = pics[index]),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                    Button(modifier = Modifier
                        .size(width = 100.dp, height = 80.dp)
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 35.dp),
                        onClick = {
                            SpUtil.put(SpKey.IS_FIRST,false)
                            push(Routes.MAIN)
                            call.invoke()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = ColorPrimary
                        )
                    ) {
                        Text(text = "去体验",style = whiteBold14)
                    }
                }
            } else {
                Image(painter = painterResource(id = pics[index]),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
        }

    }
}