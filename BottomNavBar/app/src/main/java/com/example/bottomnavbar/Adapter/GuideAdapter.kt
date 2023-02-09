package com.example.bottomnavbar.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavbar.R

class GuideAdapter : RecyclerView.Adapter<GuideAdapter.MyViewHolder>() {

    private val guideList = ArrayList<com.example.bottomnavbar.Model.GuideModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.guidelayout,
            parent, false

        )
        return MyViewHolder(itemView)

    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val title : TextView = itemView.findViewById(R.id.title)
        val step1 : TextView = itemView.findViewById(R.id.step1)
        val step2 : TextView = itemView.findViewById(R.id.step2)
        val step3 : TextView = itemView.findViewById(R.id.step3)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = guideList[position]

        holder.title.text = currentitem.title
        holder.step1.text = currentitem.step1
        holder.step2.text = currentitem.step2
        holder.step3.text = currentitem.step3

    }

    override fun getItemCount(): Int {
        return guideList.size
    }

    fun updateGuideList(guideList : List<com.example.bottomnavbar.Model.GuideModel>){

        this.guideList.clear()
        this.guideList.addAll(guideList)
        notifyDataSetChanged()

    }


}