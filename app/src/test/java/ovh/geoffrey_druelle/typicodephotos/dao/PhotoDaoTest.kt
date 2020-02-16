package ovh.geoffrey_druelle.typicodephotos.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import ovh.geoffrey_druelle.typicodephotos.TypicodePhotosApp
import ovh.geoffrey_druelle.typicodephotos.data.local.dao.PhotoDao
import ovh.geoffrey_druelle.typicodephotos.data.local.database.TypicodePhotosDatabase
import ovh.geoffrey_druelle.typicodephotos.data.local.model.PhotoEntity
import ovh.geoffrey_druelle.typicodephotos.utils.getOrAwaitValue

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, application = TypicodePhotosApp::class)
class PhotoDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var typicodePhotosDatabase: TypicodePhotosDatabase
    private lateinit var photoDao: PhotoDao
    
    @Before
    fun setUp() {
        typicodePhotosDatabase = Room.inMemoryDatabaseBuilder(
            TypicodePhotosApp.appContext,
            TypicodePhotosDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        photoDao = typicodePhotosDatabase.photoDao()
    }

    @After
    fun after() {
        typicodePhotosDatabase.close()
        stopKoin()
    }

    @Test
    fun `insert photos, delete all, and count them`() {
        photoDao.insert(PhotoEntity(1,10,"azerty qsdfgh","azertyuiop","qsdfghjklm"))
        photoDao.insert(PhotoEntity(2,10,"qwerty qsdfgh","azertyuiop","qsdfghjklm"))
        photoDao.insert(PhotoEntity(3,10,"qwertz qsdfgh","azertyuiop","qsdfghjklm"))
        photoDao.insert(PhotoEntity(4,20,"azerty wxcvbn","azertyuiop","qsdfghjklm"))

        val countBeforeDel = photoDao.countEntries()
        val attendeeBeforeDel = 4

        assertEquals(attendeeBeforeDel, countBeforeDel)

        photoDao.deleteAll()

        val countAfterDel = photoDao.countEntries()
        val attendeeAfterDel = 0

        assertEquals(attendeeAfterDel, countAfterDel)
    }

    @Test
    fun `insert photos and get items details from list`() {
        photoDao.insert(PhotoEntity(1,10,"azerty qsdfgh","azertyuiop1","qsdfghjklm1"))
        photoDao.insert(PhotoEntity(2,10,"qwerty qsdfgh","azertyuiop2","qsdfghjklm2"))
        photoDao.insert(PhotoEntity(3,10,"qwertz qsdfgh","azertyuiop3","qsdfghjklm3"))
        photoDao.insert(PhotoEntity(4,20,"azerty wxcvbn","azertyuiop4","qsdfghjklm4"))

        val photosList = photoDao.getAll().getOrAwaitValue()
        val attendeeFirstPhotoId = 1L
        val attendeeSecondAlbumId = 10L
        val attendeeThirdTitle = "qwertz qsdfgh"
        val attendeeFourthUrl = "azertyuiop4"
        val attendeeFirstThumbnailUrl = "qsdfghjklm1"

        assertEquals(attendeeFirstPhotoId, photosList[0].id)
        assertEquals(attendeeSecondAlbumId, photosList[1].albumId)
        assertEquals(attendeeThirdTitle, photosList[2].title)
        assertEquals(attendeeFourthUrl, photosList[3].url)
        assertEquals(attendeeFirstThumbnailUrl, photosList[0].thumbnailUrl)
    }
}