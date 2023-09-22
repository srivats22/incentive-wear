package com.experiment.srivats.incentivewear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.experiment.srivats.incentivewear.ui.theme.IncentiveWearTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.UUID




class SetupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Firebase.firestore
        setContent {
            IncentiveWearTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val uuid = UUID.randomUUID()
    val uuidString = uuid.toString()
    val shortUuid = uuidString.substring(0, 6)
    val db = Firebase.firestore
    val auth = Firebase.auth
    val updateMap = hashMapOf(
        "watchUUID" to shortUuid
    )
    val isWatchUUIDSet = remember { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(true) }
    db.collection("users")
        .document(auth.currentUser!!.uid)
        .get()
        .addOnSuccessListener { result ->
            println("Result: ${result.data!!["watchUUID"]}")
        }
    db.collection("users")
        .document(auth.currentUser!!.uid)
        .set(updateMap, SetOptions.merge())
    Column {
        Text(
            text = "Hello ${auth.currentUser!!.displayName}!",
            modifier = modifier
        )
        Text(
            text = "Enter the below code in the Wear OS App",
            modifier = modifier
        )
        Text(
            text = shortUuid,
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IncentiveWearTheme {
        Greeting()
    }
}