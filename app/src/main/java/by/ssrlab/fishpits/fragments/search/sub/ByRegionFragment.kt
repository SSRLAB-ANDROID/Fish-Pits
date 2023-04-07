package by.ssrlab.fishpits.fragments.search.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.ssrlab.fishpits.MainActivity
import by.ssrlab.fishpits.databinding.FragmentByRegionBinding
import by.ssrlab.fishpits.utils.base.BaseUIVM
import by.ssrlab.fishpits.utils.tools.adapters.regriv.ByRegionAdapter
import by.ssrlab.fishpits.utils.vm.main.MainActivityVM
import by.ssrlab.fishpits.utils.vm.ui.sub.bychosen.ChosenUIVM
import by.ssrlab.fishpits.utils.vm.ui.sub.tables.regriv.RegRivUIVM

class ByRegionFragment: Fragment() {

    private lateinit var binding: FragmentByRegionBinding
    private val uiVM: BaseUIVM by activityViewModels()
    private val regRivUIVM: RegRivUIVM by activityViewModels()
    private val activityVM: MainActivityVM by activityViewModels()
    private val chosenUIVM: ChosenUIVM by activityViewModels()

    private lateinit var adapter: ByRegionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentByRegionBinding.inflate(layoutInflater)

        binding.regionRv.layoutManager = LinearLayoutManager(activity as MainActivity)

        (activity as MainActivity).handleOnBackPressed()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityVM.setToolbarTitle("Regions")
        uiVM.setNavController(regRivUIVM.getNavController())

        val list = arrayListOf(0, 1, 2, 3, 4, 5)
        adapter = ByRegionAdapter(list, chosenUIVM, activityVM, uiVM)
        binding.regionRv.adapter = adapter
    }
}