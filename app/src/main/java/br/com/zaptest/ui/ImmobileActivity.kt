package br.com.zaptest.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import br.com.zaptest.R
import br.com.zaptest.entities.Immobile
import kotlinx.android.synthetic.main.activity_immobile.*

class ImmobileActivity : AppCompatActivity(), ImmobileContract.View {

    private lateinit var presenter: ImmobilePresenter
    private lateinit var vivaRealImmobiles: List<Immobile>
    private lateinit var zapImmobiles: List<Immobile>
    private lateinit var immobilesFragments: MutableList<ImmobilesFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_immobile)

        setupToolbar()

        val interactor = ImmobileInteractor()
        presenter = ImmobilePresenter(this, interactor)
        presenter.fetchImmobiles()

        try_again.setOnClickListener { presenter.fetchImmobiles() }
    }

    override fun setupImmobilesSlider() {
        immobilesFragments = mutableListOf()
        immobilesFragments.add(ImmobilesFragment(0, this::onLoadMoreImmobilies))
        immobilesFragments.add(ImmobilesFragment(1, this::onLoadMoreImmobilies))

        val sectionsPagerAdapter = SectionsPagerAdapter(immobilesFragments, this, supportFragmentManager)
        val viewPager: androidx.viewpager.widget.ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)
    }

    override fun setupZapImmobiles(immobiles: List<Immobile>) {
        zapImmobiles = immobiles
        immobilesFragments[0].addImmobiles(immobiles)
    }

    override fun setupVivaRealImmobiles(immobiles: List<Immobile>) {
        vivaRealImmobiles = immobiles
        immobilesFragments[1].addImmobiles(immobiles)
    }

    override fun addMoreImmoobilies(position: Int, immobilies: List<Immobile>) {
        immobilesFragments[position].addImmobiles(immobilies)
    }

    override fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading.visibility = View.GONE
    }

    override fun showTabs() {
        tabs.visibility = View.VISIBLE
    }

    override fun showErrorScreen() {
        error_content.visibility = View.VISIBLE
    }

    override fun hideErrorScreen() {
        error_content.visibility = View.GONE
    }

    override fun displayFastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun onLoadMoreImmobilies(position: Int) {
        presenter.loadMoreImmobiles(position)
    }

    private fun setupToolbar() {
        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)
    }

    class SectionsPagerAdapter(
        private val immobilesFragments: List<Fragment>,
        private val context: Context,
        fm: FragmentManager
    ) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val titles = arrayOf(R.string.zap, R.string.viva_real)

        override fun getItem(position: Int): Fragment {
            return immobilesFragments[position]
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return context.resources.getString(titles[position])
        }

        override fun getCount(): Int {
            return titles.size
        }
    }

}