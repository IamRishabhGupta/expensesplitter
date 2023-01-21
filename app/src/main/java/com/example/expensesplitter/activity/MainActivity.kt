package com.example.pocketmanager.activity

import android.os.Bundle
import com.example.expensesplitter.Constants.Constants
import com.example.expensesplitter.Firebase.FirestoreClass
import com.example.expensesplitter.R
import com.example.expensesplitter.activity.BaseActivity
import com.example.expensesplitter.databinding.ActivityMainBinding
import com.example.expensesplitter.models.Expense
import com.example.pocketmanager.fragments.AddFragment
import com.example.pocketmanager.fragments.HomeFragment


class MainActivity : BaseActivity() {

    var binding : ActivityMainBinding?= null
    var expenseslist : ArrayList<Expense> = ArrayList()
    val bundle=Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        showLoading(this)

        FirestoreClass().getExpense(this)


        var home = HomeFragment()
        home.arguments = bundle
        val add = AddFragment()
        add.arguments = bundle



        var man = supportFragmentManager

        binding?.bottomNavigationView?.setOnNavigationItemSelectedListener {item ->
            when(item.itemId){
                R.id.homeFragment ->{
                    man
                        .beginTransaction()
                        .replace(R.id.fragment_container_view, home)
                        .commit()

                    true
                }
                R.id.addFragment -> {
                    man.beginTransaction().replace(R.id.fragment_container_view , add).commit()

                    true
                }

                else -> {
                    true
                }
            }

        }


    }

    fun getExpenseData(expensesList : ArrayList<Expense>,){

        expenseslist = expensesList

        bundle.putParcelableArrayList(Constants.EXP_LIST,expenseslist)

        val home = HomeFragment()
        home.arguments = bundle
        val add=AddFragment()
        add.arguments=bundle

        hideLoading()
        val man = supportFragmentManager
        man
            .beginTransaction()
            .replace(R.id.fragment_container_view, home)
            .commit()
    }

}