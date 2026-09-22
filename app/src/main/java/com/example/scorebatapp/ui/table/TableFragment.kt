package com.example.scorebatapp.ui.table

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scorebatapp.data.standing.Standings
import com.example.scorebatapp.databinding.FragmentTableBinding
import com.example.scorebatapp.util.ResponseType
import com.example.scorebatapp.ui.league.LeagueViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TableFragment : Fragment() {
    private var _binding: FragmentTableBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TableViewModel by activityViewModels()

    private val mAdapter by lazy {
        TableAdapter {
            Toast.makeText(context, "${it.team.name} clicked!", Toast.LENGTH_SHORT).show()
            viewModel.tableObject = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /**
         *  Inflate the layout for this fragment
         */
        _binding = FragmentTableBinding.inflate(inflater, container, false)

        binding.rvStandings.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        viewModel.result.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseType.Loading -> {
                    Toast.makeText(context, "Premier League. . .!", Toast.LENGTH_SHORT).show()
                }
                is ResponseType.Success<List<Standings>> -> {
                    initViews(it.response)
                }
                is ResponseType.Error -> {
                    Toast.makeText(context, "Premier League. . .!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(context, "PremierLeague. . .!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.getStandingList()

        return binding.root
    }

    private fun initViews(data: List<Standings>){
        data.let {
            mAdapter.updateLeagueAdapter(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}