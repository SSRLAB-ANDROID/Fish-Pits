package by.ssrlab.fishpits.utils.vm.main

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
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
import by.ssrlab.fishpits.databinding.DialogLanguageBinding
import by.ssrlab.fishpits.utils.base.BaseUIVM
import kotlin.system.exitProcess

class MainActivityVM : ViewModel() {

    private val onMapBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            exitProcess(0)
        }
    }

    private var onTablesBackPressedCallback: OnBackPressedCallback? = null

    private val toolbarTitle: MutableLiveData<String> = MutableLiveData()

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
    fun turnOnBottomNav(binding: ActivityMainBinding) {
        if (binding.bottomNavigation.visibility == View.GONE) {
            binding.bottomNavigation.visibility = View.VISIBLE
        }
    }

    /**
     * FOR UI
     */
    fun turnOffBottomNav(binding: ActivityMainBinding) {
        if (binding.bottomNavigation.visibility == View.VISIBLE) {
            binding.bottomNavigation.visibility = View.GONE
        }
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
    fun setupNavView(binding: ActivityMainBinding, baseUIVM: BaseUIVM, activity: MainActivity) {
        binding.navigationAppDrawer.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_about_pr -> {
                    baseUIVM.navigate(R.id.aboutProject)
                    true
                }

                R.id.nav_lang -> {
                    initLangDialog(activity)
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

    private fun initLangDialog(activity: MainActivity){

        val dialog = Dialog(activity)
        val dialogBinding = DialogLanguageBinding.inflate(LayoutInflater.from(activity))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = 1000
        dialog.window?.attributes = layoutParams

        dialogBinding.firstLanguage.setOnClickListener {
            Toast.makeText(activity, "1st language", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialogBinding.secondLanguage.setOnClickListener {
            Toast.makeText(activity, "2nd language", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialogBinding.thirdLanguage.setOnClickListener {
            Toast.makeText(activity, "3rd language", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }
}