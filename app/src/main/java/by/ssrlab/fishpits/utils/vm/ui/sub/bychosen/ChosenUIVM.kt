package by.ssrlab.fishpits.utils.vm.ui.sub.bychosen

import by.ssrlab.fishpits.objects.point.PointDescripted
import by.ssrlab.fishpits.utils.base.BaseUIVM

class ChosenUIVM: BaseUIVM() {

    var access = ""
    var chosenId = 0

    private var pointGeo = PointDescripted()

    fun getPointGeo() = pointGeo
    fun setPointGeo(point: PointDescripted){
        pointGeo = point
    }
}