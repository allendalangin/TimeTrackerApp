package com.example.bottomnavbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.bottomnavbar.Model.AddTaskModel
import com.example.bottomnavbar.databinding.FragmentNewTaskBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddTaskDialogFragment: DialogFragment() {

    private lateinit var binding: FragmentNewTaskBinding
    private lateinit var database: DatabaseReference

    companion object {
        const val TAG = "DialogFragment"

        @JvmStatic
        fun newInstance(task: String? = null, description: String? = null, user: String? = null) =
            AddTaskDialogFragment().apply {
                arguments = Bundle().apply {
                    putString("task", task)
                    putString("description", description)
                    putString("user",user)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveTask.setOnClickListener() {

            val task = binding.taskInput.text.toString()
            val description = binding.descInput.text.toString()
            val user = binding.userInput.text.toString()
            val time = binding.timeInput.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Task")
            val Task = AddTaskModel(task, description, user)
            database.child(task).setValue(Task).addOnSuccessListener {

                binding.taskInput.text.clear()
                binding.userInput.text.clear()
                binding.descInput.text.clear()
                binding.timeInput.text.clear()

                Toast.makeText(activity, "Successfully Saved", Toast.LENGTH_SHORT).show()

                dialog?.dismiss()

            }.addOnFailureListener {

                Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show()

            }
        }

        binding.closeTask.setOnClickListener() {
                dialog?.dismiss()
            }


        }
    }