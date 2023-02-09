package com.example.bottomnavbar.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavbar.Model.AddTaskModel
import com.example.bottomnavbar.R
import com.example.bottomnavbar.UpdateTaskActivity
import com.google.firebase.database.core.Context

class TaskAdapter(
    var c: android.content.Context,
    var taskList: ArrayList<AddTaskModel>
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val task : TextView = itemView.findViewById(R.id.taskDisplay)
        val description : TextView = itemView.findViewById(R.id.descDisplay)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.task_layout,
        parent,false)
        return TaskViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        val currentitem = taskList[position]
        val task = currentitem.task
        val description = currentitem.description
        val startDate = currentitem.startDate
        val endDate = currentitem.endDate
        val taskId = currentitem.taskId

        holder.task.text = currentitem.task
        holder.description.text = currentitem.description
        holder.itemView.setOnClickListener() {

            val mIntent = Intent(c,UpdateTaskActivity::class.java)
            mIntent.putExtra("task",task)
            mIntent.putExtra("description",description)
            mIntent.putExtra("startDate",startDate)
            mIntent.putExtra("endDate",endDate)
            mIntent.putExtra("taskId",taskId)
            c.startActivity(mIntent)
        }

    }

    fun updateTaskList(taskList: ArrayList<AddTaskModel>){
        this.taskList.clear()
        this.taskList.addAll(taskList)
        notifyDataSetChanged()

    }


    override fun getItemCount(): Int {
        return taskList.size
    }

}