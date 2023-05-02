package by.ssrlab.fishpits.utils.tools.adapters.regriv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.ssrlab.fishpits.MainActivity
import by.ssrlab.fishpits.R
import by.ssrlab.fishpits.databinding.ItemByRiverBinding
import by.ssrlab.fishpits.objects.WaterObject
import by.ssrlab.fishpits.utils.base.BaseUIVM
import by.ssrlab.fishpits.utils.vm.main.MainVM
import by.ssrlab.fishpits.utils.vm.ui.sub.bychosen.ChosenUIVM
import by.ssrlab.fishpits.utils.vm.ui.sub.tables.regriv.RegRivUIVM

class ByRiverAdapter(
    private val list: ArrayList<WaterObject>,
    private val chosenUIVM: ChosenUIVM,
    private val activityVM: MainVM,
    private val regRivUIVM: RegRivUIVM,
    private val activity: MainActivity,
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
                binding.itemByRiverName.text = this.waterObjectName
                binding.itemByRiver.setOnClickListener {
                    chosenUIVM.access = "water"
                    regRivUIVM.toolbarTitle = activity.resources.getString(R.string.by_rivers)
                    chosenUIVM.chosenId = this.waterObjectId
                    activityVM.setToolbarTitle(this.waterObjectName)
                    uiVM.navigate(R.id.action_regRivHolderFragment_to_chosenFragment)
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size
}