package by.ssrlab.fishpits

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import by.ssrlab.fishpits.databinding.ActivityMainBinding
import by.ssrlab.fishpits.fragments.appdrawer.AboutProject
import by.ssrlab.fishpits.fragments.search.adapter.PagerAdapter
import by.ssrlab.fishpits.utils.base.BaseUIVM
import by.ssrlab.fishpits.utils.vm.main.MainActivityVM
import com.google.android.material.navigation.NavigationView

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
    fun handleOnBackPressed(bool: Boolean = false){
        activityVM.handleOnBackPressed(this, onBackPressedDispatcher, bool)
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
        activityVM.setNavFunc(this, binding, navController)
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
        activityVM.setupNav(binding, baseUIVM, this)
    }
}