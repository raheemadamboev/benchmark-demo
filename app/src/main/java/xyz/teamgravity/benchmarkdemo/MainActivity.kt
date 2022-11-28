package xyz.teamgravity.benchmarkdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import xyz.teamgravity.benchmarkdemo.ui.theme.BenchmarkDemoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BenchmarkDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val controller = rememberNavController()
                    var counter by remember { mutableStateOf(0) }

                    NavHost(
                        navController = controller,
                        startDestination = "start"
                    ) {
                        composable("start") {
                            LazyColumn(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .testTag("item_list")
                            ) {
                                item {
                                    Button(
                                        onClick = { counter++ },
                                    ) {
                                        Text(text = stringResource(id = R.string.click_me))
                                    }
                                }
                                items(counter) { index ->
                                    val text = stringResource(id = R.string.your_element, index)
                                    Text(
                                        text = text,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { controller.navigate("detail/$text") }
                                            .padding(32.dp)
                                    )
                                }
                            }
                        }
                        composable(
                            route = "detail/{text}",
                            arguments = listOf(
                                navArgument("text") {
                                    type = NavType.StringType
                                }
                            )
                        ) { entry ->
                            val text = entry.arguments?.getString("text") ?: stringResource(id = R.string.default_text)
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(text = stringResource(id = R.string.your_detail, text))
                            }
                        }
                    }
                }
            }
        }
    }
}