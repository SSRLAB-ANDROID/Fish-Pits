package by.ssrlab.fishpits.utils.vm.main

import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import androidx.navigation.ui.setupWithNavController
import by.ssrlab.fishpits.MainActivity
import by.ssrlab.fishpits.R
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
    fun setNavFunc(activity: MainActivity, binding: ActivityMainBinding, primaryNavController: NavController){

        binding.bottomNavigation.setupWithNavController(primaryNavController)

        binding.bottomNavigation.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.map_fragment -> {
                    if (binding.bottomNavigation.selectedItemId != R.id.map_fragment) {
                        binding.bottomNavigation.menu.findItem(R.id.map_fragment).isChecked = true

                        primaryNavController.navigate(
                            R.id.map_fragment,
                            bundleOf(),
                            navOptions {
                                anim {
                                    enter = R.anim.nav_slide_in_right
                                    popEnter = R.anim.nav_slide_in_left
                                    popExit = R.anim.nav_slide_out_left
                                    exit = R.anim.nav_slide_out_right
                                }
                            })
                    }
                    true
                }

                R.id.tables_fragment -> {
                    if (binding.bottomNavigation.selectedItemId != R.id.tables_fragment) {
                        binding.bottomNavigation.menu.findItem(R.id.tables_fragment).isChecked = true

                        primaryNavController.navigate(
                            R.id.tables_fragment,
                            bundleOf(),
                            navOptions {
                                anim {
                                    enter = R.anim.nav_slide_in_left
                                    popEnter = R.anim.nav_slide_in_right
                                    popExit = R.anim.nav_slide_out_right
                                    exit = R.anim.nav_slide_out_left
                                }
                            })
                    }
                    true
                }

                else -> {
                    Toast.makeText(activity, "Something went wrong...", Toast.LENGTH_SHORT).show()
                    true
                }
            }
        }

        binding.bottomNavigation.setOnItemReselectedListener {

        }
    }
}