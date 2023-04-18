package by.ssrlab.fishpits.objects.point

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class PointForDB (

    @SerializedName("id")
    @PrimaryKey
    var id: Int = 0,

    @SerializedName("lang_id")
    var languageId: Int = 0,

    var pointId: Int = 0,

    @SerializedName("point_name")
    var pointName: String = "",

    @SerializedName("visible")
    var isVisible: Boolean = false
)