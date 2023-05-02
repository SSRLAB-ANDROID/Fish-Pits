package by.ssrlab.fishpits.utils.tools.adapters.regriv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.ssrlab.fishpits.MainActivity
import by.ssrlab.fishpits.R
import by.ssrlab.fishpits.databinding.ItemByRegionBinding
import by.ssrlab.fishpits.objects.Region
import by.ssrlab.fishpits.utils.base.BaseUIVM
import by.ssrlab.fishpits.utils.vm.main.MainVM
import by.ssrlab.fishpits.utils.vm.ui.sub.bychosen.ChosenUIVM
import by.ssrlab.fishpits.utils.vm.ui.sub.tables.regriv.RegRivUIVM

class ByRegionAdapter(
    private val list: ArrayList<Region>,
    private val chosenUIVM: ChosenUIVM,
    private val activityVM: MainVM,
    private val regRivUIVM: RegRivUIVM,
    private val activity: MainActivity,
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
                binding.itemByRegionName.text = this.regionName
                binding.itemByRegion.setOnClickListener {
                    chosenUIVM.access = "region"
                    regRivUIVM.toolbarTitle = activity.resources.getString(R.string.by_regions)
                    chosenUIVM.chosenId = this.regionId
                    activityVM.setToolbarTitle(this.regionName)
                    uiVM.navigate(R.id.action_regRivHolderFragment_to_chosenFragment)
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size
}