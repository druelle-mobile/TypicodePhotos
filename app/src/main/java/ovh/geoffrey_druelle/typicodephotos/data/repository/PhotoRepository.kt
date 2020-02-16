package ovh.geoffrey_druelle.typicodephotos.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ovh.geoffrey_druelle.typicodephotos.TypicodePhotosApp
import ovh.geoffrey_druelle.typicodephotos.data.local.dao.PhotoDao
import ovh.geoffrey_druelle.typicodephotos.data.local.database.TypicodePhotosDatabase
import ovh.geoffrey_druelle.typicodephotos.data.local.model.PhotoEntity
import kotlin.coroutines.CoroutineContext

class PhotoRepository(app: TypicodePhotosApp) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var photoDao: PhotoDao

    init {
        val db = TypicodePhotosDatabase.getInstance(app)
        photoDao = db.photoDao()
    }

    suspend fun countPhotosEntries(): Int {
        return withContext(Dispatchers.IO) {
            photoDao.countEntries()
        }
    }

    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            photoDao.deleteAll()
        }
    }

    suspend fun insert(photo: PhotoEntity) {
        withContext(Dispatchers.IO) {
            photoDao.insert(photo)
        }
    }

    fun getPhotosList(): LiveData<List<PhotoEntity>> {
        return photoDao.getAll()
    }

}