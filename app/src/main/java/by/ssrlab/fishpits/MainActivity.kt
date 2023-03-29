package by.ssrlab.fishpits

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import by.ssrlab.fishpits.databinding.ActivityMainBinding
import by.ssrlab.fishpits.utils.vm.main.MainActivityVM

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar
    private val activityVM: MainActivityVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        toolbar = binding.toolbar

        setSupportActionBar(toolbar)
        setContentView(binding.root)

        activityVM.setToolbarObserver(this, toolbar)
    }

    fun handleOnBackPressed(bool: Boolean = false){
        activityVM.handleOnBackPressed(this, onBackPressedDispatcher, bool)
    }

    fun turnOnBottomNav(){
        activityVM.turnOnBottomNav(binding)
    }

    fun turnOffBottomNav(){
        activityVM.turnOffBottomNav(binding)
    }

    fun setBottomNav(navController: NavController){
        activityVM.setBottomNavFunc(this, binding, navController)
    }

    fun hideToolbar(){
        toolbar.visibility = View.GONE
    }

    fun showToolbar(){
        toolbar.visibility = View.VISIBLE
    }

    fun setToolbar(toolbar: Toolbar){
        setSupportActionBar(toolbar)
    }
}