package br.com.zaptest.data

import br.com.zaptest.BuildConfig
import br.com.zaptest.entities.Immobile
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BaseService {

    companion object {

        private fun getRetrofitInstance(baseUrl: String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        private fun instance(baseUrl: String): Retrofit {
            val instance: Retrofit by lazy { getRetrofitInstance(baseUrl) }
            return instance
        }

        fun immobiles(): Observable<List<Immobile>> {
            return instance(BuildConfig.base_url).create(ImmobileService::class.java).immobiles()
                .flatMap { response ->
                    Observable.fromCallable { response }
                }.map { response -> response }
        }
    }
}