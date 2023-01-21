package com.example.expensesplitter.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.example.expensesplitter.Constants.Constants
import com.example.expensesplitter.Firebase.FirestoreClass
import com.example.expensesplitter.activity.MainActivity
import com.example.expensesplitter.databinding.FragmentAddBinding
import com.example.expensesplitter.models.Expense
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AddFragment : Fragment() {

    var binding : FragmentAddBinding?= null
    var expensesList : ArrayList<Expense> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddBinding.inflate(layoutInflater)

        setCalendar()

       expensesList=  FirestoreClass().getExpense()



        var tag : String = ""
        binding?.spAddFrag?.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tag = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        binding?.addBtn?.setOnClickListener {
            addExpense(tag)
        }


        return binding?.root
    }

    private fun setCalendar(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        binding?.etAddFragDate?.setText("${day}-${month+1}-${year}")
        binding?.etAddFragDate?.setOnClickListener {

            activity?.let { it1 ->
                DatePickerDialog(
                    it1,
                    { _, year, monthOfYear, dayOfMonth ->
                        val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                        binding?.etAddFragDate?.setText(dat)
                    },
                    year, month, day
                )
            }?.show()
        }

    }

    private fun addExpense(tag : String){
        var title = binding?.etAddFragTitle?.text.toString()
        var amt = 0.0
        if(binding?.etAddFragExpense?.text?.isNotEmpty() == true){
            amt= binding?.etAddFragExpense?.text?.toString()?.toDouble()!!
        }

        var desc = "NA"
        if(binding?.etAddFragDesc?.text?.isNotEmpty() == true){
            desc = binding?.etAddFragDesc?.text.toString()
        }

        val date = binding?.etAddFragDate?.text.toString()
        var extra = false

        if(binding?.checkBox?.isChecked == true){
            extra = true
        }

        if(date.isEmpty() || title.isEmpty()){
            Toast.makeText(context, "some fields are empty", Toast.LENGTH_SHORT).show()
            return
        }

        val expense = Expense(title,amt,desc,tag,date,extra)

        (activity as MainActivity).showProgressDialog("Adding Expense")

        var expenseHashMap = HashMap<String , Any>()

        expensesList.add(expense)
        expenseHashMap[Constants.EXP_LIST] = expensesList

        FirestoreClass().addOrUpdateExpense(activity as MainActivity ,this,expenseHashMap)
    }


    fun expAddedSuccessfully() {
        Log.e("fragment", activity.toString())
        (activity as MainActivity).hideProgressDialog()
        binding?.etAddFragTitle?.setText("")
        setCalendar()
        binding?.checkBox?.isChecked = false
        binding?.etAddFragDesc?.setText("")
        binding?.etAddFragExpense?.setText("0")
        Toast.makeText(context, "expense added !", Toast.LENGTH_SHORT).show()
    }

}
