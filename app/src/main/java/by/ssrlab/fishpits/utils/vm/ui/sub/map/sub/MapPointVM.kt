package by.ssrlab.fishpits.utils.vm.ui.sub.map.sub

import androidx.lifecycle.ViewModel
import by.ssrlab.fishpits.objects.point.PointCommon
import by.ssrlab.fishpits.objects.point.PointDescripted

class MapPointVM: ViewModel() {

    private var point = PointCommon(point = PointDescripted())

    fun getPoint() = point
    fun setPoint(p: PointCommon){
        point = p
    }
}