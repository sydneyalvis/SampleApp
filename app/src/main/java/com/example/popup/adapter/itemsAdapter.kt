package com.example.popup.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.popup.R
import com.example.popup.model.items
import com.example.popup.utils.itemsClickListener
import com.google.android.material.card.MaterialCardView

class itemsAdapter(
    private var itemsList: ArrayList<items>,
    var listener: itemsClickListener
    ) : RecyclerView.Adapter<itemsAdapter.itemsViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): itemsAdapter.itemsViewHolder {

            val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.items,
                parent, false
            )

            return itemsViewHolder(itemView)
        }

        fun filterList(filterlist: ArrayList<items>) {

            itemsList = filterlist

            notifyDataSetChanged()
        }

        override fun onBindViewHolder(holder: itemsAdapter.itemsViewHolder, position: Int) {
            holder.itemName.text = itemsList.get(position).name

            val data = itemsList[position]
            holder.backCard.setOnClickListener {

                listener.onItemClickLister(data)

            }
            //holder.courseIV.setImageResource(courseList.get(position).courseImg)
        }

        override fun getItemCount(): Int {

            return itemsList.size
        }

        class itemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val itemName: TextView = itemView.findViewById(R.id.name)
            val backCard: MaterialCardView = itemView.findViewById(R.id.cardView)

            //val courseIV: ImageView = itemView.findViewById(R.id.idIVCourse)

        }
    }
