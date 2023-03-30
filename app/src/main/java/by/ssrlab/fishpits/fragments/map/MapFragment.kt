package by.ssrlab.fishpits.fragments.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import by.ssrlab.fishpits.MainActivity
import by.ssrlab.fishpits.databinding.FragmentMapBinding
import by.ssrlab.fishpits.fragments.map.sub.PointDescriptionFragment
import by.ssrlab.fishpits.utils.base.BaseFragment
import by.ssrlab.fishpits.utils.vm.ui.MapUIVM
import by.ssrlab.fishpits.utils.vm.ui.sub.map.MapPointVM

class MapFragment: BaseFragment() {

    private lateinit var binding: FragmentMapBinding
    override val uiVM: MapUIVM by activityViewModels()
    private val pointVM: MapPointVM by activityViewModels() //Shared with bottom sheet

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMapBinding.inflate(layoutInflater)

        uiVM.setUI(activity as MainActivity)

        binding.point.setOnClickListener {
            PointDescriptionFragment().show(childFragmentManager, "pointDescription")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uiVM.clearMapHistory()

        (activity as MainActivity).setBottomNav(uiVM.getNavController())
        activityVM.setToolbarTitle("Map")
    }

    override fun onDestroy() {
        super.onDestroy()

        uiVM.decreaseMapCounter()
    }
}