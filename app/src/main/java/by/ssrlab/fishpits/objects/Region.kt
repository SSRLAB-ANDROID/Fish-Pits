package by.ssrlab.fishpits.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Region(

    @SerializedName("id")
    @PrimaryKey
    var id: Int = 0,

    @SerializedName("region_id")
    var regionId: Int = 0,

    @SerializedName("lang_id")
    var languageId: Int = 0,

    @SerializedName("region_name")
    var regionName: String = "",

    @SerializedName("visible")
    var isVisible: Boolean = false
)
