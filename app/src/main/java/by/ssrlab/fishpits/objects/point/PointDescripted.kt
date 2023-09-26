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
    var lat1: Double = 0.0,

    @SerializedName("lng")
    var lng1: Double = 0.0,

    @SerializedName("lat_2")
    var lat2: Double = 0.0,

    @SerializedName("lng_2")
    var lng2: Double = 0.0,

    @SerializedName("lat_3")
    var lat3: Double = 0.0,

    @SerializedName("lng_3")
    var lng3: Double = 0.0,

    @SerializedName("lat_4")
    var lat4: Double = 0.0,

    @SerializedName("lng_4")
    var lng4: Double = 0.0,

    @SerializedName("visible")
    var isVisible: Boolean = false
)