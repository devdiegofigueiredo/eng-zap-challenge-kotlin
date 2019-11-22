package br.com.zaptest.ui

import br.com.zaptest.data.BaseService
import br.com.zaptest.entities.Immobile
import br.com.zaptest.ui.ImmobileContract.Presenter.ImmobilesCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ImmobileInteractor : ImmobileContract.Interactor {

    private val disposables = CompositeDisposable()

    override fun fetchImmobiles(callback: ImmobilesCallback) {

        val request = BaseService.immobiles()
        val disposable = request.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                response?.apply {
                    callback.onImmobilesSuccess()
                    filterImmobileType(this.subList(0, 100), callback)
                }
            }, {
                it.message?.apply { callback.onImmobilesError(this) }
            })

        disposables.add(disposable)
    }

    private fun filterImmobileType(immobiles: List<Immobile>, callback: ImmobilesCallback) {
        val zapImmobilles = immobiles.filter {
            it.address.geoLocation.location.lat < 0 &&
                    it.address.geoLocation.location.lon < 0 &&
                    it.pricingInfos.businessType == "SALE" &&
                    it.usableAreas.toInt() > 0
        }

        callback.onZapImmobilesSuccess(zapImmobilles)

        val vivaRealImmobiles = immobiles.filter {
            it.address.geoLocation.location.lat < 0 &&
                    it.address.geoLocation.location.lon < 0 &&
                    it.pricingInfos.businessType == "RENTAL" &&
                    it.pricingInfos.monthlyCondoFee.toInt() > 0
        }

        callback.onVivaRealImmobilesSuccess(vivaRealImmobiles)
    }

    override fun onDestroy() {
        disposables.dispose()
    }
}