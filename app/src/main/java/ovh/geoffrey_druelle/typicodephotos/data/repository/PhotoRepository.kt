package ovh.geoffrey_druelle.typicodephotos.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ovh.geoffrey_druelle.typicodephotos.TypicodePhotosApp
import ovh.geoffrey_druelle.typicodephotos.data.local.dao.PhotoDao
import ovh.geoffrey_druelle.typicodephotos.data.local.database.TypicodePhotosDatabase
import kotlin.coroutines.CoroutineContext

class PhotoRepository(app: TypicodePhotosApp): CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var photoDao: PhotoDao

    init {
        val db = TypicodePhotosDatabase.getInstance(app)
        photoDao = db.photoDao()
    }
}