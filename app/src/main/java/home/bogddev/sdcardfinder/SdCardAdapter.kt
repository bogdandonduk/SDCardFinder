package home.bogddev.sdcardfinder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class SdCardAdapter(val context: Context, val items: Array<File>) : RecyclerView.Adapter<SdCardAdapter.SdCardViewHolder>() {

    class SdCardViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemTitle: TextView

        init {
            itemTitle = itemView.findViewById(R.id.sdCardItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SdCardViewHolder {
        return SdCardViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false))
    }

    override fun onBindViewHolder(holder: SdCardViewHolder, position: Int) {
        holder.itemTitle.text = items[position].name
    }

    override fun getItemCount(): Int {
        return items.size
    }
}