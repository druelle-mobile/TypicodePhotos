package ovh.geoffrey_druelle.typicodephotos.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class PhotoEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Long,
    var albumId: Long,
    var title: String,
    var url: String,
    var thumbnailUrl: String
): Parcelable {

}