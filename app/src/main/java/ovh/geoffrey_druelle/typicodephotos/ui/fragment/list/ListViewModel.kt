package ovh.geoffrey_druelle.typicodephotos.ui.fragment.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import ovh.geoffrey_druelle.typicodephotos.TypicodePhotosApp.Companion.instance
import ovh.geoffrey_druelle.typicodephotos.data.local.model.PhotoEntity
import ovh.geoffrey_druelle.typicodephotos.data.remote.api.TypicodeApi
import ovh.geoffrey_druelle.typicodephotos.data.remote.model.Photo
import ovh.geoffrey_druelle.typicodephotos.data.repository.PhotoRepository
import ovh.geoffrey_druelle.typicodephotos.utils.cleanDatabase
import ovh.geoffrey_druelle.typicodephotos.utils.populateDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class ListViewModel(private val api: TypicodeApi) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var job: Job = Job()

    private var photoRepository = PhotoRepository(instance)

    val photoList: LiveData<List<PhotoEntity>> = photoRepository.getPhotosList()

    fun requestPhotoData() {
        val call: Call<List<Photo>> = api.getPhotosData(1000)
        Timber.i("Call : %s", call.toString())
        call.enqueue(object : Callback<List<Photo>> {
            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                Timber.d(String.format("launchRequest : Failure on call - %s", t.message))
            }

            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                Timber.i("Response message : %s", response.message())
                if (response.isSuccessful) {
                    val photoList: List<Photo> = response.body()!!
                    cleanDatabase()
                    populateDatabase(photoList)
                } else {
                    Timber.d(String.format("launchRequest : got response but not successful"))
                }
            }
        })
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}
