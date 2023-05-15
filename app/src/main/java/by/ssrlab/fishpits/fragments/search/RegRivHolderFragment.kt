package by.ssrlab.fishpits.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import by.ssrlab.fishpits.R
import by.ssrlab.fishpits.databinding.FragmentRegrivHolderBinding
import by.ssrlab.fishpits.fragments.search.adapter.PagerAdapter
import by.ssrlab.fishpits.utils.base.BaseFragment
import by.ssrlab.fishpits.utils.vm.ui.sub.tables.regriv.RegRivUIVM
import com.google.android.material.tabs.TabLayoutMediator

class RegRivHolderFragment: BaseFragment() {

    private lateinit var binding: FragmentRegrivHolderBinding
    private lateinit var pagerAdapter: PagerAdapter
    override val uiVM: RegRivUIVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRegrivHolderBinding.inflate(layoutInflater)

        activityMain.handleOnBackPressed()
        activityMain.setToolbarPopBack()

        pagerAdapter = PagerAdapter(activityMain)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.currentItem = uiVM.currentPos

        TabLayoutMediator(binding.tabTitles, binding.viewPager){ tab, position ->
            if (position == 0) tab.text = resources.getString(R.string.by_regions)
            else if (position == 1) tab.text = resources.getString(R.string.by_rivers)
        }.attach()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uiVM.defineNavController(view)
    }

    override fun onResume() {
        super.onResume()

        activityVM.setToolbarTitle(uiVM.toolbarTitle)
        activityVM.setToolbarTitle(resources.getString(R.string.tables_fragment))
    }
}