package by.ssrlab.fishpits.fragments.tables

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import by.ssrlab.fishpits.R
import by.ssrlab.fishpits.databinding.FragmentTablesBinding
import by.ssrlab.fishpits.utils.base.BaseFragment
import by.ssrlab.fishpits.utils.vm.ui.tables.TablesUIVM

class TablesFragment: BaseFragment() {

    private lateinit var binding: FragmentTablesBinding
    override val uiVM: TablesUIVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTablesBinding.inflate(layoutInflater)

        activityVM.setToolbarTitle("Tables")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uiVM.defineNavController(view)
    }

    override fun onResume() {
        super.onResume()

        binding.textByRegions.setOnClickListener {
            uiVM.navigate(R.id.action_tables_fragment_to_regRivHolderFragment)
        }

        binding.textByRivers.setOnClickListener {
            uiVM.navigate(R.id.action_tables_fragment_to_regRivHolderFragment)
        }
    }
}