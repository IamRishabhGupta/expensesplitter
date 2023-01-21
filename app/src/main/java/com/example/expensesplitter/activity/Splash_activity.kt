package com.example.expensesplitter.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log.e
import android.view.WindowManager
import com.example.expensesplitter.Firebase.FirestoreClass
import com.example.expensesplitter.R

class splash_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed({
            e("aa gaya","1 one")
            var currentUserId= FirestoreClass().getCurrentUserId()
            e("aa gaya" , "2 two")
            if(currentUserId.isNotEmpty()){
                e("aa gaya" , "3 Three ${currentUserId}")
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                e("aa gaya" , "2 Four")
                startActivity(Intent(this, IntroActivity::class.java))
            }
            finish()
        },3000)

    }
}