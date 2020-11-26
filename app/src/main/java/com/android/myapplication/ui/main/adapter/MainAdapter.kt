package com.android.myapplication.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.R
import com.android.myapplication.data.model.Search
import com.bumptech.glide.Glide
import com.android.myapplication.ui.main.adapter.MainAdapter.DataViewHolder
import kotlinx.android.synthetic.main.item_layout.view.*

class MainAdapter(private val search: ArrayList<Search>) : RecyclerView.Adapter<DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(search: Search) {
            itemView.apply {
                textViewUserName.text = search.title
                textViewUserEmail.text = search.year
                Glide.with(imageViewAvatar.context)
                    .load(search.poster)
                    .into(imageViewAvatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))

    override fun getItemCount(): Int = search.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(search[position])
    }

    fun addUsers(search: List<Search>) {
        this.search.apply {
            clear()
            addAll(search)
        }

    }
}