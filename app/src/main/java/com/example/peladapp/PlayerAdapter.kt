package com.example.peladapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.peladapp.model.Player

interface PlayerItemClickListener {
    fun onPlayerItemClick(player: Player, recyclerViewId: Int)
}

class PlayerAdapter(
    private val playerList: List<Player>,
    private val clickListener: PlayerItemClickListener? = null,
    private val recyclerViewId: Int = 0
) : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player = playerList[position]
        holder.bind(player)

        // Set click listener if available
        clickListener?.let { clickListener ->
            holder.itemView.setOnClickListener {
                clickListener.onPlayerItemClick(player, recyclerViewId)
            }
        }
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)

        fun bind(player: Player) {
            if (player.name != "" && player.name != null) {
                nameTextView.text = player.name
            } else {
                nameTextView.text = player.email
            }
        }
    }
}
