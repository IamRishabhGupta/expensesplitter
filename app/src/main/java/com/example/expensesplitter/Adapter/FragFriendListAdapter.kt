package com.example.expensesplitter.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensesplitter.Adapter.FriendsListAdapter
import com.example.expensesplitter.databinding.FriendNameItemBinding
import com.example.expensesplitter.models.friend

open class FragFriendListAdapter(
        private var list: ArrayList<friend>
) :
        RecyclerView.Adapter<FragFriendListAdapter.ItemExpenseHolder>() {

        class ItemExpenseHolder(binding: FriendNameItemBinding) :
                RecyclerView.ViewHolder(binding.root) {
                val name = binding.friendItem
        }

        private var onClickListener : OnClickListener?= null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemExpenseHolder {
                return ItemExpenseHolder(
                        FriendNameItemBinding.inflate(
                                LayoutInflater.from(parent.context), parent, false
                        )
                )
        }

        override fun onBindViewHolder(holder: ItemExpenseHolder, position: Int) {
                val model = list[position]
                holder.name.text = model.name

                holder.itemView.setOnClickListener{
                        if (onClickListener != null){
                                onClickListener!!.onClick(model)
                        }

                }
        }

        override fun getItemCount(): Int {
                return list.size
        }

        interface OnClickListener{
                fun onClick(model : friend)
        }

        fun setOnClickListener(onClickListener: OnClickListener){
                this.onClickListener = onClickListener
        }
}