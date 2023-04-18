package by.ssrlab.fishpits.fragments.search.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.ssrlab.fishpits.MainActivity
import by.ssrlab.fishpits.R
import by.ssrlab.fishpits.databinding.FragmentByRegionBinding
import by.ssrlab.fishpits.objects.Region
import by.ssrlab.fishpits.utils.base.BaseUIVM
import by.ssrlab.fishpits.utils.tools.adapters.regriv.ByRegionAdapter
import by.ssrlab.fishpits.utils.vm.main.MainVM
import by.ssrlab.fishpits.utils.vm.ui.sub.bychosen.ChosenUIVM
import by.ssrlab.fishpits.utils.vm.ui.sub.tables.regriv.RegRivUIVM
import io.reactivex.rxjava3.disposables.Disposable

class ByRegionFragment: Fragment() {

    private lateinit var langListener: Disposable

    private lateinit var binding: FragmentByRegionBinding
    private val uiVM: BaseUIVM by activityViewModels()
    private val regRivUIVM: RegRivUIVM by activityViewModels()
    private val activityVM: MainVM by activityViewModels()
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

        activityVM.setToolbarTitle(resources.getString(R.string.by_regions))
        uiVM.setNavController(regRivUIVM.getNavController())

        val application = (activity as MainActivity).provideApplication()

        var list = initList(application.getLanguage())

        adapter = ByRegionAdapter(list, chosenUIVM, activityVM, uiVM)
        binding.regionRv.adapter = adapter

        if (regRivUIVM.regLangListener == null) {
            langListener = application.languageSubj.subscribe {
                list = initList(it)
                adapter = ByRegionAdapter(list, chosenUIVM, activityVM, uiVM)
                binding.regionRv.swapAdapter(adapter, false)
            }

            regRivUIVM.regLangListener = langListener
        } else {
            langListener = regRivUIVM.regLangListener!!
        }
    }

    private fun initList(language: Int): ArrayList<Region>{
        val list = arrayListOf<Region>()

        for (i in activityVM.regions.value!!){
            if (i.languageId == language) list.add(i)
        }

        return list
    }
}