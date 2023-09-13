package com.experiment.srivats.incentivewear

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var pBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLogin = findViewById(R.id.loginBtn)
        etEmail = findViewById(R.id.email)
        etPass = findViewById(R.id.pwd)
        pBar = findViewById(R.id.progressBar)

        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener{
            pBar.visibility = View.VISIBLE
            login()
        }
    }

    private fun login(){
//        val i = Intent(this, SetupActivity::class.java)
//        startActivity(i)
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()

        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    val i = Intent(this, SetupActivity::class.java)
                    startActivity(i)
                }
                else{
                    Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
                }
            }
    }
}