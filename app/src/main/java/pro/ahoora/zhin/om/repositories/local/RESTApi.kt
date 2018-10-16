package pro.ahoora.zhin.om.repositories.local

import io.reactivex.Observable
import pro.ahoora.zhin.om.model.Patient
import retrofit2.http.GET
import retrofit2.http.Path

interface RESTApi {

    @GET("getَLocalPatientsById/{id}")
    fun getPatientById(@Path("id") id: Int): Observable<List<Patient>>

    @GET("getَLocalPatientsByName/{name}")
    fun getPatientByName(@Path("name") name: String): Observable<List<Patient>>

    @GET("getAllLocalPatients")
    fun getAllLocalPatient(): Observable<List<Patient>>


}