package com.example.popup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.popup.R
import com.example.popup.model.items

class transactionAdapter(
    private var itemsList: ArrayList<items>,
   // var listener: itemsClickListener
) : RecyclerView.Adapter<transactionAdapter.itemsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): transactionAdapter.itemsViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.transactions_layout,
            parent, false
        )

        return transactionAdapter.itemsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: transactionAdapter.itemsViewHolder, position: Int) {

        val data = itemsList[position]

        holder.itemName.text = data.name
        holder.itemPrice.text = data.price

        holder.itemImage.setImageResource(data.image)

    }


    override fun getItemCount(): Int {

        return itemsList.size
    }

    class itemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemName: TextView = itemView.findViewById(R.id.txtName)
        val itemPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val itemImage: ImageView = itemView.findViewById(R.id.imgLamp)

    }



}