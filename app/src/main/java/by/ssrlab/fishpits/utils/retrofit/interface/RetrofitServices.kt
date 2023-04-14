package by.ssrlab.fishpits.utils.retrofit.`interface`

import by.ssrlab.fishpits.objects.Region
import by.ssrlab.fishpits.objects.WaterObject
import by.ssrlab.fishpits.objects.district.DistrictCommon
import by.ssrlab.fishpits.objects.point.PointCommon
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface RetrofitServices {

    @GET("points/")
    fun getPoints(): Observable<MutableList<PointCommon>>

    @GET("regions/")
    fun getRegions(): Observable<MutableList<Region>>

    @GET("districts/")
    fun getDistricts(): Observable<MutableList<DistrictCommon>>

    @GET("water-objects/")
    fun getWaterObjects(): Observable<MutableList<WaterObject>>
}