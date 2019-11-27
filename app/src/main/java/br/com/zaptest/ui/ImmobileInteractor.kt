package br.com.zaptest.ui

import br.com.zaptest.data.BaseService
import br.com.zaptest.entities.Immobile
import br.com.zaptest.ui.ImmobileContract.Presenter.ImmobilesCallback
import br.com.zaptest.ui.ImmobileContract.Presenter.LoadMoreimmobilesCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ImmobileInteractor : ImmobileContract.Interactor {

    var lastZapImmobileIndex = 0
    var lastVivaRealImmobileIndex = 0
    private val paginationLimit = 20

    private val disposables = CompositeDisposable()
    private lateinit var immobiles: List<Immobile>

    override fun fetchImmobiles(callback: ImmobilesCallback) {
        val request = BaseService.immobiles()
        val disposable = request.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                response?.apply {
                    immobiles = this
                    callback.onImmobilesSuccess()
                    filterImmobileType(callback)
                }
            }, {
                it.message?.apply { callback.onImmobilesError(this) }
            })

        disposables.add(disposable)
    }

    override fun loadZapImmobilesPerPage(position: Int, callback: LoadMoreimmobilesCallback) {
        val filteredImmobiles = mutableListOf<Immobile>()

        immobiles.subList(lastZapImmobileIndex, immobiles.size).forEachIndexed { index, immobile ->

            if (filteredImmobiles.size == paginationLimit) {
                lastZapImmobileIndex = index
                callback.onLoadMoreImmobiles(position, filteredImmobiles)
                return
            }

            if (isZapConditions(immobile)) {
                filteredImmobiles.add(immobile)
            }
        }
    }

    override fun loadVivaRealImmobilesPerPage(position: Int, callback: LoadMoreimmobilesCallback) {
        val filteredImmobiles = mutableListOf<Immobile>()

        immobiles.subList(lastVivaRealImmobileIndex, immobiles.size).forEachIndexed { index, immobile ->

            if (filteredImmobiles.size == paginationLimit) {
                lastVivaRealImmobileIndex = index
                callback.onLoadMoreImmobiles(position, filteredImmobiles)
                return
            }

            if (isVivaRealConditions(immobile)) {
                filteredImmobiles.add(immobile)
            }
        }
    }

    private fun filterImmobileType(callback: ImmobilesCallback) {
        val zapImmobiles = filterZapImmobiles()
        callback.onZapImmobilesSuccess(zapImmobiles)

        val vivaRealImmobiles = filterVivaRealImmobiles()
        callback.onVivaRealImmobilesSuccess(vivaRealImmobiles)
    }

    private fun filterZapImmobiles(): List<Immobile> {
        val zapImmobiles = mutableListOf<Immobile>()
        immobiles.forEachIndexed { index, immobile ->
            if (zapImmobiles.size == paginationLimit) {
                lastZapImmobileIndex = index
                return zapImmobiles
            }

            if (isZapConditions(immobile) && zapImmobiles.size < paginationLimit) {
                zapImmobiles.add(immobile)
            }
        }

        return zapImmobiles
    }

    private fun filterVivaRealImmobiles(): List<Immobile> {
        val vivaRealImmobiles = mutableListOf<Immobile>()
        immobiles.forEachIndexed { index, immobile ->
            if (vivaRealImmobiles.size == paginationLimit) {
                lastZapImmobileIndex = index
                return vivaRealImmobiles
            }

            if (isVivaRealConditions(immobile) && vivaRealImmobiles.size < paginationLimit) {
                vivaRealImmobiles.add(immobile)
            }
        }

        return vivaRealImmobiles
    }

    private fun isZapConditions(immobile: Immobile): Boolean {
        return immobile.address.geoLocation.location.lat < 0 &&
                immobile.address.geoLocation.location.lon < 0 &&
                immobile.pricingInfos.businessType == "SALE" &&
                immobile.usableAreas.toInt() > 0
    }

    private fun isVivaRealConditions(immobile: Immobile): Boolean {
        immobile.pricingInfos.monthlyCondoFee?.apply {
            this.toInt().takeIf { it > 0 }?.apply {
                if (this.toDouble() < (immobile.pricingInfos.price.toDouble() / 100.0f) * 30) {
                    return false
                }
            } ?: kotlin.run {
                return false
            }
        }

        return immobile.address.geoLocation.location.lat < 0 &&
                immobile.address.geoLocation.location.lon < 0 &&
                immobile.pricingInfos.businessType == "RENTAL"
    }

    override fun onDestroy() {
        disposables.dispose()
    }
}