package br.com.zaptest.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import br.com.zaptest.R
import br.com.zaptest.entities.Immobile

class ImmobileActivity : AppCompatActivity(), ImmobileContract.View {

    private lateinit var vivaRealImmobiles: List<Immobile>
    private lateinit var zapImmobiles: List<Immobile>
    private lateinit var immobilesFragments: MutableList<ImmobilesFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_immobile)

        val interactor = ImmobileInteractor()
        val presenter = ImmobilePresenter(this, interactor)
        presenter.fetchImmobiles()
    }

    override fun setupImmobilesSlider() {
        immobilesFragments = mutableListOf()
        immobilesFragments.add(ImmobilesFragment())
        immobilesFragments.add(ImmobilesFragment())

        val sectionsPagerAdapter = SectionsPagerAdapter(immobilesFragments, this, supportFragmentManager)
        val viewPager: androidx.viewpager.widget.ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
    }

    override fun setupZapImmobiles(immobiles: List<Immobile>) {
        zapImmobiles = immobiles
        immobilesFragments[0].addImmobiles(immobiles)
    }

    override fun setupVivaRealImmobiles(immobiles: List<Immobile>) {
        vivaRealImmobiles = immobiles
        immobilesFragments[1].addImmobiles(immobiles)
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