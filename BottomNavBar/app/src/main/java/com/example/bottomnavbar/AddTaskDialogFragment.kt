package com.example.bottomnavbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.bottomnavbar.databinding.FragmentNewTaskBinding
import com.google.android.material.textfield.TextInputEditText
import java.sql.DatabaseMetaData

class AddTaskDialogFragment: DialogFragment() {

    private lateinit var binding: FragmentNewTaskBinding
    private var listener : OnDialogNextBtnClickListener? = null
    private var addTaskModel: AddTaskModel? = null

    fun setListener(listener: OnDialogNextBtnClickListener) {
        this.listener = listener
    }

    companion object {
        const val TAG = "DialogFragment"
        @JvmStatic
        fun newInstance(name: String? = null, date: String? = null, description: String? = null) =
            AddTaskDialogFragment().apply {
                arguments = Bundle().apply {
                    putString("name", name)
                    putString("date", date)
                    putString("description", description)
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

        if (arguments != null){
            addTaskModel = AddTaskModel(arguments?.getString("name").toString() ,arguments?.getString("date").toString(), arguments?.getString("description").toString())
            binding.taskInput.setText(addTaskModel?.name)
            binding.dateInput.setText(addTaskModel?.date)
            binding.descInput.setText(addTaskModel?.description)
        }

        binding.closeTask.setOnClickListener() {
            dialog?.dismiss()
        }

        binding.saveTask.setOnClickListener() {
            val todoTask = binding.taskInput.text.toString()
            if (todoTask.isNotEmpty()){
                if (addTaskModel == null){
                    listener?.saveTask(todoTask , binding.taskInput as TextInputEditText)
                }else{
                    addTaskModel!!.name = todoTask
                    listener?.updateTask(addTaskModel!!, binding.taskInput as TextInputEditText)
                }

            }
        }

    }

    interface OnDialogNextBtnClickListener{
        fun saveTask(todoTask:String , taskInputEditText: TextInputEditText)
        fun updateTask(addTaskModel: AddTaskModel , taskInputEditText: TextInputEditText)
    }
}