package com.experiment.srivats.incentivewear.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.LocalContentColor
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import com.experiment.srivats.incentivewear.presentation.BASE_URL
import com.experiment.srivats.incentivewear.presentation.helper.user.UserDataModelItem
import com.experiment.srivats.incentivewear.presentation.helper.user.UserInterface
import com.experiment.srivats.incentivewear.presentation.helper.user.UserStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun LoginView(navController: NavController){
    var isLoading = remember { mutableStateOf(false) }
    Scaffold(
        timeText = { TimeText() },
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
    ) {
        var text by rememberSaveable { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val context = LocalContext.current

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(isLoading.value){
                CircularProgressIndicator()
            }
            else{
                Text("Welcome To Incentive")
                Spacer(modifier = Modifier.height(5.dp))
                BasicTextField(
                    value = text,
                    textStyle = TextStyle(color = LocalContentColor.current),
                    onValueChange = {
                        //isReady = it.length>11
                        text = it
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 25.dp, end = 25.dp)
                                .border(
                                    width = 2.dp,
                                    color = Color(0xFFAAE9E6),
                                    shape = RoundedCornerShape(size = 16.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 12.dp), // inner padding
                        ) {
                            if (text.isEmpty()) {
                                Text(
                                    text = "Enter Code",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.LightGray
                                )
                            }
                            innerTextField()
                        }
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                Button(onClick = {
                    if(text.isEmpty()){
                        Toast.makeText(context, "Enter Code", Toast.LENGTH_LONG).show()
                    }
                    else{
                        isLoading.value = true
                        val store = UserStore(context)
                        val retrofitBuilder = Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(BASE_URL)
                            .build()
                            .create(UserInterface::class.java)

                        val retrofitData = retrofitBuilder.getData(text)

                        retrofitData.enqueue(object : Callback<List<UserDataModelItem>?> {
                            override fun onResponse(
                                call: Call<List<UserDataModelItem>?>,
                                response: Response<List<UserDataModelItem>?>,
                            ) {
                                val respBody = response.body()!!
                                var userUid  = ""
                                for(rb in respBody){
                                    userUid = rb.uid
                                }
                                CoroutineScope(Dispatchers.IO).launch {
                                    store.saveAuthState(true)
                                    store.saveToken(userUid)
                                }
                                navController.navigate("Home")
                            }

                            override fun onFailure(call: Call<List<UserDataModelItem>?>, t: Throwable) {
                                Toast.makeText(context, "An Error Occurred, Try Again", Toast.LENGTH_LONG).show()
                                isLoading.value = false
                                Log.d("Error",t.message.toString())
                            }
                        })
                    }
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, "continue",
                        tint = Color.White)
                }
            }
        }
    }
}
