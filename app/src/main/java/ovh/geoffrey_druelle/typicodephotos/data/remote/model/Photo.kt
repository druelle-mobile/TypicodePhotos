package ovh.geoffrey_druelle.typicodephotos.data.remote.model

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("albumId") val albumId: Long,
    @SerializedName("id") val id: Long,
    @SerializedName("thumbnailUrl") val thumbnailUrl: String,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String
)