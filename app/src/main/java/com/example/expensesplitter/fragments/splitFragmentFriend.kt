package com.example.expensesplitter.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log.e
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensesplitter.Adapter.FragFriendListAdapter
import com.example.expensesplitter.Constants.Constants
import com.example.expensesplitter.Firebase.FirestoreClass
import com.example.expensesplitter.activity.FriendStatusActivity
import com.example.expensesplitter.databinding.FragmentSplitFriendBinding
import com.example.expensesplitter.models.friend


class splitFragmentFriend : Fragment() {
    var binding : FragmentSplitFriendBinding?= null
    var nameList:ArrayList<friend> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSplitFriendBinding.inflate(layoutInflater)

        FirestoreClass().getFriendsFrag(this)

        binding?.llAddfrndSecond?.visibility=View.GONE

        binding?.newFriendBtn?.setOnClickListener{
            binding?.llAddfrndSecond?.visibility=View.VISIBLE
            binding?.newFriendBtn?.visibility = View.GONE
        }

        binding?.btnNewFriendEmail?.setOnClickListener {
            addFriend()
        }


        return binding?.root
    }

    fun addFriend(){

        if(binding?.newFriendEmail?.text?.isNotEmpty() == true){
            val email:String=binding?.newFriendEmail?.text?.toString()!!
            FirestoreClass().getAddFriends(email,this)
            FirestoreClass().getFriendsFrag(this)

        }else{
            Toast.makeText(requireContext(), "pls enter email", Toast.LENGTH_SHORT).show()
        }
    }

    fun getFriendsName(friendsName : ArrayList<friend>){
        nameList=friendsName
        e("name",nameList.toString())
        var adapter  = FragFriendListAdapter(nameList)
        adapter.setOnClickListener(object : FragFriendListAdapter.OnClickListener{
            override fun onClick(model: friend) {
                var intent = Intent(requireContext(),FriendStatusActivity::class.java)
                intent.putExtra("name",model.name)
                intent.putExtra(Constants.ID,model.id)
                startActivity(intent)
            }
        })
        binding?.friendRecyclerView?.adapter= adapter
            binding?.friendRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
            binding?.friendlinearlayout?.visibility=View.VISIBLE
    }

}