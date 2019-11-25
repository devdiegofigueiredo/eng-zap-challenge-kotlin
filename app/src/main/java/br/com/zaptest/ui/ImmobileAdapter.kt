package br.com.zaptest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.zaptest.R
import br.com.zaptest.entities.Immobile
import com.squareup.picasso.Picasso
import kotlin.reflect.KFunction0

class ImmobileAdapter(val loadMoreImobiles: KFunction0<Unit>) :
    RecyclerView.Adapter<ImmobileAdapter.ViewHolder>() {

    private val immobiles = mutableListOf<Immobile>()
    private var isLoading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.immobile_item, parent, false))

    override fun getItemCount(): Int = immobiles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.value.text = immobiles[position].pricingInfos.price
        Picasso.get().load(immobiles[position].images.first())

        immobiles.size.takeIf { position == it - 5 && position > 0 }?.apply {
            loadMoreImobiles()
            isLoading = true
        }
    }

    fun immobiles(newImmobiles: List<Immobile>) {
        immobiles.addAll(newImmobiles)
        isLoading = false
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