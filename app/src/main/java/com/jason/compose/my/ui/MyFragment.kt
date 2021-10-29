package com.jason.compose.my.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lib.router.Navigator
import com.lib.router.Routes
import com.module.common.ui.theme.ColorF5
import com.module.common.ui.theme.ColorPrimary
import com.module.common.ui.theme.blackNormal14
import com.module.common.ui.theme.whiteNormal12
import java.lang.RuntimeException
import com.jason.compose.R

private val menus = mutableListOf<String>("常用网站", "收藏", "反馈", "设置")

@Composable
fun MyFragmentView() {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Column(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(ColorPrimary),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(id = R.drawable.img_header),
                    contentDescription = "",
                    modifier = Modifier.size(60.dp))
                Spacer(modifier = Modifier.size(15.dp))
                Text(text = "去登陆", style = whiteNormal12, modifier = Modifier.clickable { })
            }
        }
        itemsIndexed(menus) { index, item ->
            val icon = when (index) {
                0 -> {
                    R.drawable.menu_list_one
                }
                1 -> {
                    R.drawable.menu_list_six
                }
                2 -> {
                    R.drawable.menu_list_two
                }
                3 -> {
                    R.drawable.menu_list_seven
                }
                else -> {
                    throw RuntimeException("Not resource")
                }
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    when (index) {
                        0 -> {
                            Navigator.push(Routes.USED_WEB)
                        }
                        1 -> {
                            Navigator.push(Routes.COLLECT)
                        }
                        2 -> {
                            Navigator.push(Routes.FEEDBACK)
                        }
                        3 -> {
                            Navigator.push(Routes.SETTING)
                        }
                        else -> {
                            throw RuntimeException("Not resource")
                        }
                    }
                }) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = icon),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp))
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = menus[index], style = blackNormal14, modifier = Modifier.weight(1f))
                    Image(painter = painterResource(id = R.drawable.img_right_arrow),
                        contentDescription = null,
                        modifier = Modifier.size(10.dp))
                }
                Divider(color = ColorF5,modifier = Modifier.fillMaxWidth().height(1.dp))
            }

        }
    }
}