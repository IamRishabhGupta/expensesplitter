package com.example.expensesplitter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.expensesplitter.Firebase.FirestoreClass
import com.example.expensesplitter.R
import com.example.expensesplitter.databinding.FragmentAddBinding
import com.example.expensesplitter.databinding.FragmentSplitFriendBinding


class splitFragmentFriend : Fragment() {
    var binding : FragmentSplitFriendBinding?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplitFriendBinding.inflate(layoutInflater)

        binding?.llAddfrndFirst?.visibility=View.VISIBLE
        binding?.llAddfrndSecond?.visibility=View.GONE

        binding?.newFriendBtn?.setOnClickListener{
            binding?.llAddfrndFirst?.visibility=View.GONE
            binding?.llAddfrndSecond?.visibility=View.VISIBLE
        }

        binding?.btnNewFriendEmail?.setOnClickListener {
            addFriend()
        }

        return binding?.root
    }

    fun addFriend(){
        if(binding?.newFriendEmail?.text?.isNotEmpty() == true){
            val email:String=binding?.newFriendEmail?.text?.toString()!!
            FirestoreClass().getAddFriend(email)
        }else{
            Toast.makeText(requireContext(), "pls enter email", Toast.LENGTH_SHORT).show()
        }

    }

}