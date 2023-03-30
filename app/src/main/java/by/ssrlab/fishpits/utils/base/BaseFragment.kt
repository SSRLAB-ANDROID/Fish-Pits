package by.ssrlab.fishpits.utils.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.ssrlab.fishpits.MainActivity
import by.ssrlab.fishpits.utils.vm.main.MainActivityVM
import by.ssrlab.fishpits.utils.vm.ui.MapUIVM

abstract class BaseFragment: Fragment() {

    abstract val uiVM: BaseUIVM
    val activityVM: MainActivityVM by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityVM.implementLaunchesCounter()

        if (uiVM is MapUIVM) {
            if (activityVM.getLaunchesCounter() == 1) {
                uiVM.defineNavController(view, true)
            }
        } else {
            uiVM.defineNavController(view)
        }
    }

    override fun onResume() {
        super.onResume()

        uiVM.handlePopBack(activity as MainActivity)
    }
}