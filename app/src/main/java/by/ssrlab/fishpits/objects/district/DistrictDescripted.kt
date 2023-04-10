package by.ssrlab.fishpits.objects.district

import com.google.gson.annotations.SerializedName

data class DistrictDescripted(

    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("region_id")
    var regionId: Int = 0
)