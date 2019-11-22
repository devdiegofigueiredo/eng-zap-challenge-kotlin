package br.com.zaptest.ui

import br.com.zaptest.entities.Immobile

interface ImmobileContract {

    interface View {
        fun setupImmobiles(immobiles: List<Immobile>)
    }

    interface Presenter {
        interface ImmobilesCallback {
            fun onImmobilesSuccess(immobiles: List<Immobile>)
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