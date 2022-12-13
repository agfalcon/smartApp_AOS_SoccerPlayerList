package kr.ac.kumoh.s20180094.soccerplayerlist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.NetworkImageView

class PlayerAdapter(private val model: PlayerViewModel, private val context: Context): RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view), OnClickListener {
        val playerImage: NetworkImageView = view.findViewById<NetworkImageView>(R.id.img_player)
        val playerName: TextView = view.findViewById<TextView>(R.id.text_name)
        val playerTeam: TextView = view.findViewById<TextView>(R.id.text_team)

        init{
            playerImage.setDefaultImageResId(android.R.drawable.ic_menu_report_image)
            view.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra(PlayerActivity.KEY_NAME, model.players.value?.get(adapterPosition)?.name)
            intent.putExtra(PlayerActivity.KEY_NATION, model.players.value?.get(adapterPosition)?.nation)
            intent.putExtra(PlayerActivity.KEY_TEAM, model.players.value?.get(adapterPosition)?.team)
            intent.putExtra(PlayerActivity.KEY_IMAGE, model.getImageUrl(adapterPosition))
            startActivity(context, intent, null)
        }
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