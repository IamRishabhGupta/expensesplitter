package com.example.expensesplitter.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensesplitter.Adapter.splitAdapter
import com.example.expensesplitter.Firebase.FirestoreClass
import com.example.expensesplitter.R
import com.example.expensesplitter.models.money

class FriendStatusActivity : BaseActivity() {

    private var ReqmoneyList : ArrayList<money> = ArrayList()
    private var OwemoneyList : ArrayList<money> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_status)

        var name = intent.hasExtra("name")
        var id = intent.hasExtra("id")

        FirestoreClass().getRequestMoneyDataReq(this)
    }

    fun gotTheListReq(ReqList : ArrayList<money>){
        ReqmoneyList = ReqList
        e("friendActivity me data" , ReqList.toString())

        var adapter = splitAdapter(ReqList)
        var rv = findViewById<RecyclerView>(R.id.rv)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager((this))

    }


}