package ovh.geoffrey_druelle.typicodephotos.data.local.dao

import androidx.room.Dao
import ovh.geoffrey_druelle.typicodephotos.core.BaseDao
import ovh.geoffrey_druelle.typicodephotos.data.local.model.PhotoEntity

@Dao
interface PhotoDao: BaseDao<PhotoEntity> {

}
