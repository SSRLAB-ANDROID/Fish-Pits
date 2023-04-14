package by.ssrlab.fishpits.objects.district

import com.google.gson.annotations.SerializedName

data class DistrictCommon(

    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("district")
    var district: DistrictDescripted,

    @SerializedName("lang_id")
    var languageId: Int = 0,

    @SerializedName("district_name")
    var districtName: String = "",

    @SerializedName("visible")
    var isVisible: Boolean = false
)
