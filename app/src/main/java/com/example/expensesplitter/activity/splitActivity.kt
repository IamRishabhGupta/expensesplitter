package com.example.expensesplitter.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.expensesplitter.Firebase.FirestoreClass
import com.example.expensesplitter.R
import com.example.expensesplitter.databinding.ActivitySplitBinding
import com.example.expensesplitter.fragments.splitFragmentFriend

class splitActivity : AppCompatActivity() {
    var binding : ActivitySplitBinding?= null
    val bundle=Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplitBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        var man = supportFragmentManager

        FirestoreClass().getFriends(this)

        var friend = splitFragmentFriend()

        man
            .beginTransaction()
            .replace(R.id.fragment_split_view, friend)
            .commit()
    }



}