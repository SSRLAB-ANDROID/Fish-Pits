package by.ssrlab.fishpits.fragments.tabels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import by.ssrlab.fishpits.MainActivity
import by.ssrlab.fishpits.databinding.FragmentTablesBinding
import by.ssrlab.fishpits.utils.base.BaseFragment
import by.ssrlab.fishpits.utils.vm.TablesUIVM
import by.ssrlab.fishpits.utils.vm.main.MainActivityVM

class TablesFragment: BaseFragment() {

    private lateinit var binding: FragmentTablesBinding
    private val activityVM: MainActivityVM by activityViewModels()
    override val viewModel: TablesUIVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTablesBinding.inflate(layoutInflater)

        activityVM.setToolbarTitle("Tables")

        return binding.root
    }
}