package br.com.zaptest.data

import br.com.zaptest.entities.Immobile
import io.reactivex.Observable
import retrofit2.http.GET

interface ImmobileService {

    @GET(Endpoints.immobiles)
    fun immobiles(): Observable<List<Immobile>>
}