package ovh.geoffrey_druelle.typicodephotos.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ovh.geoffrey_druelle.typicodephotos.data.local.dao.PhotoDao
import ovh.geoffrey_druelle.typicodephotos.data.local.database.TypicodePhotosDatabase.Companion.databaseVersion
import ovh.geoffrey_druelle.typicodephotos.data.local.model.PhotoEntity
import ovh.geoffrey_druelle.typicodephotos.utils.DB_NAME

@Database(
    entities = [
        PhotoEntity::class    ],
    version = databaseVersion,
    exportSchema = false
)
abstract class TypicodePhotosDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao

    companion object {
        @Volatile
        private var instance: TypicodePhotosDatabase? = null
        const val databaseVersion = 1

        fun getInstance(context: Context): TypicodePhotosDatabase =
            instance ?: synchronized(this) {
                instance ?: build(context).also {
                    instance = it
                }
            }

        private fun build(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TypicodePhotosDatabase::class.java,
                DB_NAME
            )
                .addMigrations(nToNPlusOneMigration)
                .build()

        private val nToNPlusOneMigration =
            object : Migration(
                databaseVersion,
                databaseVersion + 1
            ) {
                override fun migrate(database: SupportSQLiteDatabase) {}
            }
    }
}
