package com.example.expensesplitter.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensesplitter.Firebase.FirestoreClass
import com.example.expensesplitter.databinding.FriendRecycleviewItemBinding
import com.example.expensesplitter.models.Expense
import com.example.expensesplitter.models.friend

open class FriendsListAdapter (
    private val NameList : ArrayList<friend> ) :
            RecyclerView.Adapter<FriendsListAdapter.FriendsNameHolder>(){
                class FriendsNameHolder(binding: FriendRecycleviewItemBinding) :
                        RecyclerView.ViewHolder(binding.root){
                            var name = binding.friendItem
                    var amt = binding.amtFriend
                        }

    private var onClickListener : OnClickListener?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsNameHolder {
        return FriendsNameHolder(
            FriendRecycleviewItemBinding.inflate(

                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FriendsNameHolder, position: Int) {
        val model = NameList[position]

        holder.name.text = model.name

        holder.itemView.setOnClickListener{
            if (onClickListener != null){
                onClickListener!!.onClick(position,model , holder.amt.text.toString().toDouble())
            }

        }
    }

    override fun getItemCount(): Int {
        return NameList.size
    }

    interface OnClickListener{
        fun onClick(position: Int , friend: friend, amt : Double = 0.00)
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

}