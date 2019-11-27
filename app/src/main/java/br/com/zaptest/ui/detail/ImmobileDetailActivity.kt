package br.com.zaptest.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import br.com.zaptest.R
import br.com.zaptest.entities.Immobile
import kotlinx.android.synthetic.main.activity_immobile_detail.*
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ImmobileDetailActivity : AppCompatActivity() {

    companion object {
        const val extra_immobile = "extra_immobile"
    }

    private lateinit var immobile: Immobile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_immobile_detail)

        immobile = intent.extras?.getSerializable(extra_immobile) as Immobile

        setupViewPager(immobile.images)
        setupImmobileInformation()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_location, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_location -> {
                openMap()
            }

            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun openMap() {
        val uri = String.format(Locale.getDefault(), "geo:%f,%f", immobile.address.geoLocation.location.lat, immobile.address.geoLocation.location.lon)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }

    private fun setupImmobileInformation() {
        immobile.address.neighborhood.takeIf { it.isNotEmpty() }?.apply {
            neighborhood.text = immobile.address.neighborhood
        } ?: run { neighborhood.text = getString(R.string.uninformed_neighborhood) }

        val formatter = DecimalFormat("#,###")
        val formattedNumber = formatter.format(immobile.pricingInfos.price.toDouble())
        immobile.pricingInfos.businessType.takeIf { it == "SALE" }?.apply {
            setupToolbar(getString(R.string.immobile_for_sale))
            value.text = getString(R.string.money_symbol).plus(formattedNumber)
        }

        immobile.pricingInfos.businessType.takeIf { it == "RENTAL" }?.apply {
            setupToolbar(getString(R.string.immobile_for_rental))
            value.text = getString(R.string.money_symbol)
                .plus(String.format("%.2f", immobile.pricingInfos.price.toDouble()))
                .plus(getString(R.string.by_month))
            immobile.pricingInfos.monthlyCondoFee.takeIf { it != "" }?.apply {
                condominium_value.text =
                    getString(R.string.condominium_value).plus(String.format("%.2f", this.toDouble()))
            } ?: kotlin.run { condominium_value.visibility = View.GONE }
        }

        immobile.address.city.takeIf { it != "" }?.apply {
            city.text = this
        } ?: kotlin.run { city.text = getString(R.string.uninformed_city) }

        size.text = immobile.usableAreas.plus("m²")
        bedrooms.text = immobile.bedrooms.plus(" Quartos")
        restrooms.text = immobile.bathrooms.plus(" Banheiros")
        parking_space.text = immobile.parkingSpaces.plus(" Vagas")
        created_at.text = getString(R.string.created_at).plus(dateByString(immobile.createdAt))
    }

    private fun setupToolbar(title: String) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewPager(images: List<String>) {
        val sectionsPagerAdapter = SectionsPagerAdapter(images, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
    }

    private fun dateByString(dateString: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale("pt", "BR"))

        try {
            val date = dateFormat.parse(dateString)
            val calendar = Calendar.getInstance()
            calendar.time = date
            return getDateByCalendar(calendar)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return dateString
    }

    private fun getDateByCalendar(calendar: Calendar): String {
        return addZeroIfNecessary(calendar.get(Calendar.DAY_OF_MONTH)) +
                "/" +
                addZeroIfNecessary(calendar.get(Calendar.MONTH) + 1) +
                "/" +
                calendar.get(Calendar.YEAR)
    }

    private fun addZeroIfNecessary(value: Int): String {
        return if (value < 10) {
            "0$value"
        } else {
            value.toString()
        }
    }

    class SectionsPagerAdapter(
        private val images: List<String>,
        fm: FragmentManager
    ) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            return ImmobileImagesFragment(images[position])
        }

        override fun getCount(): Int {
            return images.size
        }
    }
}