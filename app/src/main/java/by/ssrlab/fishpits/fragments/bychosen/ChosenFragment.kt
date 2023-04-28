package by.ssrlab.fishpits.fragments.bychosen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.ssrlab.fishpits.databinding.FragmentChosenBinding
import by.ssrlab.fishpits.fragments.bychosen.sub.MapPointFragment
import by.ssrlab.fishpits.objects.point.PointCommon
import by.ssrlab.fishpits.utils.base.BaseFragment
import by.ssrlab.fishpits.utils.tools.adapters.ChosenAdapter
import by.ssrlab.fishpits.utils.vm.ui.sub.bychosen.ChosenUIVM
import io.reactivex.rxjava3.disposables.Disposable

class ChosenFragment: BaseFragment() {

    private lateinit var langListener: Disposable
    private lateinit var binding: FragmentChosenBinding
    private lateinit var adapter: ChosenAdapter
    override val uiVM: ChosenUIVM by activityViewModels() /** Common with ChosenFragment */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentChosenBinding.inflate(layoutInflater)

        binding.points.layoutManager = LinearLayoutManager(context)

        activityMain.handleOnBackPressed()

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val application = activityMain.provideApplication()

        var list = initList(application.getLanguage())

        val mapPointFragment = MapPointFragment()

        adapter = ChosenAdapter(list, activityVM, uiVM, mapPointFragment, childFragmentManager)
        binding.points.adapter = adapter

        langListener = application.languageSubj.subscribe {
            list = initList(it)
            adapter = ChosenAdapter(list, activityVM, uiVM, mapPointFragment, childFragmentManager)
            binding.points.swapAdapter(adapter, false)
        }
    }

    private fun initList(language: Int): ArrayList<PointCommon>{

        val list = arrayListOf<PointCommon>()

        for (i in activityVM.points.value!!){
            if (uiVM.access == "region"){
                if (i.languageId == language){
                    for (j in activityVM.districts.value!!){
                        if (j.district.regionId == uiVM.chosenId && i.point.pointDistrictId == j.district.id && j.languageId == language) list.add(i)
                    }
                }
            }
            else {
                if (i.languageId == language && i.point.waterObjId == uiVM.chosenId) list.add(i)
            }
        }

        return list
    }

    override fun onDestroy() {
        super.onDestroy()

        langListener.dispose()

//        if (uiVM.access == "region"){
//            activityVM.setToolbarTitle(resources.getString(R.string.by_regions))
//        } else activityVM.setToolbarTitle(resources.getString(R.string.by_rivers))
    }
}