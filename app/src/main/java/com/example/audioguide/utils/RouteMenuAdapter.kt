package com.example.audioguide.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.audioguide.R
import com.example.audioguide.model.Route
import com.squareup.picasso.Picasso

/**
 * Адаптер для правильного отборажения списка маршрутов в меню
 */
class RouteMenuAdapter(private val listRoutes: List<Route>, private val context: Context)
    : RecyclerView.Adapter<RouteMenuAdapter.RouteViewHolder>(){
    class RouteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView : ImageView = itemView.findViewById(R.id.imageView)
        val textView : TextView = itemView.findViewById(R.id.textView)
    }

    /**
     * Установка адаптера
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_route_menu, parent, false)
        return RouteViewHolder(view)
    }

    /**
     * Количество маршрутов
     */
    override fun getItemCount(): Int {
        return listRoutes.size
    }

    /**
     * Установка картинок и текста у каждого маршрута
     */
    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        //TODO Установить правильные изображения, а не только котиков ))))
        val route = listRoutes[position]
        val drawable: Drawable? = Drawable.createFromPath(listRoutes[position].listPathsImage[0])
//        println(listRoutes[position].listPathsImage[0])
//        holder.imageView.setImageResource(R.drawable.test_photo)
        ;
        Picasso.get()
            .load(listRoutes[position].listPathsImage[0])
            .into(holder.imageView)
//        holder.imageView.setImageDrawable(context.getDrawable());
        holder.textView.text = route.nameRoute
    }
}