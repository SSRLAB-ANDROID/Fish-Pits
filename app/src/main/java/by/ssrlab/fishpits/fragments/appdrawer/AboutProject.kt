package by.ssrlab.fishpits.fragments.appdrawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import by.ssrlab.fishpits.databinding.FragmentAboutProjectBinding
import by.ssrlab.fishpits.utils.base.BaseFragment
import by.ssrlab.fishpits.utils.base.BaseUIVM

class AboutProject: BaseFragment() {

    private lateinit var binding: FragmentAboutProjectBinding
    override val uiVM: BaseUIVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAboutProjectBinding.inflate(layoutInflater)

        activityMain.hideNavView()
        activityMain.hideToolbar()
        activityMain.turnOffBottomNav()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()

        activityMain.turnOnBottomNav()
        activityMain.showToolbar()
    }
}