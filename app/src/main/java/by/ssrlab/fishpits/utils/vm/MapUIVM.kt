package by.ssrlab.fishpits.utils.vm

import android.os.Build
import android.view.View
import by.ssrlab.fishpits.MainActivity
import by.ssrlab.fishpits.utils.base.BaseUIVM

class MapUIVM: BaseUIVM() {

    private var launchesCounter = 0

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

    fun getLaunchesCounter() = launchesCounter

    private fun implementLaunchesCounter(){
        launchesCounter++
    }

    fun handlePopBack(activity: MainActivity){
        if (getBackStackSize() == 1) activity.handleOnBackPressed(true)
        else activity.handleOnBackPressed()
    }

    fun setUI(activity: MainActivity){
        turnUIBack(activity)
        implementLaunchesCounter()
        activity.turnOnBottomNav()
    }
}