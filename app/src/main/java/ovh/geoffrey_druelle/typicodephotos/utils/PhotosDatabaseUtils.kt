package ovh.geoffrey_druelle.typicodephotos.utils

import kotlinx.coroutines.runBlocking
import ovh.geoffrey_druelle.typicodephotos.TypicodePhotosApp
import ovh.geoffrey_druelle.typicodephotos.data.local.model.PhotoEntity
import ovh.geoffrey_druelle.typicodephotos.data.remote.model.Photo
import ovh.geoffrey_druelle.typicodephotos.data.repository.PhotoRepository

private var photoRepository = PhotoRepository(TypicodePhotosApp.instance)

fun populateDatabase(photoList: List<Photo>) {
    runBlocking {
        for (i in photoList.indices)
            photoRepository.insert(createPhotoObject(photoList[i]))
    }
}

fun cleanDatabase() {
    runBlocking {
        photoRepository.deleteAll()
    }
}

fun createPhotoObject(photo: Photo): PhotoEntity {
    return PhotoEntity(
        photo.id,
        photo.albumId,
        photo.title,
        photo.url,
        photo.thumbnailUrl
    )
}