package by.ssrlab.fishpits.utils.vm.main

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
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

    fun setToolbarObserver(activity: MainActivity, toolbar: Toolbar){
        toolbarTitle.observe(activity){
            toolbar.title = it
        }
    }

    fun setToolbarTitle(title: String){
        toolbarTitle.value = title
    }

    fun handleOnBackPressed(activity: MainActivity, onBackPressedDispatcher: OnBackPressedDispatcher, bool: Boolean = false){
        if (bool){
            onBackPressedDispatcher.addCallback(activity, onBackPressedCallback)
        } else {
            onBackPressedCallback.remove()
        }
    }

    fun turnOnBottomNav(binding: ActivityMainBinding){
        if (binding.bottomNavigation.visibility == View.GONE){
            binding.bottomNavigation.visibility = View.VISIBLE
        }
    }

    fun turnOffBottomNav(binding: ActivityMainBinding){
        if (binding.bottomNavigation.visibility == View.VISIBLE){
            binding.bottomNavigation.visibility = View.GONE
        }
    }

    fun setBottomNavFunc(activity: MainActivity, binding: ActivityMainBinding, navController: NavController){
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.map_fragment,
            R.id.tables_fragment
        ))
//        activity.setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigation.setupWithNavController(navController)
    }
}