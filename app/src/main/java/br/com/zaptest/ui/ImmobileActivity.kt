package br.com.zaptest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.zaptest.R
import br.com.zaptest.entities.Immobile
import kotlinx.android.synthetic.main.activity_immobile.*

class ImmobileActivity : AppCompatActivity(), ImmobileContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_immobile)

        val interactor = ImmobileInteractor()
        val presenter = ImmobilePresenter(this, interactor)
        presenter.fetchImmobiles()
    }

    override fun setupImmobiles(immobiles: List<Immobile>) {
        val adapter = ImmobileAdapter()
        immobile_list.layoutManager = LinearLayoutManager(this)
        immobile_list.adapter = adapter
        adapter.immobiles(immobiles)
    }

}
