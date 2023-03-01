package com.example.scorebatapp.ui.video

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.scorebatapp.R
import com.example.scorebatapp.data.model.VideoItemModel
import com.google.gson.Gson

class VideoAdapter(
    private val videoList: List<VideoItemModel>,
    private val context: Context
    ) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.player_view)

        fun bind(video: VideoItemModel) {
            titleTextView.text = video.title
            itemView.setOnClickListener {
                val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                val fragment = VideoFragment()
                val bundle = Bundle()
                bundle.putString("videoItem", Gson().toJson(video))
                fragment.arguments = bundle
                fragmentTransaction.replace(R.id.container, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = videoList[position]
        holder.bind(video)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }
}