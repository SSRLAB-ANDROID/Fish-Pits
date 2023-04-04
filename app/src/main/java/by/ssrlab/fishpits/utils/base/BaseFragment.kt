package by.ssrlab.fishpits.utils.base

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.navOptions
import by.ssrlab.fishpits.MainActivity
import by.ssrlab.fishpits.R
import by.ssrlab.fishpits.utils.vm.main.MainActivityVM
import by.ssrlab.fishpits.utils.vm.ui.MapUIVM
import by.ssrlab.fishpits.utils.vm.ui.TablesUIVM

abstract class BaseFragment : Fragment() {

    abstract val uiVM: BaseUIVM
    lateinit var activityMain: MainActivity
    val activityVM: MainActivityVM by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMain = activity as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uiVM.defineNavController(view)
    }

    override fun onResume() {
        super.onResume()

        when (uiVM) {
            is MapUIVM -> activityMain.handleOnBackPressed(true)

            is TablesUIVM -> {
                activityMain.handleOnBackPressed(onBackPressedCallback = object : OnBackPressedCallback(true){
                    override fun handleOnBackPressed() {
                        uiVM.getNavController().navigate(
                            R.id.map_fragment,
                            bundleOf(),
                            navOptions {
                                anim {
                                    enter = R.anim.nav_slide_in_right
                                    exit = R.anim.nav_slide_out_right
                            }
                        })
                    }

                })
            }

            else -> activityMain.handleOnBackPressed()
        }
    }
}