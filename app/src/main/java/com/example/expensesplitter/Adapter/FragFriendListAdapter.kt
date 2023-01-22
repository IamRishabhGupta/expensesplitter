package com.example.pocketmanager.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensesplitter.databinding.FragmentSplitFriendBinding
import com.example.expensesplitter.databinding.FriendNameItemBinding
import com.example.expensesplitter.databinding.FriendRecycleviewItemBinding
import com.example.expensesplitter.databinding.RecyclerviewItemBinding
import com.example.expensesplitter.models.Expense

open class FragFriendListAdapter(
        private var list : ArrayList<String> ) :
        RecyclerView.Adapter<FragFriendListAdapter.ItemExpenseHolder>() {

        class ItemExpenseHolder(binding: FriendNameItemBinding) :
                RecyclerView.ViewHolder(binding.root) {
                val name = binding.friendItem
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemExpenseHolder {
                return ItemExpenseHolder(
                        FriendNameItemBinding.inflate(
                                LayoutInflater.from(parent.context), parent, false
                        )
                )
        }

        override fun onBindViewHolder(holder: ItemExpenseHolder, position: Int) {
                val model = list[position]
                holder.name.text = model
        }

        override fun getItemCount(): Int {
                return list.size
        }
}