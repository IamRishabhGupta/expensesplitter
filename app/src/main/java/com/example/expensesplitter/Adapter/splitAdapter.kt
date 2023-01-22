package com.example.expensesplitter.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensesplitter.databinding.SplitItemBinding
import com.example.expensesplitter.models.Expense
import com.example.expensesplitter.models.money

class splitAdapter (
        private var list : ArrayList<money> ) :
        RecyclerView.Adapter<splitAdapter.splitItemHolder>() {

        class splitItemHolder(binding: SplitItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            val title = binding.splitTitle
            val amt = binding.splitAmt
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): splitItemHolder {
        return splitItemHolder(
            SplitItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: splitItemHolder, position: Int) {
        val model = list[position]

        holder.title.text = model.title
        holder.amt.text = model.amount.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setItems(List : ArrayList<money>){
        this.list = List
    }
}