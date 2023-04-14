package by.ssrlab.fishpits.utils.vm.main

import android.app.Dialog
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import androidx.navigation.ui.setupWithNavController
import by.ssrlab.fishpits.LaunchActivity
import by.ssrlab.fishpits.MainActivity
import by.ssrlab.fishpits.R
import by.ssrlab.fishpits.app.Application
import by.ssrlab.fishpits.databinding.ActivityMainBinding
import by.ssrlab.fishpits.databinding.DialogLanguageBinding
import by.ssrlab.fishpits.db.AppDB
import by.ssrlab.fishpits.objects.Region
import by.ssrlab.fishpits.objects.WaterObject
import by.ssrlab.fishpits.objects.district.DistrictCommon
import by.ssrlab.fishpits.objects.district.DistrictDescripted
import by.ssrlab.fishpits.objects.district.DistrictForDB
import by.ssrlab.fishpits.objects.point.PointCommon
import by.ssrlab.fishpits.objects.point.PointDescripted
import by.ssrlab.fishpits.objects.point.PointForDB
import by.ssrlab.fishpits.utils.retrofit.`interface`.RetrofitServices
import by.ssrlab.fishpits.utils.base.BaseUIVM
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class MainVM : ViewModel() {

    private val mediaJob = Job()
    private val mediaScope = CoroutineScope(Dispatchers.Main + mediaJob)

    private val onMapBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            exitProcess(0)
        }
    }

    private var onTablesBackPressedCallback: OnBackPressedCallback? = null

    private val toolbarTitle: MutableLiveData<String> = MutableLiveData()

    private lateinit var mService: RetrofitServices
    private var mDisposable = CompositeDisposable()
    private val points = MutableLiveData<MutableList<PointCommon>>()
    private val regions = MutableLiveData<MutableList<Region>>()
    private val districts = MutableLiveData<MutableList<DistrictCommon>>()
    private val waterObjects = MutableLiveData<MutableList<WaterObject>>()

    /**
     * FOR LOGIC
     */
    fun loadPreferences(application: Application, activity: MainActivity){
        val sharedPreferences = activity.getSharedPreferences(application.PREFERENCES, MODE_PRIVATE)
        val language = sharedPreferences.getInt(application.LANGUAGE, 0)
        application.setLanguage(language)
    }

    /**
     * FOR LOGIC
     */
    private fun savePreferences(application: Application, activity: MainActivity, language: Int){
        val sharedPreferences = activity.getSharedPreferences(application.PREFERENCES, MODE_PRIVATE) ?: return
        with (sharedPreferences.edit()){
            putInt(application.LANGUAGE, language)
            apply()
        }
    }

    /**
     * FOR UI
     */
    fun setObservers(activity: MainActivity, binding: ActivityMainBinding) {
        toolbarTitle.observe(activity) {
            binding.toolbarTitle.text = it
        }
    }

    /**
     * FOR UI
     */
    fun setToolbarTitle(title: String) {
        toolbarTitle.value = title
    }

    /**
     * FOR UI
     */
    fun handleOnBackPressed(activity: MainActivity, onBackPressedDispatcher: OnBackPressedDispatcher, bool: Boolean = false, onBackPressedCallback: OnBackPressedCallback? = null) {
        if (bool) {
            onBackPressedDispatcher.addCallback(activity, onMapBackPressedCallback)
        } else if (onBackPressedCallback != null){
            onMapBackPressedCallback.remove()
            onTablesBackPressedCallback = onBackPressedCallback
            onBackPressedDispatcher.addCallback(onTablesBackPressedCallback!!)
        } else {
            onMapBackPressedCallback.remove()
            if (onTablesBackPressedCallback != null){
                onTablesBackPressedCallback!!.remove()
            }
        }
    }

    /**
     * FOR UI
     */
    fun turnOnBottomNav(binding: ActivityMainBinding, activity: MainActivity) {
        if (binding.bottomNavigation.visibility == View.GONE) {

            val showAnim = AnimationUtils.loadAnimation(activity, R.anim.bottom_nav_show_anim)
            binding.bottomNavigation.startAnimation(showAnim)

            binding.bottomNavigation.visibility = View.VISIBLE
        }
    }

    /**
     * FOR UI
     */
    fun turnOffBottomNav(binding: ActivityMainBinding, activity: MainActivity) {
        if (binding.bottomNavigation.visibility == View.VISIBLE) {

            val hideAnim = AnimationUtils.loadAnimation(activity, R.anim.bottom_nav_hide_anim)
            binding.bottomNavigation.startAnimation(hideAnim)

            binding.bottomNavigation.visibility = View.GONE
        }
    }

    /**
     * FOR UI
     */
    fun hideToolbar(toolbar: Toolbar, activity: MainActivity){
        val hidAnim = AnimationUtils.loadAnimation(activity, R.anim.toolbar_hide_anim)
        toolbar.startAnimation(hidAnim)
        toolbar.visibility = View.GONE
    }

    /**
     * FOR UI
     */
    fun showToolbar(toolbar: Toolbar, activity: MainActivity){
        val showAnim = AnimationUtils.loadAnimation(activity, R.anim.toolbar_show_anim)
        toolbar.startAnimation(showAnim)
        toolbar.visibility = View.VISIBLE
    }

    /**
     * FOR UI
     */
    fun setupBottomNavFunc(
        binding: ActivityMainBinding,
        primaryNavController: NavController
    ) {

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

                else -> false
            }
        }

        binding.bottomNavigation.setOnItemReselectedListener {

            when (it.itemId) {
                R.id.tables_fragment -> {
                    primaryNavController.navigate(
                        R.id.tables_fragment,
                        bundleOf(),
                        navOptions {
                            anim {
                                enter = R.anim.nav_slide_in_right
                                exit = R.anim.nav_slide_out_right
                            }
                        })
                }
            }
        }
    }

    /**
     * FOR UI
     */
    fun setupNavView(binding: ActivityMainBinding, baseUIVM: BaseUIVM, activity: MainActivity, application: Application) {
        binding.navigationAppDrawer.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_about_pr -> {
                    baseUIVM.navigate(R.id.aboutProject)
                    true
                }

                R.id.nav_lang -> {
                    initLangDialog(activity, application)
                    true
                }

                R.id.nav_write_to_devs -> {
                    activity.startActivity(Intent(
                            Intent.ACTION_SENDTO,
                            Uri.parse("mailto:ssrlab221@gmail.com")
                        )
                    )
                    true
                }

                else -> true
            }
        }
    }

    /**
     * FOR UI
     */
    fun initLangDialog(activity: MainActivity, application: Application){

        val dialog = Dialog(activity)
        val dialogBinding = DialogLanguageBinding.inflate(LayoutInflater.from(activity))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = 1000
        dialog.window?.attributes = layoutParams

        dialogBinding.langButton1.setOnClickListener {
            savePreferences(application, activity, 1)
            dialog.dismiss()
        }

        dialogBinding.langButton2.setOnClickListener {
            savePreferences(application, activity, 2)
            dialog.dismiss()
        }

        dialogBinding.langButton3.setOnClickListener {
            savePreferences(application, activity,3)
            dialog.dismiss()
        }

        dialog.show()
    }

    /**
     * FOR UI
     */
    fun setDrawerListener(binding: ActivityMainBinding, activity: MainActivity){
        binding.drawer.addDrawerListener(object: DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

            override fun onDrawerOpened(drawerView: View) {}

            override fun onDrawerClosed(drawerView: View) {}

            override fun onDrawerStateChanged(newState: Int) {
                if (!binding.drawer.isDrawerOpen(binding.navigationAppDrawer)){
                    if (newState == DrawerLayout.STATE_SETTLING){
                        turnOffBottomNav(binding, activity)
                        activity.hideToolbar()
                    }
                } else {
                    if (newState == DrawerLayout.STATE_SETTLING){
                        activity.showToolbar()
                        turnOnBottomNav(binding, activity)
                    }
                }
            }
        })
    }

    /**
     * FOR LOGIC
     */
    fun setServices(mService: RetrofitServices) {
        this.mService = mService
    }

    /**
     * FOR LOGIC
     */
    fun loadData(application: Application){

        val room = AppDB.getInstance(application.getContext())

        getAllPointsFromDb(room)
//        getPoints(room)
//        getRegions(room)
//        getDistricts(room)
//        getWaterObjects(room)
    }

    /**
     * FOR LOGIC
     */
    private fun getPoints(room: AppDB){
        mDisposable.add(
            mService.getPoints()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    points.value = it

                    writePoints(room)

                }, {})
        )
    }

    /**
     * FOR LOGIC
     */
    private fun getRegions(room: AppDB){
        mDisposable.add(
            mService.getRegions()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    regions.value = it

                    mediaScope.launch {
                        room.appDao.insertRegions(it)
                    }

                }, {})
        )
    }

    /**
     * FOR LOGIC
     */
    private fun getDistricts(room: AppDB){
        mDisposable.add(
            mService.getDistricts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    districts.value = it

                    writeDist(room)

                }, {})
        )
    }

    /**
     * FOR LOGIC
     */
    private fun getWaterObjects(room: AppDB){
        mDisposable.add(
            mService.getWaterObjects()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    mediaScope.launch{
                        room.appDao.insertWaterObjects(it)
                    }

                }, {})
        )
    }

    /**
     * FOR LOGIC
     */
    private fun writeDist(room: AppDB){
        val distForDbArray = arrayListOf<DistrictForDB>()
        val distDescripted = arrayListOf<DistrictDescripted>()

        for (i in 0 until districts.value!!.size) {
            with(districts.value!![i]) {
                val distForDb = DistrictForDB(
                    this.id,
                    this.district.id,
                    this.languageId,
                    this.districtName,
                    this.isVisible
                )

                distForDbArray.add(distForDb)
                distDescripted.add(this.district)
            }
        }

        mediaScope.launch {
            room.appDao.insertDistrictDB(distForDbArray)
            room.appDao.insertDistrictDescripted(distDescripted)
        }
    }

    /**
     * FOR LOGIC
     */
    private fun writePoints(room: AppDB){
        val pointsForDbArray = arrayListOf<PointForDB>()
        val pointsDescripted = arrayListOf<PointDescripted>()

        for (i in 0 until points.value!!.size){
            with(points.value!![i]){
                val pointForDb = PointForDB(
                    this.id,
                    this.languageId,
                    this.point.id,
                    this.pointName,
                    this.isVisible
                )

                pointsForDbArray.add(pointForDb)
                pointsDescripted.add(this.point)
            }
        }

        mediaScope.launch {
            room.appDao.insertPoints(pointsForDbArray)
            room.appDao.insertPointsDescripted(pointsDescripted)
        }
    }

    /**
     * FOR LOGIC
     */
    private fun getAllPointsFromDb(room: AppDB): List<PointCommon>{

        val list = arrayListOf<PointCommon>()
        var pointForDbArray = arrayListOf<PointForDB>()
        var pointDescArray = arrayListOf<PointDescripted>()

        mediaScope.launch {
            println(room.appDao.getAllPoints())
            pointForDbArray = room.appDao.getAllPoints() as ArrayList<PointForDB>
            pointDescArray = room.appDao.getAllPointsDesc() as ArrayList<PointDescripted>
        }

        for (i in 0 until pointForDbArray.size){
            val pointCommon = PointCommon(
                pointForDbArray[i].id,
                pointForDbArray[i].languageId,
                pointDescArray[i],
                pointForDbArray[i].pointName,
                pointForDbArray[i].isVisible
            )

            list.add(pointCommon)
        }

        return list
    }

    /**
     * FOR LOGIC
     */
    fun dispose(){
        mDisposable.dispose()
    }
}