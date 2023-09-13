/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.experiment.srivats.incentivewear.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.experiment.srivats.incentivewear.presentation.helper.ApiDataModelItem
import com.experiment.srivats.incentivewear.presentation.helper.ApiInterface
import com.experiment.srivats.incentivewear.presentation.screens.HomeView
import com.experiment.srivats.incentivewear.presentation.screens.LoginView
import com.experiment.srivats.incentivewear.presentation.theme.IncentiveWearTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//const val BASE_URL = "http://192.168.1.163:4000/"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        getData()
        setContent {
            WearApp(applicationContext)
        }
    }
}

fun getData(){
    val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(ApiInterface::class.java)

    val retrofitData = retrofitBuilder.getData("95348b")

    retrofitData.enqueue(object : Callback<List<ApiDataModelItem>?> {
        override fun onResponse(
            call: Call<List<ApiDataModelItem>?>,
            response: Response<List<ApiDataModelItem>?>,
        ) {
            val respBody = response.body()!!
            val sb =  StringBuilder()
            for(rb in respBody){
                sb.append(rb.uid)
                sb.append("\n")
            }
            println(sb.toString())
            Log.d("API DATA", sb.toString())
        }

        override fun onFailure(call: Call<List<ApiDataModelItem>?>, t: Throwable) {
            Log.d("Error",t.message.toString())
        }
    })
}

@Composable
fun WearApp(context: Context) {
    val swipeDismissableNavController = rememberSwipeDismissableNavController()
    val sharedPreferences =
        context.applicationContext.getSharedPreferences("isUserLoggedIn", Context.MODE_PRIVATE)
    val loginState = sharedPreferences.contains("isLoggedIn")
    IncentiveWearTheme {
        /* If you have enough items in your list, use [ScalingLazyColumn] which is an optimized
         * version of LazyColumn for wear devices with some added features. For more information,
         * see d.android.com/wear/compose.
         */
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(MaterialTheme.colors.background),
//            verticalArrangement = Arrangement.Center
//        ) {
//            Greeting(greetingName = greetingName)
//        }
        SwipeDismissableNavHost(
            navController = swipeDismissableNavController,
            startDestination = if(!loginState) "Landing" else "Home",
            modifier = Modifier.background(MaterialTheme.colors.background)
        ) {
            composable("Landing") {
//                ScalingLazyColumn(
//                    modifier = Modifier.fillMaxSize(),
//                    contentPadding = PaddingValues(
//                        top = 28.dp,
//                        start = 10.dp,
//                        end = 10.dp,
//                        bottom = 40.dp
//                    ),
//                    verticalArrangement = Arrangement.Center,
//                ) {
//                    items(10) { index ->
//                        Chip(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(top = 10.dp),
//                            label = {
//                                Text(
//                                    modifier = Modifier.fillMaxWidth(),
//                                    color = MaterialTheme.colors.onPrimary,
//                                    text = "Item ${index + 1}"
//                                )
//                            },
//                            onClick = {
//                                swipeDismissableNavController.navigate("Detail")
//                            }
//                        )
//                    }
//                }
                LoginView(swipeDismissableNavController)
            }

            composable("Home") {
                HomeView(navController = swipeDismissableNavController)
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(
//                            top = 60.dp,
//                            start = 8.dp,
//                            end = 8.dp
//                        ),
//                    verticalArrangement = Arrangement.Top
//                ) {
//                    Text(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .align(Alignment.CenterHorizontally),
//                        color = MaterialTheme.colors.primary,
//                        textAlign = TextAlign.Center,
//                        fontSize = 22.sp,
//                        text = "Hello from Details Screen"
//                    )
//                }
            }
        }
    }
}
