package br.com.zaptest.ui

import br.com.zaptest.entities.Immobile

interface ImmobileContract {

    interface View {
        fun setupZapImmobiles(immobiles: List<Immobile>)
        fun setupVivaRealImmobiles(immobiles: List<Immobile>)
        fun setupImmobilesSlider()
        fun addMoreImmoobilies(position: Int, immobilies: List<Immobile>)
        fun showLoading()
        fun hideLoading()
        fun showTabs()
        fun showErrorScreen()
        fun hideErrorScreen()
        fun displayFastMessage(message: String)
    }

    interface Presenter {
        interface ImmobilesCallback {
            fun onImmobilesSuccess()
            fun onVivaRealImmobilesSuccess(immobiles: List<Immobile>)
            fun onZapImmobilesSuccess(immobiles: List<Immobile>)
            fun onImmobilesError(message: String)
        }

        interface LoadMoreimmobilesCallback {
            fun onLoadMoreImmobiles(position: Int, immobilies: List<Immobile>)
        }

        fun fetchImmobiles()
        fun onDestroy()
        fun loadMoreImmobiles(position: Int)
    }

    interface Interactor {
        fun fetchImmobiles(callback: Presenter.ImmobilesCallback)
        fun onDestroy()
        fun loadZapImmobilesPerPage(position: Int, callback: Presenter.LoadMoreimmobilesCallback)
        fun loadVivaRealImmobilesPerPage(position: Int, callback: Presenter.LoadMoreimmobilesCallback)
    }
}