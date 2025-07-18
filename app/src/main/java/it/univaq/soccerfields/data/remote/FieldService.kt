package it.univaq.soccerfields.data.remote

import it.univaq.soccerfields.common.API_DATA
import it.univaq.soccerfields.data.remote.model.RemoteField
import retrofit2.http.GET

interface FieldService {

    @GET(API_DATA)
    suspend fun downloadData(): List<RemoteField>
}