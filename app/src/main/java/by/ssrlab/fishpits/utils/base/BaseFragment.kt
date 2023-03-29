package by.ssrlab.fishpits.utils.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.ssrlab.fishpits.utils.vm.MapUIVM

abstract class BaseFragment: Fragment() {

    abstract val viewModel: BaseUIVM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel is MapUIVM) {
            if ((viewModel as MapUIVM).getLaunchesCounter() == 1) {
                viewModel.defineNavController(view, true)
            }
        } else {
            viewModel.defineNavController(view)
        }
    }
}