package br.com.zaptest.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.zaptest.R
import br.com.zaptest.entities.Immobile
import br.com.zaptest.ui.detail.ImmobileDetailActivity
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import kotlin.reflect.KFunction0

class ImmobileAdapter(val loadMoreImobiles: KFunction0<Unit>, private val context: Context) :
    RecyclerView.Adapter<ImmobileAdapter.ViewHolder>() {

    private val immobiles = mutableListOf<Immobile>()
    private var isLoading = false
    private val formatter = DecimalFormat("#,###")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.immobile_item, parent, false))

    override fun getItemCount(): Int = immobiles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val immobile = immobiles[position]

        Picasso.get().load(immobile.images.first()).placeholder(R.drawable.placeholder) .into(holder.image)

        immobile.address.neighborhood.takeIf { it.isNotEmpty() }?.apply {
            holder.neighborhood.text = immobile.address.neighborhood
        } ?: run { holder.neighborhood.text = context.getString(R.string.uninformed_neighborhood) }

        val formattedNumber = formatter.format(immobile.pricingInfos.price.toDouble())
        immobile.pricingInfos.businessType.takeIf { it == "SALE" }?.apply {
            holder.status.text = context.getString(R.string.immobile_for_sale)
            holder.value.text = context.getString(R.string.money_symbol).plus(formattedNumber)
        }

        immobile.pricingInfos.businessType.takeIf { it == "RENTAL" }?.apply {
            holder.status.text = context.getString(R.string.immobile_for_rental)
            holder.value.text = context.getString(R.string.money_symbol)
                .plus(String.format("%.2f", immobile.pricingInfos.price.toDouble()))
                .plus(context.getString(R.string.by_month))
        }

        holder.size.text = immobile.usableAreas.plus("m²")
        holder.bedrooms.text = immobile.bedrooms.plus(" Quartos")
        holder.restrooms.text = immobile.bathrooms.plus(" Banheiros")
        holder.parkingSpace.text = immobile.parkingSpaces.plus(" Vagas")

        immobiles.size.takeIf { position == it - 5 && position > 0 }?.apply {
            loadMoreImobiles()
            isLoading = true
        }

        holder.mainContent.setOnClickListener {
            val intent = Intent(context, ImmobileDetailActivity::class.java)
            intent.putExtra(ImmobileDetailActivity.extra_immobile, immobiles[position])
             context.startActivity(intent)
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
        val parkingSpace = view.findViewById<TextView>(R.id.parking_space)
        val mainContent = view.findViewById<CardView>(R.id.main_content)
    }
}