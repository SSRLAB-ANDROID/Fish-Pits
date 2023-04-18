package by.ssrlab.fishpits.objects.point

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class PointDescripted(

    @SerializedName("id")
    @PrimaryKey
    var id: Int = 0,

    @SerializedName("point_district_id")
    var pointDistrictId: Int = 0,

    @SerializedName("water_object_id")
    var waterObjId: Int = 0,

    @SerializedName("point_geo_type")
    var pointGeoType: String = "",

    @SerializedName("lat")
    var latStart: Double = 0.0,

    @SerializedName("lng")
    var lngStart: Double = 0.0,

    @SerializedName("lat_2")
    var latFinish: Double = 0.0,

    @SerializedName("lng_2")
    var lngFinish: Double = 0.0,

    @SerializedName("visible")
    var isVisible: Boolean = false
)