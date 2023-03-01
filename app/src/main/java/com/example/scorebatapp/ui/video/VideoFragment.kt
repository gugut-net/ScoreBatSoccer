package com.example.scorebatapp.ui.video

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.scorebatapp.data.model.VideoItemModel
import com.example.scorebatapp.databinding.FragmentVideoViewerBinding
import com.example.scorebatapp.databinding.ItemVideoBinding
import com.example.scorebatapp.viewModel.HomeViewModel
import com.google.android.exoplayer2.ExoPlayer
import dagger.hilt.android.AndroidEntryPoint

//class VideoFragment : Fragment() {
//
//    private lateinit var player: ExoPlayer
//    private var videoItem: VideoItemModel? = null
//
//    private var _binding: ItemVideoBinding? = null
//
//    private val binding get() = _binding!!
//
//    private val sharedViewModel: HomeViewModel by activityViewModels()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        /**
//         *  Set up the player
//         */
//        player = ExoPlayer.Builder(requireContext()).build()
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = ItemVideoBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        videoItem = sharedViewModel.homeObject?.videos?.firstOrNull()
//        binding.playerView.player = player
//
//        Log.d("VIDEOS", "onViewCreated: ${sharedViewModel.homeObject?.videos}")
//
////        mediaItem = MediaItem.fromUri(Uri.parse(videoItem?.embed))
////        Build the media item and set it to the player mediaItem = MediaItem.fromUri(Uri.parse(videoItem.embed))
////        player.setMediaItem(mediaItem)
//        player.prepare()
//        player.play()
//
//        /**
//         *  Set the video title
//         */
//        binding.videoTitle.text = videoItem?.title
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        player.release()
//    }
//
//}


@AndroidEntryPoint
class VideoFragment : Fragment() {

    private var _binding: FragmentVideoViewerBinding? = null
    private val binding: FragmentVideoViewerBinding
        get() = _binding!!

    private val viewModel: VideoViewerViewModel by viewModels()

    companion object {
        const val TOTAL_LOADING_PROGRESS = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentVideoViewerBinding
            .inflate(inflater, container, false)
            .also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() = binding.run {
        webView.apply {
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            webChromeClient = object : WebChromeClient() {

                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)

                    progressBar.setProgressCompat(newProgress, true)
                    if (newProgress == TOTAL_LOADING_PROGRESS) {
                        progressBar.isVisible = false
                    }
                }
            }
            loadDataWithBaseURL(
                null,
                viewModel.videoViewerProvider.websiteData,
                "text/html",
                "UTF-8",
                null
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
