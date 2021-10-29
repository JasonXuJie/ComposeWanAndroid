package com.jason.compose.web.utils

import android.webkit.WebChromeClient
import android.webkit.WebView
import com.module.common.utils.MyLog

class ChromeClient : WebChromeClient() {

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        //super.onProgressChanged(view, newProgress)
        MyLog.e("progress:${newProgress}")
    }


    override fun onReceivedTitle(view: WebView?, title: String?) {
        //super.onReceivedTitle(view, title)
    }
}