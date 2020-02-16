package ovh.geoffrey_druelle.typicodephotos.data.remote.api

import ovh.geoffrey_druelle.typicodephotos.data.remote.model.Photo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TypicodeApi {

    @GET("photos")
    fun getPhotosData(
        @Query("_limit") limit: Int
    ) : Call<List<Photo>>
}