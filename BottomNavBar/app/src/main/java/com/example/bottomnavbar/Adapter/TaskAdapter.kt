package com.example.bottomnavbar.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavbar.Model.AddTaskModel
import com.example.bottomnavbar.R

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val taskList = ArrayList<AddTaskModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.task_layout,
        parent,false)
        return TaskViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        val currentitem = taskList[position]

        holder.task.text = currentitem.task
        holder.description.text = currentitem.description

    }

    fun updateTaskList(taskList: ArrayList<AddTaskModel>){

        this.taskList.clear()
        this.taskList.addAll(taskList)
        notifyDataSetChanged()

    }


    override fun getItemCount(): Int {

        return taskList.size
    }


    class TaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val task : TextView = itemView.findViewById(R.id.taskDisplay)
        val description : TextView = itemView.findViewById(R.id.descDisplay)

    }

}