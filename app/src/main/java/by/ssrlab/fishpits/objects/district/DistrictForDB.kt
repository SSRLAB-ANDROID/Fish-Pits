package by.ssrlab.fishpits.objects.district

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class DistrictForDB(

    @SerializedName("id")
    @PrimaryKey
    var id: Int = 0,

    var districtId: Int = 0,

    @SerializedName("lang_id")
    var languageId: Int = 0,

    @SerializedName("district_name")
    var districtName: String = "",

    @SerializedName("visible")
    var isVisible: Boolean = false
)