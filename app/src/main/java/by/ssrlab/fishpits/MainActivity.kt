package by.ssrlab.fishpits

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import by.ssrlab.fishpits.app.Application
import by.ssrlab.fishpits.databinding.ActivityMainBinding
import by.ssrlab.fishpits.utils.base.BaseUIVM
import by.ssrlab.fishpits.utils.vm.main.MainVM

class MainActivity : AppCompatActivity() {

    private lateinit var application: Application

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar

    private val activityVM: MainVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        application = Application()
        application.setContext(this)

        activityVM.loadDataFromDb(application)

        activityVM.loadPreferences(application, this, binding)
        if (application.getLanguage() == 0){
            activityVM.initLangDialog(this, application, binding)
        }

        toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setContentView(binding.root)

        turnOnBottomNav()
        activityVM.setupBottomNavFunc(binding, this)

        activityVM.initListener(binding, this)
        activityVM.setDrawerListener(binding)

        binding.menuButton.setOnClickListener {
            binding.drawer.openDrawer(binding.navigationAppDrawer)
        }

        showToolbar()

        activityVM.setObservers(this, binding)
    }

    override fun onDestroy() {
        super.onDestroy()

        activityVM.dispose()
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
        activityVM.turnOnBottomNav(binding, this)
    }

    /**
     * FOR UI
     */
    fun turnOffBottomNav(){
        activityVM.turnOffBottomNav(binding, this)
    }

    /**
     * FOR UI
     */
    fun hideToolbar(){
        activityVM.hideToolbar(toolbar, this)
    }

    /**
     * FOR UI
     */
    fun showToolbar(){
        activityVM.showToolbar(toolbar, this)
    }

    /**
     * FOR UI
     */
    fun setToolbarPopBack(){
        binding.menuButton.setImageResource(R.drawable.ic_pop_back)
        binding.menuButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    /**
     * FOR UI
     */
    fun setToolbarShowMenu(){
        binding.menuButton.setImageResource(R.drawable.ic_menu)
        binding.menuButton.setOnClickListener {
            binding.drawer.openDrawer(binding.navigationAppDrawer)
        }
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
        activityVM.setupNavView(binding, baseUIVM, this, application)
    }

    /**
     * FOR LOGIC
     */
    fun provideApplication() = application
}