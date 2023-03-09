package com.example.scorebatapp.ui.video

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.scorebatapp.databinding.FragmentVideoBinding
import com.example.scorebatapp.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoFragment : Fragment() {

    private var _binding:  FragmentVideoBinding? = null
    private val binding get() = _binding!!


    private val sharedViewModel: HomeViewModel by activityViewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentVideoBinding.inflate(inflater,container,false)

        var url = sharedViewModel.homeObject?.matchviewUrl

        binding.wbVideos.webViewClient = WebViewClient()
        binding.wbVideos.apply {
            url?.let { loadUrl(it) }
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true

        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


