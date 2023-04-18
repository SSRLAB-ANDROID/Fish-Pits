package by.ssrlab.fishpits.objects.district

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class DistrictDescripted(

    @SerializedName("id")
    @PrimaryKey
    var id: Int = 0,

    @SerializedName("region_id")
    var regionId: Int = 0
)