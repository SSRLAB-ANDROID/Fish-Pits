package by.ssrlab.fishpits.utils.tools.adapters.regriv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.ssrlab.fishpits.R
import by.ssrlab.fishpits.databinding.ItemByRiverBinding
import by.ssrlab.fishpits.utils.base.BaseUIVM
import by.ssrlab.fishpits.utils.vm.main.MainActivityVM
import by.ssrlab.fishpits.utils.vm.ui.sub.bychosen.ChosenUIVM

class ByRiverAdapter(
    private val list: ArrayList<Int>,
    private val chosenUIVM: ChosenUIVM,
    private val activityVM: MainActivityVM,
    private val uiVM: BaseUIVM
): RecyclerView.Adapter<ByRiverAdapter.ByRiverHolder>() {

    inner class ByRiverHolder(val binding: ItemByRiverBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ByRiverHolder {
        val binding = ItemByRiverBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ByRiverHolder(binding)
    }

    override fun onBindViewHolder(holder: ByRiverHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.itemByRiverName.text = "River ${position + 1}"
                binding.itemByRiver.setOnClickListener {
                    chosenUIVM.chosenOne = 1
                    activityVM.setToolbarTitle("River ${position + 1}")
                    uiVM.navigate(R.id.action_regRivHolderFragment_to_chosenFragment)
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size
}