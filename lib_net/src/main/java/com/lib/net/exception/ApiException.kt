package com.lib.net.exception

import java.lang.RuntimeException

data class ApiException(val errorCode:Int,val errorMsg:String):RuntimeException(errorMsg)
