package com.example.scorebatapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.scorebatapp.R
import com.example.scorebatapp.data.model.ResponseItemModel
import com.example.scorebatapp.databinding.ItemHomeBinding
import com.example.scorebatapp.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsAdapter : Fragment() {

    private var _binding: ItemHomeBinding? = null

    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ItemHomeBinding.inflate(inflater, container, false)
        initViews(viewModel.homeObject)

        return binding.root
    }

    /**
     * Glide in let scope function event
     */
    private fun initViews(data: ResponseItemModel?) {
        data?.let { it ->
            binding.ivTeam.let {
                Glide.with(it.context)
                    .load(data.thumbnail)
                    .centerCrop()
                    .placeholder(R.drawable.animate_loading)
                    .error(R.drawable.animate_loading)
                    .centerCrop()
                    .override(300, 330)
                    .into(it)
            }
            binding.tvTeam.text
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}