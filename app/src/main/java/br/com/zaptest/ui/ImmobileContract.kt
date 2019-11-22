package br.com.zaptest.ui

import br.com.zaptest.entities.Immobile

interface ImmobileContract {

    interface View {
        fun setupZapImmobiles(immobiles: List<Immobile>)
        fun setupVivaRealImmobiles(immobiles: List<Immobile>)
        fun setupImmobilesSlider()
    }

    interface Presenter {
        interface ImmobilesCallback {
            fun onImmobilesSuccess()
            fun onVivaRealImmobilesSuccess(immobiles: List<Immobile>)
            fun onZapImmobilesSuccess(immobiles: List<Immobile>)
            fun onImmobilesError(message: String)
        }

        fun fetchImmobiles()
        fun onDestroy()
    }

    interface Interactor {
        fun fetchImmobiles(callback: Presenter.ImmobilesCallback)
        fun onDestroy()
    }
}