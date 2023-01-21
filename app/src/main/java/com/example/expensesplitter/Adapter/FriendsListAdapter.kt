package com.example.expensesplitter.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensesplitter.databinding.FriendRecycleviewItemBinding
import com.example.expensesplitter.databinding.RecyclerviewItemBinding
import com.example.pocketmanager.Adapter.ExpenseListAdapter

open class FriendsListAdapter (
    private val NameList : ArrayList<String> ) :
            RecyclerView.Adapter<FriendsListAdapter.FriendsNameHolder>(){
                class FriendsNameHolder(binding: FriendRecycleviewItemBinding) :
                        RecyclerView.ViewHolder(binding.root){
                            var name = binding.friendItem
                        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsNameHolder {
        return FriendsListAdapter.FriendsNameHolder(
            FriendRecycleviewItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FriendsNameHolder, position: Int) {
        val model = NameList[position]

        holder.name.text = model
    }

    override fun getItemCount(): Int {
        return NameList.size
    }
}