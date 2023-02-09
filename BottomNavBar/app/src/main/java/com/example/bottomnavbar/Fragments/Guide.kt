package com.example.bottomnavbar.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavbar.Adapter.GuideAdapter
import com.example.bottomnavbar.Model.GuideViewModel
import com.example.bottomnavbar.Model.GuideModel
import com.example.bottomnavbar.R


private lateinit var viewModel: GuideViewModel
private lateinit var guideRecyclerView: RecyclerView
lateinit var adapter: GuideAdapter

class Guide : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.example.bottomnavbar.R.layout.fragment_guide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        guideRecyclerView = view.findViewById(R.id.recyclerView)
        guideRecyclerView.layoutManager = LinearLayoutManager(context)
        guideRecyclerView.setHasFixedSize(true)
        adapter = GuideAdapter()
        guideRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(GuideViewModel::class.java)

        viewModel.allGuide.observe(viewLifecycleOwner, Observer {

            adapter.updateGuideList(it as ArrayList<GuideModel>)
        })
    }
}