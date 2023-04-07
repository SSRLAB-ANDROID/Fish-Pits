package by.ssrlab.fishpits.utils.tools.adapters.regriv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.ssrlab.fishpits.R
import by.ssrlab.fishpits.databinding.ItemByRegionBinding
import by.ssrlab.fishpits.utils.base.BaseUIVM
import by.ssrlab.fishpits.utils.vm.main.MainActivityVM
import by.ssrlab.fishpits.utils.vm.ui.sub.bychosen.ChosenUIVM

class ByRegionAdapter(
    private val list: ArrayList<Int>,
    private val chosenUIVM: ChosenUIVM,
    private val activityVM: MainActivityVM,
    private val uiVM: BaseUIVM
): RecyclerView.Adapter<ByRegionAdapter.ByRegionHolder>() {

    inner class ByRegionHolder(val binding: ItemByRegionBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ByRegionHolder {
        val binding = ItemByRegionBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ByRegionHolder(binding)
    }

    override fun onBindViewHolder(holder: ByRegionHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.itemByRegionName.text = "Region ${position + 1}"
                binding.itemByRegion.setOnClickListener {
                    chosenUIVM.chosenOne = 0
                    activityVM.setToolbarTitle("Region ${position + 1}")
                    uiVM.navigate(R.id.action_regRivHolderFragment_to_chosenFragment)
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size
}