package com.example.expensesplitter.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log.e
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensesplitter.Firebase.FirestoreClass
import com.example.expensesplitter.R
import com.example.expensesplitter.models.Expense
import com.example.pocketmanager.Adapter.ExpenseListAdapter
import com.google.android.material.tabs.TabLayout

class TransitionHistoryActivity : BaseActivity(){

    var exp : ArrayList<Expense> = ArrayList()
    var adapterExpList : ArrayList<Expense> = ArrayList()
    var adapter : ExpenseListAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_history)

        findViewById<TextView>(R.id.splitCancelBtn).setOnClickListener {
            val rv = findViewById<RecyclerView>(R.id.rv_trans_history)
            rv.visibility = View.VISIBLE
            findViewById<LinearLayout>(R.id.friends).visibility = View.GONE
        }




        FirestoreClass().getExpense(this)

        findViewById<TabLayout>(R.id.tl_parent).addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {

                var ch : Boolean = true

                @SuppressLint("NotifyDataSetChanged")
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    var id = tab?.position
                    val rv = findViewById<RecyclerView>(R.id.rv_trans_history)
                    if(id == 0){
                        adapter?.setItems(exp)
                        adapter?.notifyDataSetChanged()
                    }
                    if(id == 1) {
                        for(item in exp){
                            if(item.tag == "Dining"){
                                adapterExpList.add(item)
                            }
                        }
                        adapter?.setItems(adapterExpList)
                        adapter?.notifyDataSetChanged()
                    }
                    else if (id == 2) {
                        for(item in exp){
                            if(item.tag == "Bills"){
                                adapterExpList.add(item)
                            }
                        }
                        adapter?.setItems(adapterExpList)
                        adapter?.notifyDataSetChanged()
                    }
                    else if (id == 3) {
                        for(item in exp){
                            if(item.tag == "LifeStyle"){
                                adapterExpList.add(item)
                            }
                        }
                        adapter?.setItems(adapterExpList)
                        adapter?.notifyDataSetChanged()
                    }
                    else if (id == 4) {
                        for(item in exp){
                            if(item.tag == "Others"){
                                adapterExpList.add(item)
                            }
                        }
                        adapter?.setItems(adapterExpList)
                        adapter?.notifyDataSetChanged()
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    ch = true
                    adapterExpList.clear()
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            }
        )

        FirestoreClass().getFriends(this)
    }

    fun getExpenseListData(expenseList : ArrayList<Expense>){
        exp = expenseList
        e("result by transaction",exp.toString())

        setExpenseListAdapter(exp)
    }


    fun setExpenseListAdapter(exp : ArrayList<Expense>){

        val rv = findViewById<RecyclerView>(R.id.rv_trans_history)
        adapter = ExpenseListAdapter(exp)
        e("rv laouuo4" , exp.toString())

        adapter?.setOnClickListener(object  : ExpenseListAdapter.OnClickListener{
            override fun onClick(position: Int, expense: Expense) {
                val rv = findViewById<RecyclerView>(R.id.rv_trans_history)
                rv.visibility = View.GONE
                findViewById<LinearLayout>(R.id.friends).visibility = View.VISIBLE
            }

        })

        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
    }

    fun getFriendsName(friendsName : ArrayList<String>){
        for(i in friendsName){
            e("ye rahe naam",i.toString())
        }
    }


}



