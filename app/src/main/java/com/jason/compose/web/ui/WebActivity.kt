package com.jason.compose.web.ui

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jason.common.bean.WebContent
import com.jason.compose.R
import com.jason.compose.web.utils.ChromeClient
import com.lib.router.Routes
import com.module.common.base.BaseComposeActivity
import com.module.common.config.BundleKey
import com.module.common.ui.theme.ColorD3
import com.module.common.ui.theme.blackNormal14

@Route(path = Routes.WEB)
class WebActivity : BaseComposeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            intent.extras?.also {
                val content = it.getParcelable<WebContent>(BundleKey.WEB_CONTENT)
                content?.apply {
                    val url = this.url!!
                    Box(modifier = Modifier.fillMaxSize()) {
                        MyWebView(url = url)
                        if (!content.author.isNullOrEmpty()){
                            BottomBar(modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(start = 10.dp, end = 10.dp,top = 20.dp,bottom = 20.dp).align(Alignment.BottomCenter))
                        }
                    }
                }
            }
        }

    }
}





@Composable
fun MyWebView(url:String){
   AndroidView(factory = {context -> WebView(context).apply {
            val settings = settings
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true // 开启 DOM storage API 功能
            settings.databaseEnabled = true   //开启 database storage API 功能
            settings.loadWithOverviewMode = true // 缩放至屏幕的大小
            settings.useWideViewPort = true  //将图片调整到适合webview的大小
            settings.defaultTextEncodingName = "utf-8" //设置编码格式
            settings.loadsImagesAutomatically = true  //支持自动加载图片
            webChromeClient = ChromeClient()
            webViewClient = WebViewClient()
            loadUrl(url)

   } })
}

@Composable
fun BottomBar(modifier: Modifier){
    Row(modifier = modifier,verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween) {
        Row {
            Text(text = "作者",style = blackNormal14)
            Spacer(modifier = Modifier.width(10.dp))
            Image(painter = painterResource(id = R.drawable.share), contentDescription = "",modifier = Modifier
                .size(20.dp)
                .clickable { })
            Spacer(modifier = Modifier.width(10.dp))
            Image(painter = painterResource(id = R.drawable.refresh), contentDescription = "",modifier = Modifier
                .size(20.dp)
                .clickable { })
        }
        Divider(modifier = Modifier
            .width(50.dp)
            .height(5.dp),color = ColorD3)
        Text(text = "评论(0)",style = blackNormal14)
    }    
}



