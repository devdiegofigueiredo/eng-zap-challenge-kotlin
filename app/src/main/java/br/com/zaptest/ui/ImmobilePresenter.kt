package br.com.zaptest.ui

import br.com.zaptest.entities.Immobile

class ImmobilePresenter(
    private val view: ImmobileContract.View,
    private val interactor: ImmobileContract.Interactor
) : ImmobileContract.Presenter,
    ImmobileContract.Presenter.ImmobilesCallback,
    ImmobileContract.Presenter.LoadMoreimmobilesCallback {

    override fun fetchImmobiles() {
        view.hideErrorScreen()
        view.showLoading()
        interactor.fetchImmobiles(this)
    }

    override fun onZapImmobilesSuccess(immobiles: List<Immobile>) {
        view.setupZapImmobiles(immobiles)
        view.hideLoading()
        view.showTabs()
    }

    override fun onVivaRealImmobilesSuccess(immobiles: List<Immobile>) {
        view.setupVivaRealImmobiles(immobiles)
    }

    override fun onImmobilesSuccess() {
        view.setupImmobilesSlider()
    }

    override fun onImmobilesError(message: String) {
        view.hideLoading()
        view.showErrorScreen()
        view.displayFastMessage(message)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onLoadMoreImmobiles(position: Int, immobilies: List<Immobile>) {
        view.addMoreImmoobilies(position, immobilies)
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