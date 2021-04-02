package com.codewithsouma.bookhub.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codewithsouma.bookhub.R
import com.codewithsouma.bookhub.model.Book

class DashboardRecyclerAdapter(val context:Context, private val itemList: ArrayList<Book>) : RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>(){

    class DashboardViewHolder(view: View): RecyclerView.ViewHolder(view){
        val bookName: TextView = view.findViewById(R.id.txtBookName)
        val bookAuthor: TextView = view.findViewById(R.id.txtBookAuthor)
        val bookPrice: TextView = view.findViewById(R.id.txtBookPrice)
        val bookRating: TextView = view.findViewById(R.id.txtBookRating)
        val bookImage: ImageView = view.findViewById(R.id.imgBookImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row,parent,false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val book = itemList[position]
        holder.bookName.text = book.bookName
        holder.bookAuthor.text = book.bookAuthor
        holder.bookPrice.text = book.bookCost
        holder.bookRating.text = book.bookRating
        holder.bookImage.setImageResource(book.bookImage)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}