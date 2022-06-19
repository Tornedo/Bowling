package com.iav.bowling.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.iav.bowling.R
import com.iav.bowling.ui.adapter.FrameAdapter
import com.iav.bowling.ui.viewmodel.OngoingGameViewModel
import kotlinx.android.synthetic.main.fragment_scoreboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScoreBoardFragment : Fragment() {

    // Lazy injected MainViewModel
    private val viewModel: OngoingGameViewModel by viewModel()
    private lateinit var adapter: FrameAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scoreboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        setDataObservers()
        viewModel.calculateScore()
        fragmentScorecardResetButton.setOnClickListener {
            viewModel.resetAll()
        }
    }

    private fun setupList() {
        adapter = FrameAdapter()
        fragmentScorecardRecyclerView.adapter = adapter
        fragmentScorecardRecyclerView.layoutManager = StaggeredGridLayoutManager(5, VERTICAL)
    }

    private fun setDataObservers() {
        viewModel.frames.observe(viewLifecycleOwner, Observer {
            adapter.clear()
            adapter.addAll(it.values.toList())
        })
    }

    companion object {
        fun newInstance() = ScoreBoardFragment()
    }
}
