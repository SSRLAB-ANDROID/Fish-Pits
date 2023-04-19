package by.ssrlab.fishpits.utils.tools.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import by.ssrlab.fishpits.databinding.ItemChosenBinding
import by.ssrlab.fishpits.fragments.bychosen.sub.MapPointFragment
import by.ssrlab.fishpits.objects.point.PointCommon
import by.ssrlab.fishpits.utils.vm.main.MainVM
import by.ssrlab.fishpits.utils.vm.ui.sub.bychosen.ChosenUIVM

class ChosenAdapter(
    private val list: List<PointCommon>,
    private val activityVM: MainVM,
    private val uiVM: ChosenUIVM,
    private val childFragmentManager: FragmentManager) :
    RecyclerView.Adapter<ChosenAdapter.ChosenHolder>() {

    inner class ChosenHolder(val binding: ItemChosenBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChosenHolder {
        val binding = ItemChosenBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ChosenHolder(binding)
    }

    override fun onBindViewHolder(holder: ChosenHolder, position: Int) {

        with(holder) {
            with(list[position]) {
                binding.chosenNumber.text = (position + 1).toString()
                binding.chosenLabel.text = activityVM.districts.value!!.find{ it.district.id == this.point.pointDistrictId && it.languageId == this.languageId }!!.districtName
                binding.chosenStartText.text = "${this.point.latStart}, ${this.point.lngStart}"
                binding.chosenFinishText.text = "${this.point.latFinish}, ${this.point.lngFinish}"
                binding.chosenRegionShortDesc.text = Html.fromHtml(this.pointName, Html.FROM_HTML_MODE_LEGACY)
                binding.chosenShowButton.setOnClickListener {
                    uiVM.setPointGeo(this.point)
                    MapPointFragment().show(childFragmentManager, "${this.id}_mapPoint")
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size
}