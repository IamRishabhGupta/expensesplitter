package com.example.expensesplitter.fragments

import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.expensesplitter.Constants.Constants
import com.example.expensesplitter.R
import com.example.expensesplitter.activity.TransitionHistoryActivity
import com.example.expensesplitter.databinding.FragmentHomeBinding
import com.example.expensesplitter.models.Expense


class HomeFragment : Fragment() {

    var binding: FragmentHomeBinding? = null
    var expensesList: ArrayList<Expense> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        expensesList = arguments?.getParcelableArrayList(Constants.EXP_LIST)!!

        e("Home Fragment" , expensesList.toString())

        binding?.cvHistory?.setOnClickListener {
            var intent = Intent(requireContext(), TransitionHistoryActivity::class.java)
            startActivity(intent)
        }

        var sumArray=expenseThisMonth(expensesList)
        binding?.tvHomeSum?.text=sumArray[0].toString()
        binding?.tvAmtDining?.text=sumArray[1].toString()
        binding?.tvAmtLifeStyle?.text=sumArray[2].toString()
        binding?.tvAmtBills?.text=sumArray[3].toString()
        binding?.tvAmtOthers?.text=sumArray[4].toString()
        e("sum",sumArray.toString())

        slideup(requireContext())

        return binding?.root
    }

    fun expenseThisMonth(expensesList: ArrayList<Expense>):ArrayList<Double>{
        var sum:Double=0.0
        var sumDining=0.0
        var sumLifeStyle=0.0
        var sumBills=0.0
        var sumOthers=0.0

        val c = Calendar.getInstance()
        val month = c.get(Calendar.MONTH)

        for(i in expensesList){
            var date:String=i.date
            var s:String=""
            for(j in 0..date.length){
                if(date[j]=='-' && date[j+2]=='-'){
                    s+=date[j+1]
                    break
                }else if(date[j]=='-'){
                    s+=date[j+1]
                    s+=date[j+2]
                    break;
                }
            }
            if(s==(month+1).toString()){
                sum+=i.amount
            }
            if(s==(month+1).toString() && i.tag=="Dining"){
                sumDining+=i.amount
            }
            if(s==(month+1).toString() && i.tag=="LifeStyle"){
                sumLifeStyle+=i.amount
            }
            if(s==(month+1).toString() && i.tag=="Bills"){
                sumBills+=i.amount
            }
            if(s==(month+1).toString() && i.tag=="Others"){
                sumOthers+=i.amount
            }
        }
        return arrayListOf(sum,sumDining,sumLifeStyle,sumBills,sumOthers)
    }

    fun replaceFragmentWithAnimation(fragment: Fragment, tag: String) {
        var transaction = parentFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.enter_from_left,
            R.anim.exit_to_right,
            R.anim.enter_from_right,
            R.anim.exit_to_left
        );
        transaction.replace(R.id.fragment_container_view, fragment)
        transaction.setReorderingAllowed(true)
        transaction.addToBackStack(tag)
        transaction.commit()
    }
    fun slideup(context: Context){
        val animation = AnimationUtils.loadAnimation(
            context, R.anim.enter_from_right)
        binding?.cvDining?.startAnimation(animation)
        binding?.cvLifeStyle?.startAnimation(animation)
        binding?.cvBills?.startAnimation(animation)
        binding?.cvOthers?.startAnimation(animation)

    }
}