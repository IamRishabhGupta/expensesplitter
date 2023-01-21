package com.example.expensesplitter.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.expensesplitter.R

open class BaseFragment : Fragment() {

    private lateinit var mProgressDialog:Dialog

    var num = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    fun showProgressDialog(context: Context,text:String){
        mProgressDialog= Dialog(context)
        mProgressDialog.setContentView(R.layout.dialog_process)
        mProgressDialog.setTitle(text)
        mProgressDialog.show()
    }
    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }

}