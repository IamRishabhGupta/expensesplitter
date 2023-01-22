package com.example.expensesplitter.fragments

import android.os.Bundle
import android.util.Log
import android.util.Log.e
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensesplitter.Firebase.FirestoreClass
import com.example.expensesplitter.R
import com.example.expensesplitter.databinding.FragmentAddBinding
import com.example.expensesplitter.databinding.FragmentSplitFriendBinding
import com.example.pocketmanager.Adapter.FragFriendListAdapter


class splitFragmentFriend : Fragment() {
    var binding : FragmentSplitFriendBinding?= null
    var nameList:ArrayList<String> = ArrayList()

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

        FirestoreClass().getFriendsFrag(this)


        return binding?.root
    }

    fun addFriend(){

        if(binding?.newFriendEmail?.text?.isNotEmpty() == true){
            val email:String=binding?.newFriendEmail?.text?.toString()!!
            FirestoreClass().getAddFriends(email)
            FirestoreClass().getFriendsFrag(this)

        }else{
            Toast.makeText(requireContext(), "pls enter email", Toast.LENGTH_SHORT).show()
        }
    }

    fun getFriendsName(friendsName : ArrayList<String>){
        nameList=friendsName
        e("name",nameList.toString())
        binding?.friendRecyclerView?.adapter=FragFriendListAdapter(nameList)
        binding?.friendRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.friendRecyclerView?.visibility=View.VISIBLE
    }

}