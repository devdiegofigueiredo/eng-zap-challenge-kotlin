package br.com.zaptest.ui

import br.com.zaptest.entities.Immobile

class ImmobilePresenter(
    private val view: ImmobileContract.View,
    private val interactor: ImmobileInteractor
) : ImmobileContract.Presenter,
    ImmobileContract.Presenter.ImmobilesCallback {

    override fun fetchImmobiles() {
        interactor.fetchImmobiles(this)
    }

    override fun onZapImmobilesSuccess(immobiles: List<Immobile>) {
        view.setupImmobiles(immobiles)
    }

    override fun onVivaRealImmobilesSuccess(immobiles: List<Immobile>) {

    }

    override fun onImmobilesError(message: String) {

    }

    override fun onDestroy() {
        interactor.onDestroy()
    }
}