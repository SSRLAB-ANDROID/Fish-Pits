package by.ssrlab.fishpits.fragments.bychosen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.ssrlab.fishpits.databinding.FragmentChosenBinding
import by.ssrlab.fishpits.utils.base.BaseFragment
import by.ssrlab.fishpits.utils.tools.adapters.ChosenAdapter
import by.ssrlab.fishpits.utils.vm.ui.sub.bychosen.ChosenUIVM

class ChosenFragment: BaseFragment() {

    private lateinit var binding: FragmentChosenBinding
    private lateinit var adapter: ChosenAdapter
    private lateinit var list: ArrayList<Int>
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

        list = if (uiVM.chosenOne == 0){
            arrayListOf(0, 1, 2, 3, 4, 5, 6)
        } else {
            arrayListOf(0, 1, 2, 3)
        }

        adapter = ChosenAdapter(list, childFragmentManager)
        binding.points.adapter = adapter
    }
}