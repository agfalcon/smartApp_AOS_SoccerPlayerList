package kr.ac.kumoh.s20180094.soccerplayerlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.NetworkImageView

class PlayerAdapter(private val model: PlayerViewModel): RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val playerImage: NetworkImageView = view.findViewById(R.id.img_player)
        val playerName: TextView = view.findViewById(R.id.text_name)
        val playerTeam: TextView = view.findViewById(R.id.text_team)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.playerName.text = model.players.value?.get(position)?.name
        holder.playerTeam.text = model.players.value?.get(position)?.team
        holder.playerImage.setImageUrl(model.getImageUrl(position), model.imageLoader)
    }

    override fun getItemCount(): Int = model.players.value?.size ?: 0
}