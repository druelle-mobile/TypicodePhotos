package ovh.geoffrey_druelle.typicodephotos.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import ovh.geoffrey_druelle.typicodephotos.TypicodePhotosApp
import ovh.geoffrey_druelle.typicodephotos.data.local.dao.PhotoDao
import ovh.geoffrey_druelle.typicodephotos.data.local.database.TypicodePhotosDatabase
import ovh.geoffrey_druelle.typicodephotos.data.local.model.PhotoEntity
import ovh.geoffrey_druelle.typicodephotos.data.repository.PhotoRepository
import ovh.geoffrey_druelle.typicodephotos.utils.getOrAwaitValue
import kotlin.coroutines.CoroutineContext

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, application = TypicodePhotosApp::class)
class PhotoRepositoryTest : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var photoRepository: PhotoRepository
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

        photoRepository = PhotoRepository(TypicodePhotosApp.instance)
    }

    @After
    fun after() {
        typicodePhotosDatabase.close()
        stopKoin()
    }

    @Test
    fun `insert photos, delete all, and count them`() {
        runBlocking {
            photoRepository.insert(PhotoEntity(1,10,"azerty qsdfgh","azertyuiop1","qsdfghjklm1"))
            photoRepository.insert(PhotoEntity(2,10,"qwerty qsdfgh","azertyuiop2","qsdfghjklm2"))
            photoRepository.insert(PhotoEntity(3,10,"qwertz qsdfgh","azertyuiop3","qsdfghjklm3"))
            photoRepository.insert(PhotoEntity(4,20,"azerty wxcvbn","azertyuiop4","qsdfghjklm4"))


            val countBeforeDel = photoRepository.countPhotosEntries()
            val attendeeBeforeDel = 4

            assertEquals(attendeeBeforeDel, countBeforeDel)

            photoRepository.deleteAll()

            val countAfterDel = photoRepository.countPhotosEntries()
            val attendeeAfterDel = 0

            assertEquals(attendeeAfterDel, countAfterDel)
        }
    }

    @Test
    fun `insert photos and get items details from list`() {
        runBlocking {
            withContext(Dispatchers.IO) {
                photoRepository.insert(
                    PhotoEntity(
                        1,
                        10,
                        "azerty qsdfgh",
                        "azertyuiop1",
                        "qsdfghjklm1"
                    )
                )
                photoRepository.insert(
                    PhotoEntity(
                        2,
                        10,
                        "qwerty qsdfgh",
                        "azertyuiop2",
                        "qsdfghjklm2"
                    )
                )
                photoRepository.insert(
                    PhotoEntity(
                        3,
                        10,
                        "qwertz qsdfgh",
                        "azertyuiop3",
                        "qsdfghjklm3"
                    )
                )
                photoRepository.insert(
                    PhotoEntity(
                        4,
                        20,
                        "azerty wxcvbn",
                        "azertyuiop4",
                        "qsdfghjklm4"
                    )
                )

                val photosList = photoRepository.getPhotosList().getOrAwaitValue()
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
    }
}