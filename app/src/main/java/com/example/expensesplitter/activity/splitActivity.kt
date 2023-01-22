package com.example.expensesplitter.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.expensesplitter.Constants.Constants
import com.example.expensesplitter.Firebase.FirestoreClass
import com.example.expensesplitter.R
import com.example.expensesplitter.databinding.ActivityMainBinding
import com.example.expensesplitter.databinding.ActivitySplitBinding
import com.example.expensesplitter.fragments.AddFragment
import com.example.expensesplitter.fragments.HomeFragment
import com.example.expensesplitter.fragments.splitFragmentFriend
import com.example.expensesplitter.fragments.splitFragmentGroup

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
        val group = splitFragmentGroup()

        man
            .beginTransaction()
            .replace(R.id.fragment_split_view, friend)
            .commit()

        binding?.bottomNavigationView?.setOnNavigationItemSelectedListener {item ->
            when(item.itemId){
                R.id.addFriend ->{
                    man
                        .beginTransaction()
                        .replace(R.id.fragment_split_view,friend)
                        .commit()

                    true
                }
                R.id.addGroup -> {
                    man.beginTransaction().replace(R.id.fragment_split_view,group ).commit()

                    true
                }

                else -> {
                    true
                }
            }

        }
    }



}