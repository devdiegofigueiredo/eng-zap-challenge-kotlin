package br.com.zaptest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.zaptest.R
import br.com.zaptest.entities.Immobile
import kotlinx.android.synthetic.main.fragment_zap_immobiles.*

class ImmobilesFragment : Fragment() {

    private lateinit var adapter: ImmobileAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_zap_immobiles, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupImmobiles()
    }

    private fun setupImmobiles() {
        adapter = ImmobileAdapter()
        immobile_list.layoutManager = LinearLayoutManager(activity)
        immobile_list.adapter = adapter
    }

    fun addImmobiles(newImmobiles: List<Immobile>) {
        adapter.immobiles(newImmobiles)
    }
}