package ovh.geoffrey_druelle.typicodephotos.ui.fragment.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import ovh.geoffrey_druelle.typicodephotos.TypicodePhotosApp.Companion.appContext
import ovh.geoffrey_druelle.typicodephotos.TypicodePhotosApp.Companion.instance
import ovh.geoffrey_druelle.typicodephotos.data.remote.api.TypicodeApi
import ovh.geoffrey_druelle.typicodephotos.data.remote.model.Photo
import ovh.geoffrey_druelle.typicodephotos.data.repository.PhotoRepository
import ovh.geoffrey_druelle.typicodephotos.utils.cleanDatabase
import ovh.geoffrey_druelle.typicodephotos.utils.createPhotoObject
import ovh.geoffrey_druelle.typicodephotos.utils.helper.ConnectivityHelper.isConnectedToNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class SplashScreenViewModel(private val api: TypicodeApi) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var job: Job = Job()

    private var photoRepository = PhotoRepository(instance)

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean>
        get() = _isConnected

    private val _noDataNoConnection = MutableLiveData<Boolean>()
    val noDataNoConnection: LiveData<Boolean>
        get() = _noDataNoConnection

    private val _navToList = MutableLiveData<Boolean>()
    val navToList: LiveData<Boolean>
        get() = _navToList

    var appVersion: String = instance.getVersionName()

    init {
        testConnection()
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }

    private fun testConnection() {
        val bool: Boolean = if (isConnectedToNetwork(appContext)) {
            _isConnected.postValue(true)
            true
        } else {
            _isConnected.postValue(false)
            false
        }

        checkPhotoData(bool)
    }

    private fun checkPhotoData(bool: Boolean) {
        val photoCount = runBlocking {
            photoRepository.countPhotosEntries()
        }

        when {
            bool -> runBlocking { launchRequest() }
            photoCount != 0 -> _navToList.postValue(true)
            else -> _noDataNoConnection.postValue(true)
        }
    }

    private fun launchRequest() {
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
                    _noDataNoConnection.postValue(false)
                } else {
                    Timber.d(String.format("launchRequest : got response but not successful"))
                }
            }
        })
    }


    private fun populateDatabase(photoList: List<Photo>) {
        runBlocking {
            for (i in photoList.indices)
                photoRepository.insert(createPhotoObject(photoList[i]))
            _navToList.postValue(true)
        }
    }
}
