package com.example.expensesplitter.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.expensesplitter.databinding.ActivityTransactionHistoryBinding

class TransactionHistoryActivity : AppCompatActivity() {
    var binding : ActivityTransactionHistoryBinding ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)


    }
}