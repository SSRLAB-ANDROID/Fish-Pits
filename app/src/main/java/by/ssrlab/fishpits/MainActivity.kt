package by.ssrlab.fishpits

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import by.ssrlab.fishpits.databinding.ActivityMainBinding
import by.ssrlab.fishpits.objects.Region
import by.ssrlab.fishpits.objects.WaterObject
import by.ssrlab.fishpits.objects.district.DistrictCommon
import by.ssrlab.fishpits.objects.point.PointCommon
import by.ssrlab.fishpits.retrofit.common.Common
import by.ssrlab.fishpits.utils.base.BaseUIVM
import by.ssrlab.fishpits.utils.vm.main.MainVM
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar

    private val activityVM: MainVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setContentView(binding.root)

        activityVM.setDrawerListener(binding, this)

        val mServices = Common.retrofitService
        mServices.getPoints().subscribe(object: Observer<MutableList<PointCommon>>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
            }

            override fun onNext(t: MutableList<PointCommon>) {
                println(t)
            }
        })

        mServices.getRegions().subscribe(object: Observer<MutableList<Region>>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
            }

            override fun onNext(t: MutableList<Region>) {
                println(t)
            }
        })

        mServices.getDistricts().subscribe(object: Observer<MutableList<DistrictCommon>>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
            }

            override fun onNext(t: MutableList<DistrictCommon>) {
                println(t)
            }
        })

        mServices.getWaterObjects().subscribe(object: Observer<MutableList<WaterObject>>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
            }

            override fun onNext(t: MutableList<WaterObject>) {
                println(t)
            }
        })

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
    fun setBottomNav(navController: NavController){
        activityVM.setupBottomNavFunc(binding, navController)
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