package com.example.expensesplitter.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import com.example.expensesplitter.Constants.Constants
import com.example.expensesplitter.Firebase.FirestoreClass
import com.example.expensesplitter.R
import com.example.expensesplitter.databinding.ActivityMainBinding
import com.example.expensesplitter.fragments.AddFragment
import com.example.expensesplitter.fragments.HomeFragment
import com.example.expensesplitter.models.Expense
import com.google.firebase.auth.FirebaseAuth

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

        binding?.ivFriends?.setOnClickListener{
            startActivity(Intent(this,splitActivity::class.java))
        }

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

                R.id.statsFragment -> {

                    val builder = AlertDialog.Builder(this)
                    builder.setTitle(" Alert")
                    builder.setMessage("are you sure you want to sign out")
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

                    builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                        FirebaseAuth.getInstance().signOut()
                        val intent=Intent(this,IntroActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                    builder.setNegativeButton(android.R.string.no) { dialog, which ->
                        dialog.dismiss()
                    }

                    builder.show()





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