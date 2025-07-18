package it.univaq.soccerfields

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import it.univaq.soccerfields.ui.screen.list.ListScreen
import it.univaq.soccerfields.ui.screen.map.MapScreen
import it.univaq.soccerfields.ui.theme.SoccerFieldsTheme
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SoccerFieldsTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(navController)
                    }
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Screen.List
                    ){
                        composable<Screen.List> {
                            ListScreen(
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        composable<Screen.Map> {
                            MapScreen(
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController
) {

    //Remember così che la variabile non debba essere riaggiornata ogni volta
    val items = remember {
        listOf(
            BottomNavigationItem(
                title = "List",
                icon = Icons.AutoMirrored.Default.List,
                route = Screen.List,
            ),
            BottomNavigationItem(
                title = "Map",
                icon = Icons.Default.LocationOn,
                route = Screen.Map,
            )
        )
    }

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach{
            NavigationBarItem(
                selected = currentRoute == it.route.javaClass.canonicalName,
                onClick = {
                    navController.navigate(it.route){
                        popUpTo(navController.graph.startDestinationId){
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(it.icon, contentDescription = it.title)
                },
                label = {
                    Text(text = it.title)
                }
            )
        }

    }
}

data class BottomNavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: Screen,
)

sealed class Screen {
    @Serializable
    data object List: Screen()

    @Serializable
    data object Map: Screen()
}
