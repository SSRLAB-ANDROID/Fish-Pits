package by.ssrlab.fishpits.objects

import com.google.gson.annotations.SerializedName

data class Region(

    @SerializedName("id")
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
