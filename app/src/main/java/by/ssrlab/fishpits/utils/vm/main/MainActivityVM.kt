package by.ssrlab.fishpits.utils.vm.main

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import by.ssrlab.fishpits.MainActivity
import by.ssrlab.fishpits.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivityVM: ViewModel() {

    private val onBackPressedCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            exitProcess(0)
        }
    }

    private val toolbarTitle: MutableLiveData<String> = MutableLiveData()
    private val backStackSize: MutableLiveData<Int> = MutableLiveData()
    private val launchesCounter: MutableLiveData<Int> = MutableLiveData(-1)

    /**
     * FOR UI
     */
    fun implementLaunchesCounter(){
        launchesCounter.value = launchesCounter.value?.plus(1)
    }

    /**
     * FOR UI
     */
    fun getLaunchesCounter() = launchesCounter.value

    /**
     * FOR UI
     */
    fun setObservers(activity: MainActivity, binding: ActivityMainBinding){
        toolbarTitle.observe(activity){
            binding.toolbarTitle.text = it
        }

        launchesCounter.observe(activity){
            backStackSize.value = it
        }
    }

    /**
     * FOR UI
     */
    fun setToolbarTitle(title: String){
        toolbarTitle.value = title
    }

    /**
     * FOR UI
     */
    fun handleOnBackPressed(activity: MainActivity, onBackPressedDispatcher: OnBackPressedDispatcher, bool: Boolean = false){
        if (bool){
            onBackPressedDispatcher.addCallback(activity, onBackPressedCallback)
        } else {
            onBackPressedCallback.remove()
        }
    }

    /**
     * FOR UI
     */
    fun turnOnBottomNav(binding: ActivityMainBinding){
        if (binding.bottomNavigation.visibility == View.GONE){
            binding.bottomNavigation.visibility = View.VISIBLE
        }
    }

    /**
     * FOR UI
     */
    fun turnOffBottomNav(binding: ActivityMainBinding){
        if (binding.bottomNavigation.visibility == View.VISIBLE){
            binding.bottomNavigation.visibility = View.GONE
        }
    }

    /**
     * FOR UI
     */
    fun setNavFunc(binding: ActivityMainBinding, primaryNavController: NavController){

        binding.bottomNavigation.setupWithNavController(primaryNavController)
    }
}