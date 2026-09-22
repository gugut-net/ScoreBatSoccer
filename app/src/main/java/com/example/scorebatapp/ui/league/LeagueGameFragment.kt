package com.example.scorebatapp.ui.league

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scorebatapp.data.matches.MatchesModel
import com.example.scorebatapp.databinding.FragmentLeagueBinding
import com.example.scorebatapp.util.ResponseType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeagueGameFragment : Fragment() {


    private var _binding: FragmentLeagueBinding? = null

    private val binding get() = _binding!!

    private val sharedViewModel: LeagueViewModel by activityViewModels()

    private val mAdapter by lazy {
        LeagueGameAdapter {
            /**
             * @Logic
             * From here we move to details
             */
            Toast.makeText(context, "${it.competition} clicked!", Toast.LENGTH_SHORT).show()
            sharedViewModel.tableObject = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeagueBinding.inflate(inflater, container, false)

        binding.rvStandings.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        sharedViewModel.result.observe(viewLifecycleOwner) {
            when(it) {
                is ResponseType.Loading -> {
                    Toast.makeText(context, "Loading. . .!", Toast.LENGTH_SHORT).show()
                }
                is ResponseType.Success -> {
                    initViews(it.response)
                }
                is ResponseType.Error -> {
                    Toast.makeText(context, "Loading. . .!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        sharedViewModel.getMatchesList()

        return binding.root
    }

    private fun initViews(data: List<MatchesModel>) {
        data?.let {
            mAdapter.updateAdapterList(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}