package br.com.zaptest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import br.com.zaptest.R
import br.com.zaptest.entities.Immobile
import com.squareup.picasso.Picasso

class ImmobileAdapter :
    RecyclerView.Adapter<ImmobileAdapter.ViewHolder>() {

    private val immobiles = mutableListOf<Immobile>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.immobile_item, parent, false))

    override fun getItemCount(): Int = immobiles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.value.text = immobiles[position].pricingInfos.price
        Picasso.get().load(immobiles[position].images.first())
    }

    fun immobiles(newImmobiles: List<Immobile>) {
        immobiles.addAll(newImmobiles)
        notifyDataSetChanged()
    }

    fun clearImmobiles() {
        immobiles.clear()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)
        val status = view.findViewById<TextView>(R.id.status)
        val neighborhood = view.findViewById<TextView>(R.id.neighborhood)
        val value = view.findViewById<TextView>(R.id.value)
        val size = view.findViewById<TextView>(R.id.size)
        val bedrooms = view.findViewById<TextView>(R.id.bedrooms)
        val restrooms = view.findViewById<TextView>(R.id.restrooms)
        val parking_space = view.findViewById<TextView>(R.id.parking_space)
    }
}