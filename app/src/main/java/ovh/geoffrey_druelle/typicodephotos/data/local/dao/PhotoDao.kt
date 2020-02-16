package ovh.geoffrey_druelle.typicodephotos.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ovh.geoffrey_druelle.typicodephotos.core.BaseDao
import ovh.geoffrey_druelle.typicodephotos.data.local.model.PhotoEntity

@Dao
interface PhotoDao: BaseDao<PhotoEntity> {

    @Query("SELECT COUNT(*) FROM PhotoEntity")
    fun countEntries(): Int

    @Query("DELETE FROM PhotoEntity")
    fun deleteAll()

    @Query("SELECT * FROM PhotoEntity")
    fun getAll(): LiveData<List<PhotoEntity>>
}
