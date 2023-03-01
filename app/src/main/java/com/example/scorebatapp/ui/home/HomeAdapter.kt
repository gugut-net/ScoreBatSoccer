package com.example.scorebatapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.scorebatapp.R
import com.example.scorebatapp.data.model.ResponseItemModel
import com.example.scorebatapp.databinding.ItemHomeBinding
import com.example.scorebatapp.ui.league.LeagueGameAdapter


class HomeAdapter(
    val leagueList: MutableList<ResponseItemModel> = mutableListOf(),
    val clickListener: (ResponseItemModel) -> Unit
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    fun updateAdapterList(newList: List<ResponseItemModel>) {
        leagueList.clear()
        leagueList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val view: ItemHomeBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun setup(responseItemModel: ResponseItemModel) {
            with(itemView) {
                Glide.with(context)
                    .load(responseItemModel.thumbnail)
                    .placeholder(R.drawable.animate_loading)
                    .centerCrop()
                    .into(view.ivTeam)
            }
            view.tvTeam.text = responseItemModel.title
            view.tvCreated.text = responseItemModel.competition

            itemView.setOnClickListener {
                clickListener.invoke(responseItemModel)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemHomeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setup(leagueList[position])
    }

    override fun getItemCount(): Int = leagueList.size


}

