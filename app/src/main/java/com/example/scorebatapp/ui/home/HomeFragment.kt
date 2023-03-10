package com.example.scorebatapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewFragment
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scorebatapp.MainActivity
import com.example.scorebatapp.R
import com.example.scorebatapp.data.model.ResponseItemModel
import com.example.scorebatapp.databinding.ActivityMainBinding
import com.example.scorebatapp.databinding.FragmentHomeBinding
import com.example.scorebatapp.util.ResponseType
import com.example.scorebatapp.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: HomeViewModel by activityViewModels()

    private val mAdapter by lazy {
        HomeAdapter {
            Toast.makeText(context, "${it.competition} Home Fragment clicked!", Toast.LENGTH_SHORT).show()
            sharedViewModel.homeObject = it
            findNavController().navigate(R.id.action_navigation_home_to_navigation_highlits)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.rvStandings.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        sharedViewModel.selected.observe(viewLifecycleOwner) {
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

        sharedViewModel.getCompetitionList()
        return binding.root
    }

    private fun initViews(data: List<ResponseItemModel>?) {
        data?.let {
            mAdapter.updateAdapterList(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}