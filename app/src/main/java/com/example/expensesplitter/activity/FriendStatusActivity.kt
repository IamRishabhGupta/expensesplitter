package com.example.expensesplitter.activity

import android.os.Bundle
import android.util.Log.e
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensesplitter.Adapter.splitAdapter
import com.example.expensesplitter.Constants.Constants
import com.example.expensesplitter.Firebase.FirestoreClass
import com.example.expensesplitter.R
import com.example.expensesplitter.models.money
import org.w3c.dom.Text

class FriendStatusActivity : BaseActivity() {

    private var ReqmoneyList : ArrayList<money> = ArrayList()
    private lateinit var adapter: splitAdapter
    var totalReqMoney = 0.0;
    var totalOweMoney = 0.0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_status)

        var name = intent.getStringExtra("name")
        var id : String = intent.getStringExtra(Constants.ID)!!

        findViewById<TextView>(R.id.tv_username).text = name.toString()

        FirestoreClass().getRequestMoneyDataReq(this , id)
        FirestoreClass().getRequestMoneyDataOwe(this , id)
    }

    fun gotTheListReq(ReqList : ArrayList<money>){
        ReqmoneyList = ReqList
        e("friendActivity me data" , ReqList.toString())

        adapter = splitAdapter(ReqList)
        var rv = findViewById<RecyclerView>(R.id.rv)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager((this))

        for (i in ReqList){
            totalReqMoney += i.amount
        }

    }

    fun gotTheListOwe(OweList : ArrayList<money>){
        ReqmoneyList.addAll(OweList)
        e("log",OweList.toString())
        var rv = findViewById<RecyclerView>(R.id.rv)
        adapter.setItems(ReqmoneyList)
        adapter.notifyDataSetChanged()

        for(i in OweList){
            totalOweMoney += i.amount
        }

        SetUpValues()
    }

    fun SetUpValues(){
        findViewById<TextView>(R.id.Req).text = "Total Requested Money - " + totalReqMoney.toString()

        findViewById<TextView>(R.id.Owe).text = "Total Owed Money - " + totalOweMoney.toString()
    }
}