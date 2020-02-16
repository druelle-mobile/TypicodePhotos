package ovh.geoffrey_druelle.typicodephotos.data.remote.api

import ovh.geoffrey_druelle.typicodephotos.data.remote.model.Photo
import retrofit2.Call
import retrofit2.http.GET

interface TypicodeApi {

    @GET("photos")
    fun getPhotosData() : Call<Photo>
}