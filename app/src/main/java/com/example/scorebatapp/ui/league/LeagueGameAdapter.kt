package com.example.scorebatapp.ui.league

import android.graphics.Insets.add
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.clear
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.example.scorebatapp.R
import com.example.scorebatapp.data.matches.MatchesModel
import com.example.scorebatapp.data.matches.MatchesResponseModel
import com.example.scorebatapp.databinding.ItemLeagueMatchBinding

private const val TAG = "LeagueGameAdapter"
class LeagueGameAdapter(
    val leagueList: MutableList<MatchesModel> = mutableListOf(),
    val clickListener: (MatchesModel) -> Unit
) : RecyclerView.Adapter<LeagueGameAdapter.ViewHolder>() {

    inner class ViewHolder(private val view: ItemLeagueMatchBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun setup(responseItemModel: MatchesModel) {
            view.ivHomeTeamLogo.clear()
            view.ivAwayTeamLogo.clear()
            val cresthome = responseItemModel.homeTeam!!.crest
            Log.d(TAG, "Home: $cresthome")
            if (cresthome!!.lowercase().endsWith(".svg")){
                view.ivHomeTeamLogo.loadSvg(cresthome)
            } else {
                with(itemView) {
                    Glide.with(context)
                        .load(cresthome)
                        .placeholder(R.drawable.animate_loading)
                        .override(900, 900)
                        .centerCrop()
                        .into(view.ivHomeTeamLogo)
                }
            }
                val crestaway = responseItemModel.awayTeam!!.crest
                if (crestaway!!.lowercase().endsWith(".svg")) {
                    view.ivAwayTeamLogo.loadSvg(crestaway)
                }else {
                    with(itemView) {
                        Glide.with(context)
                            .load(crestaway)
                            .placeholder(R.drawable.animate_loading)
                            .override(900,900)
                            .centerCrop()
                            .into(view.ivAwayTeamLogo)
                    }
                }
            view.tvDate.text = responseItemModel.utcDate

            view.tvHomeTeam.text = responseItemModel.homeTeam!!.shortName
            view.tvScoreHome.text = responseItemModel.score!!.fullTime!!.home.toString()
            view.tvAwayTeam.text = responseItemModel.awayTeam!!.shortName
            view.tvScoreAway.text = responseItemModel.score!!.fullTime!!.away.toString()

//            view.tvVs.text = responseItemModel.toString()

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


    fun ImageView.loadSvg(url: String) {
        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
            .build()

        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
    }
}
