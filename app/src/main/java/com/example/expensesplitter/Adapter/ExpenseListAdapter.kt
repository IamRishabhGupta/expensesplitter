package com.example.pocketmanager.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensesplitter.databinding.RecyclerviewItemBinding
import com.example.expensesplitter.models.Expense

open class ExpenseListAdapter(
        private var list : ArrayList<Expense> ) :
        RecyclerView.Adapter<ExpenseListAdapter.ItemExpenseHolder>() {

        class ItemExpenseHolder(binding: RecyclerviewItemBinding) :
                RecyclerView.ViewHolder(binding.root) {
                val title = binding.tvItemTitle
                val desc = binding.tvItemDescription
                val amt = binding.tvItemAmt
                var date = binding.tvOnItem
                val stale = binding.ivItemStale
        }

        private var onClickListener : OnClickListener?= null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemExpenseHolder {
                return ItemExpenseHolder(
                        RecyclerviewItemBinding.inflate(
                                LayoutInflater.from(parent.context), parent, false
                        )
                )
        }

        override fun onBindViewHolder(holder: ItemExpenseHolder, position: Int) {
                val model = list[position]

                holder.date.text = model.date
                holder.title.text = model.title
                holder.desc.text = model.description
                holder.amt.text = model.amount.toString()

                if(model.extra){
                        holder.stale.visibility = View.VISIBLE
                }else{
                        holder.stale.visibility = View.GONE
                }

                holder.itemView.setOnClickListener{
                        if (onClickListener != null){
                                onClickListener!!.onClick(position,model)
                        }
                }
        }


        interface OnClickListener{
                fun onClick(position: Int , expense : Expense)
        }

        fun setOnClickListener(onClickListener: OnClickListener){
                this.onClickListener = onClickListener
        }


        override fun getItemCount(): Int {
                return list.size
        }

        fun setItems(expList : ArrayList<Expense>){
               this.list = expList
        }
}