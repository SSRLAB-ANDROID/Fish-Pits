package by.ssrlab.fishpits.objects

import com.google.gson.annotations.SerializedName

data class WaterObject(

    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("water_object_id")
    var waterObjectId: Int = 0,

    @SerializedName("lang_id")
    var languageId: Int = 0,

    @SerializedName("water_object_name")
    var waterObjectName: String = "",

    @SerializedName("visible")
    var isVisible: Boolean = false
)
