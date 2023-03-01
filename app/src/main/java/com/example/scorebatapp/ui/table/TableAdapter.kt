package com.example.scorebatapp.ui.table

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.scorebatapp.R
import com.example.scorebatapp.data.standing.Standings
import com.example.scorebatapp.databinding.ItemLeagueTableBinding

class TableAdapter (
    private val footballList: MutableList<Standings> = mutableListOf(),
    private val clickListener: (Standings) -> Unit
) : RecyclerView.Adapter<TableAdapter.ViewHolder>() {

    fun updateLeagueAdapter(newSeason: List<Standings>) {
        footballList.clear()
        footballList.addAll(newSeason)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val view: ItemLeagueTableBinding) : RecyclerView.ViewHolder(view.root) {
        fun setup(seasonModelItemModel: Standings, clickListener: (Standings) -> Unit) {
            with(itemView) {
                Glide.with(context)
                    .load(seasonModelItemModel.team.logos[0].href)
                    .placeholder(R.drawable.animate_loading)
                    .centerCrop()
                    .override(800,800)
                    .into(view.ivChrist)
            }
            for (i in 0 until seasonModelItemModel.stats.size){
                when (seasonModelItemModel.stats[i].displayName) {
                    "Rank" -> {
                        view.tvPosition.text = seasonModelItemModel.stats[i].displayValue
                    }
                    "Games Played" -> {
                        view.tvPlayed.text = seasonModelItemModel.stats[i].displayValue
                    }
                    "Wins" -> {
                        view.tvWins.text = seasonModelItemModel.stats[i].displayValue
                    }
                    "Draws" -> {
                        view.tvDraws.text = seasonModelItemModel.stats[i].displayValue
                    }
                    "Losses" -> {
                        view.tvLoses.text = seasonModelItemModel.stats[i].displayValue
                    }
                    "Goals For" -> {
                        view.tvGoodgoals.text = seasonModelItemModel.stats[i].displayValue
                    }
                    "Points" -> {
                        view.tvPts.text = seasonModelItemModel.stats[i].displayValue
                    }
                }
            }
            itemView.setOnClickListener {
                clickListener(seasonModelItemModel)
               clickListener(seasonModelItemModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemLeagueTableBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = footballList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setup(footballList[position], clickListener)
    }
}