package com.example.expensesplitter.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.expensesplitter.R

class FriendStatusActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_status)

        var name = intent.hasExtra("name")
        var id = intent.hasExtra("id")
    }
}