package by.ssrlab.fishpits

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import by.ssrlab.fishpits.databinding.ActivityMainBinding
import by.ssrlab.fishpits.utils.base.BaseUIVM
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
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setContentView(binding.root)

        binding.menuButton.setOnClickListener {
            binding.drawer.openDrawer(binding.navigationAppDrawer)
        }

        activityVM.setObservers(this, binding)
    }

    /**
     * FOR UI
     */
    fun handleOnBackPressed(bool: Boolean = false, onBackPressedCallback: OnBackPressedCallback? = null){
        if (onBackPressedCallback != null){
            activityVM.handleOnBackPressed(this, onBackPressedDispatcher, bool, onBackPressedCallback)
        } else {
            activityVM.handleOnBackPressed(this, onBackPressedDispatcher, bool)
        }
    }

    /**
     * FOR UI
     */
    fun turnOnBottomNav(){
        activityVM.turnOnBottomNav(binding)
    }

    /**
     * FOR UI
     */
    fun turnOffBottomNav(){
        activityVM.turnOffBottomNav(binding)
    }

    /**
     * FOR UI
     */
    fun setBottomNav(navController: NavController){
        activityVM.setupBottomNavFunc(binding, navController)
    }

    /**
     * FOR UI
     */
    fun hideToolbar(){
        toolbar.visibility = View.GONE
    }

    /**
     * FOR UI
     */
    fun showToolbar(){
        toolbar.visibility = View.VISIBLE
    }

    /**
     * FOR UI
     */
    fun hideNavView(){
        binding.drawer.closeDrawer(binding.navigationAppDrawer)
    }

    /**
     * FOR UI
     */
    fun setupNavView(baseUIVM: BaseUIVM){
        activityVM.setupNavView(binding, baseUIVM, this)
    }
}