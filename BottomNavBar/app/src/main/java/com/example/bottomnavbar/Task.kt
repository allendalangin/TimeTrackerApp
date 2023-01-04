package com.example.bottomnavbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavbar.Adapter.TaskAdapter
import com.example.bottomnavbar.Model.AddTaskModel
import com.example.bottomnavbar.Model.AddTaskViewModel
import com.example.bottomnavbar.databinding.FragmentTasksBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var taskViewModel : AddTaskViewModel
private lateinit var taskRecyclerView: RecyclerView
lateinit var taskAdapter: TaskAdapter

class Tasks : Fragment(){
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentTasksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        taskRecyclerView = view.findViewById(R.id.taskView)
        taskRecyclerView.layoutManager = LinearLayoutManager(context)
        taskRecyclerView.setHasFixedSize(true)
        taskAdapter = TaskAdapter()
        taskRecyclerView.adapter = taskAdapter


        taskViewModel = ViewModelProvider(this).get(AddTaskViewModel::class.java)

        taskViewModel.allUsers.observe(viewLifecycleOwner, Observer {

            taskAdapter.updateTaskList(it as ArrayList<AddTaskModel>)

        })

        binding.addTask.setOnClickListener() {
            val toast = Toast.makeText(activity, "Add Task", Toast.LENGTH_SHORT)
            toast.show()

            val dialog = AddTaskDialogFragment()
            dialog.show((activity as AppCompatActivity).supportFragmentManager, "customDialog")

        }

        binding.all.setOnClickListener() {
            val toast = Toast.makeText(activity, "All", Toast.LENGTH_SHORT)
            toast.show()
        }

        binding.completed.setOnClickListener() {
            val toast = Toast.makeText(activity, "Done", Toast.LENGTH_SHORT)
            toast.show()
        }

        binding.ongoing.setOnClickListener() {
            val toast = Toast.makeText(activity, "Ongoing", Toast.LENGTH_SHORT)
            toast.show()
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Tasks.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            com.example.bottomnavbar.Tasks().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}