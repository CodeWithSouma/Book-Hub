package com.codewithsouma.bookhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codewithsouma.bookhub.R
import com.codewithsouma.bookhub.database.BookEntity
import com.squareup.picasso.Picasso

class FavouriteRecyclerAdapter(val context: Context, private val listOfFavouriteBooks: List<BookEntity>): RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>() {
    class FavouriteViewHolder(view: View): RecyclerView.ViewHolder(view){
        val bookImage: ImageView = view.findViewById(R.id.imgFavBookImage)
        val bookName: TextView = view.findViewById(R.id.txtFavBookTitle)
        val bookAuthor: TextView = view.findViewById(R.id.txtFavBookAuthor)
        val bookPrice: TextView = view.findViewById(R.id.txtFavBookPrice)
        val bookRating: TextView = view.findViewById(R.id.txtFavBookRating)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_favorite_single_row,parent,false)
        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val favBook = listOfFavouriteBooks[position]
        Picasso.get().load(favBook.bookImage).error(R.drawable.default_book_cover).into(holder.bookImage)
        holder.bookName.text = favBook.bookName
        holder.bookAuthor.text = favBook.bookAuthor
        holder.bookPrice.text = favBook.bookPrice
        holder.bookRating.text = favBook.bookRating
    }

    override fun getItemCount(): Int {
        return listOfFavouriteBooks.size
    }
}