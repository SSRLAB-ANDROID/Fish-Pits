package by.ssrlab.fishpits.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import by.ssrlab.fishpits.databinding.FragmentRegrivHolderBinding
import by.ssrlab.fishpits.fragments.search.adapter.PagerAdapter
import by.ssrlab.fishpits.utils.base.BaseFragment
import by.ssrlab.fishpits.utils.vm.ui.tables.regriv.RegRivUIVM

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

        pagerAdapter = PagerAdapter(activityMain)
        binding.viewPager.adapter = pagerAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uiVM.defineNavController(view)
    }
}