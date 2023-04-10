package by.ssrlab.fishpits

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import by.ssrlab.fishpits.databinding.ActivityLaunchBinding
import by.ssrlab.fishpits.retrofit.common.Common
import by.ssrlab.fishpits.retrofit.`interface`.RetrofitServices
import by.ssrlab.fishpits.utils.vm.main.MainVM

import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
class LaunchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaunchBinding
    private val mediaJob = Job()
    private val mediaScope = CoroutineScope(Dispatchers.Main + mediaJob)

    private val activityVM: MainVM by viewModels()
    private lateinit var mService: RetrofitServices

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startActivity()
    }

    private suspend fun startApp() {
        val animationName = AnimationUtils.loadAnimation(this, R.anim.logo_appear_anim)
        val animationLogo = AnimationUtils.loadAnimation(this, R.anim.logo_appear_anim)

        binding.appName.startAnimation(animationName)
        binding.appName.visibility = View.VISIBLE
        delay(1500)
        binding.appLogo.startAnimation(animationLogo)
        binding.appLogo.visibility = View.VISIBLE
    }

    private fun startActivity(){
        mediaScope.launch {
            startApp()
        }

        mService = Common.retrofitService
        activityVM.setServices(mService)
        activityVM.loadData()

        mediaScope.launch {
            delay(5000)
            val intent = Intent(this@LaunchActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}