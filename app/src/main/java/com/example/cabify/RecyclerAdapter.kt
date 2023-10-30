package com.example.cabify

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(context: Context, val resource: Int, val objects: ArrayList<Items>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.title)
        val imgId = itemView.findViewById<ImageView>(R.id.imgView)
        val seats = itemView.findViewById<TextView>(R.id.seats)
        val price = itemView.findViewById<TextView>(R.id.price)
        val time = itemView.findViewById<TextView>(R.id.time)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(resource, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val myObj = objects[position]
        holder.imgId.setImageResource(myObj.imgId)
        holder.title.text = myObj.title
        holder.imgId.setImageResource(myObj.imgId)
        holder.seats.text = myObj.seats + " seats"
        holder.price.text = "₹ " + myObj.rate + " km/hr"
        holder.time.text = myObj.time + " min"

    }

    override fun getItemCount(): Int {
        return objects.size
    }
}