package by.ssrlab.fishpits.utils.base

import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import by.ssrlab.fishpits.MainActivity
import by.ssrlab.fishpits.R

open class BaseUIVM: ViewModel() {

    private lateinit var navController: NavController
    private var mapCounter = 1
    private var previousBackQueueCount = 0

    fun defineNavController(view: View, bool: Boolean = false){
        navController = view.findNavController()

        if (bool){
            for (i in 1 until navController.backQueue.size){
                navController.backQueue.removeAt(0)
            }
        }
    }

    fun handlePopBack(activity: MainActivity){

        if (getBackStackSize() == 1) activity.handleOnBackPressed(true)
        else activity.handleOnBackPressed()
    }

    fun getNavController() = navController

    private fun getBackStackSize() = navController.backQueue.size

    fun navigate(address: Int) {
        navigateTo(address)
    }

    private fun navigateTo(address: Int){
        navController.navigate(
            address,
            bundleOf(),
            navOptions {
                anim {
                    enter = R.anim.nav_slide_in_left
                    popEnter = R.anim.nav_slide_in_left
                    popExit = R.anim.nav_slide_out_right
                    exit = R.anim.nav_slide_out_right
                }
            })
    }

    fun clearMapHistory(){

        if (mapCounter > 2){
            for (i in 1 .. 4){
                navController.backQueue.removeAt(0)
            }
        }

        if (mapCounter == 3) mapCounter = 2

        if (previousBackQueueCount != navController.backQueue.size) {
            mapCounter++
            previousBackQueueCount = navController.backQueue.size
        } else {
            mapCounter--
            previousBackQueueCount -= 2
        }
    }

    fun decreaseMapCounter(){
        mapCounter--
    }
}