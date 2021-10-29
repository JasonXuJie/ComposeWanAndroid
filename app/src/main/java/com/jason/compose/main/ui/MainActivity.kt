package com.jason.compose.main.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jason.compose.R
import com.jason.compose.home.ui.HomeFragment
import com.jason.compose.main.model.BottomItem
import com.jason.compose.my.ui.MyFragmentView
import com.jason.compose.project.ui.ProjectFragment
import com.jason.compose.qa.ui.QaFragmentView
import com.jason.compose.system.ui.SysFragment
import com.lib.router.Routes
import com.module.common.ui.theme.ColorD3
import com.module.common.ui.theme.ColorPrimary
import com.module.common.ui.theme.White


@ExperimentalPagerApi
@Route(path = Routes.MAIN)
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            rememberSystemUiController().setStatusBarColor(color = ColorPrimary,
                darkIcons = MaterialTheme.colors.isLight
            )
            MainLayout()
        }
    }
}

@ExperimentalPagerApi
@Composable
fun MainLayout() {
    val homeBottomItem =
        BottomItem("route_home", R.drawable.home_select, R.drawable.home_unselect, "首页")
    val qaBottomItem =
        BottomItem("route_qa", R.drawable.qa_project_select, R.drawable.qa_project_unselect, "问答")
    val sysBottomItem =
        BottomItem("route_sys", R.drawable.sys_select, R.drawable.sys_unselect, "系统")
    val projectBottomItem = BottomItem("route_project",
        R.drawable.qa_project_select,
        R.drawable.qa_project_unselect,
        "项目")
    val myBottomItem = BottomItem("route_my", R.drawable.my_select, R.drawable.my_unselect, "我的")
    val bottomItems =
        arrayOf(homeBottomItem, qaBottomItem, sysBottomItem, projectBottomItem, myBottomItem)
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(bottomBar = {
        BottomNavigation(backgroundColor = White, elevation = 3.dp) {
            bottomItems.forEach { item ->
                BottomNavigationItem(selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            //当底部导航导航到在非首页的页面时，执行手机的返回键 回到首页
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            //从名字就能看出来 跟activity的启动模式中的SingleTop模式一样 避免在栈顶创建多个实例
                            launchSingleTop = true
                            //切换状态的时候保存页面状态
                            restoreState = true
                        }
                    },
                    alwaysShowLabel = true,
                    selectedContentColor = ColorPrimary,
                    unselectedContentColor = ColorD3,
                    //icon = { Icon(Icons.Filled.Favorite, "") },
                    icon = {
                        Icon(painter = painterResource(id = if (currentRoute == item.route) item.selectIconId else item.unSelectedIconId),
                            contentDescription = "",
                            modifier = Modifier.size(30.dp))
                    },
                    label = { Text(item.title) }
                )
            }
        }
    }) {
        NavHost(navController = navController, startDestination = homeBottomItem.route) {
            composable(homeBottomItem.route) {
                HomeFragment()
            }
            composable(qaBottomItem.route) {
                QaFragmentView()
            }
            composable(sysBottomItem.route) {
                SysFragment()
            }
            composable(projectBottomItem.route) {
                ProjectFragment()
            }
            composable(myBottomItem.route) {
                MyFragmentView()

            }

        }
    }
}

















