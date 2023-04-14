package by.ssrlab.fishpits.fragments.search.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.ssrlab.fishpits.MainActivity
import by.ssrlab.fishpits.databinding.FragmentByRiverBinding
import by.ssrlab.fishpits.objects.WaterObject
import by.ssrlab.fishpits.utils.base.BaseUIVM
import by.ssrlab.fishpits.utils.tools.adapters.regriv.ByRiverAdapter
import by.ssrlab.fishpits.utils.vm.main.MainVM
import by.ssrlab.fishpits.utils.vm.ui.sub.bychosen.ChosenUIVM
import by.ssrlab.fishpits.utils.vm.ui.sub.tables.regriv.RegRivUIVM
import io.reactivex.rxjava3.disposables.Disposable

class ByRiverFragment: Fragment() {

    private lateinit var langListener: Disposable

    private lateinit var binding: FragmentByRiverBinding
    private val uiVM: BaseUIVM by activityViewModels()
    private val regRivUIVM: RegRivUIVM by activityViewModels()
    private val activityVM: MainVM by activityViewModels()
    private val chosenUIVM: ChosenUIVM by activityViewModels()

    private lateinit var adapter: ByRiverAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentByRiverBinding.inflate(layoutInflater)

        binding.riverRv.layoutManager = LinearLayoutManager(activity as MainActivity)

        (activity as MainActivity).handleOnBackPressed()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityVM.setToolbarTitle("Rivers")
        uiVM.setNavController(regRivUIVM.getNavController())

        val application = (activity as MainActivity).provideApplication()

        var list = initList(application.getLanguage())

        adapter = ByRiverAdapter(list, chosenUIVM, activityVM, uiVM)
        binding.riverRv.adapter = adapter

        langListener = application.languageSubj.subscribe{
            list = initList(it)
            adapter = ByRiverAdapter(list, chosenUIVM, activityVM, uiVM)
            binding.riverRv.swapAdapter(adapter, false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        langListener.dispose()
    }

    private fun initList(language: Int): ArrayList<WaterObject>{
        val list = arrayListOf<WaterObject>()

        for (i in activityVM.waterObjects.value!!){
            if (i.languageId == language) list.add(i)
        }

        return list
    }
}