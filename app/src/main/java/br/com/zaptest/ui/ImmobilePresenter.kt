package br.com.zaptest.ui

import br.com.zaptest.entities.Immobile

class ImmobilePresenter(
    private val view: ImmobileContract.View,
    private val interactor: ImmobileInteractor
) : ImmobileContract.Presenter,
    ImmobileContract.Presenter.ImmobilesCallback,
    ImmobileContract.Presenter.LoadMoreimmobilesCallback {

    override fun fetchImmobiles() {
        interactor.fetchImmobiles(this)
    }

    override fun onZapImmobilesSuccess(immobiles: List<Immobile>) {
        view.setupZapImmobiles(immobiles)
    }

    override fun onVivaRealImmobilesSuccess(immobiles: List<Immobile>) {
        view.setupVivaRealImmobiles(immobiles)
    }

    override fun onImmobilesSuccess() {
        view.setupImmobilesSlider()
    }

    override fun onImmobilesError(message: String) {

    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onLoadMoreImmobilesSuccess(position: Int, immobilies: List<Immobile>) {
        view.addMoreImmoobilies(position, immobilies)
    }

    override fun onLoadMoreImmobilesError() {

    }

    override fun loadMoreImmobiles(position: Int) {
        when (position) {

            0 -> {
                interactor.loadZapImmobilesPerPage(position, this)
            }

            1 -> {
                interactor.loadVivaRealImmobilesPerPage(position, this)
            }
        }
    }

}