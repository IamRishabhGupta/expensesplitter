package com.example.expensesplitter.activity

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log.e
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.expensesplitter.Constants.commonUtils
import com.example.expensesplitter.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

open class BaseActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce=false

    private lateinit var mProgressDialog:Dialog

    private lateinit  var loadingDialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    fun showProgressDialog(text:String){
        mProgressDialog= Dialog(this)
        mProgressDialog.setContentView(R.layout.dialog_process)
        mProgressDialog.setTitle(text)
        mProgressDialog.show()
    }
    fun hideProgressDialog(){
        e("Activity", mProgressDialog.toString())
        mProgressDialog.dismiss()
    }

    fun getCurrentUserId():String{
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    fun doubleBackToExit(){
        if(doubleBackToExitPressedOnce){
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce=true
        Toast.makeText(this, "press again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce=false},2000)
    }

    fun showErrorSnackBar(message:String){
        val snackBar=Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_LONG)
        val snackBarView=snackBar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(this,
            R.color.snackbar_background_color
        ))
        snackBar.show()
    }
    fun showLoading(context: Context){
        loadingDialog = commonUtils.showLoadingDialog(context)
    }

    fun hideLoading(){
        loadingDialog?.let {
            if(it.isShowing){
                it.cancel()
            }
        }
    }
}