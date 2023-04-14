package by.ssrlab.fishpits.objects.point

import com.google.gson.annotations.SerializedName

data class PointCommon(

    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("lang_id")
    var languageId: Int = 0,

    @SerializedName("point")
    var point: PointDescripted,

    @SerializedName("point_name")
    var pointName: String = "",

    @SerializedName("visible")
    var isVisible: Boolean = false
)
