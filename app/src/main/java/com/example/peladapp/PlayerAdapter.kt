package com.example.peladapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.peladapp.model.Player

/**
 * Interface for handling item click events in the [PlayerAdapter].
 */
interface PlayerItemClickListener {
    /**
     * Called when a player item is clicked.
     *
     * @param player The clicked player.
     * @param recyclerViewId The ID of the RecyclerView associated with the clicked item.
     */
    fun onPlayerItemClick(player: Player, recyclerViewId: Int)
}


/**
 * Adapter class for displaying a list of players in a RecyclerView.
 *
 * @param playerList The list of players to display.
 * @param clickListener The listener for item click events.
 * @param recyclerViewId The ID of the RecyclerView associated with this adapter.
 */
class PlayerAdapter(
    private val playerList: List<Player>,
    private val clickListener: PlayerItemClickListener? = null,
    private val recyclerViewId: Int = 0
) : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    /**
     * Creates a new ViewHolder instance for each item view.
     *
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    /**
     * Binds the data to the ViewHolder at the specified position.
     *
     * @param holder The ViewHolder to bind the data.
     * @param position The position of the item in the dataset.
     */
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

    /**
     * Gets the total number of items in the dataset held by the adapter.
     *
     * @return The total number of items.
     */
    override fun getItemCount(): Int {
        return playerList.size
    }

    /**
     * ViewHolder class for holding item views.
     *
     * @param itemView The item view.
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)

        /**
         * Binds player data to the item view.
         *
         * @param player The player object to bind.
         */
        fun bind(player: Player) {
            if (player.name != "" && player.name != null) {
                nameTextView.text = player.name
            } else {
                nameTextView.text = player.email
            }
        }
    }
}
