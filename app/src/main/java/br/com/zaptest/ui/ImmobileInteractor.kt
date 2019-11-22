package br.com.zaptest.ui

import br.com.zaptest.data.BaseService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ImmobileInteractor : ImmobileContract.Interactor {

    private val disposables = CompositeDisposable()

    override fun fetchImmobiles(callback: ImmobileContract.Presenter.ImmobilesCallback) {

        val request = BaseService.immobiles()
        val disposable = request.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                response?.apply {
                    callback.onImmobilesSuccess(this.subList(0, 20))
                }

            }, {
                it.message?.apply { callback.onImmobilesError(this) }
            })
    }

    override fun onDestroy() {
        disposables.dispose()
    }
}