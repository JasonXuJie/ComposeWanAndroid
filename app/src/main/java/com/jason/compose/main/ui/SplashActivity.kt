package com.jason.compose.main.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.lib.router.Navigator.push
import com.lib.router.Routes
import com.module.common.R
import com.module.common.base.BaseComposeActivity
import com.module.common.config.SpKey
import com.module.common.ui.theme.ColorPrimary
import com.module.common.ui.theme.ComposeWanAndroidTheme
import com.module.common.ui.theme.whiteNormal14
import com.module.common.utils.SpUtil
import kotlinx.coroutines.delay

@Route(path = Routes.SPLASH)
class SplashActivity : BaseComposeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            rememberSystemUiController().setStatusBarColor(color = Color.Transparent,
                darkIcons = MaterialTheme.colors.isLight
                )
            ComposeWanAndroidTheme {
                SplashView { finish() }
            }
        }
    }


}

@Composable
fun SplashView(call:()->Unit){
    LaunchedEffect(Unit){
        delay(2000)
        val isFirst = SpUtil.get(SpKey.IS_FIRST,true) as Boolean
        if (isFirst){
            push(Routes.GUIDE)
        }else{
            push(Routes.MAIN)
        }
        call.invoke()
    }
    Box(modifier = Modifier
        .background(color = ColorPrimary)
        .fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Column {
            Image(painter = painterResource(id = R.drawable.app_icon),
                contentDescription = null,
                modifier = Modifier.size(70.dp, 70.dp))
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = "WanAndroid", style = whiteNormal14)
        }

    }
}