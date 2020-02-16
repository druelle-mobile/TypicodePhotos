package ovh.geoffrey_druelle.typicodephotos.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PhotoEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Long,
    var albumId: Long,
    var title: String,
    var url: String,
    var thumbnailUrl: String
) {

}