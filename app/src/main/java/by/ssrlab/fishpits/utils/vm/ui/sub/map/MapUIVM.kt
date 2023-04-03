package by.ssrlab.fishpits.utils.vm.ui.sub.map

import android.os.Build
import android.view.View
import by.ssrlab.fishpits.MainActivity
import by.ssrlab.fishpits.utils.base.BaseUIVM

class MapUIVM: BaseUIVM() {

    private fun turnUIBack(activity: MainActivity) {
        activity.showToolbar()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.decorView.windowInsetsController!!.show(
                android.view.WindowInsets.Type.statusBars()
            )
        } else {
            activity.window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_VISIBLE
        }
    }

    fun setUI(activity: MainActivity){
        turnUIBack(activity)
        activity.turnOnBottomNav()
    }
}