package com.example.scorebatapp.ui.league

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.scorebatapp.R
import com.example.scorebatapp.data.matches.MatchesModel
import com.example.scorebatapp.data.matches.MatchesResponseModel
import com.example.scorebatapp.databinding.ItemLeagueMatchBinding

class LeagueGameAdapter(
    val leagueList: MutableList<MatchesModel> = mutableListOf(),
    val clickListener: (MatchesModel) -> Unit
) : RecyclerView.Adapter<LeagueGameAdapter.ViewHolder>() {

    inner class ViewHolder(private val view: ItemLeagueMatchBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun setup(responseItemModel: MatchesModel) {
            with(itemView) {
                Glide.with(context)
                    .load(responseItemModel.id)
                    .placeholder(R.drawable.animate_loading)
                    .centerCrop()
                    .into(view.ivAwayTeam)

                with(itemView) {
                    Glide.with(context)
                        .load(responseItemModel.id)
                        .placeholder(R.drawable.animate_loading)
                        .centerCrop()
                        .into(view.ivHomeTeam)
                }
            }



//            view.tvDate.text = responseItemModel.title
//
//            view.tvAwayTeam.text = responseItemModel.date
//            view.tvHomeTeam.text = responseItemModel.title
//            view.tvScore.text = responseItemModel.competition

            itemView.setOnClickListener {
                clickListener.invoke(responseItemModel)
            }
        }
    }

    fun updateAdapterList(newList: List<MatchesModel>) {
        leagueList.clear()
        leagueList.addAll(newList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemLeagueMatchBinding.inflate(
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
