package by.ssrlab.fishpits.fragments.map.sub

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import by.ssrlab.fishpits.MainActivity
import by.ssrlab.fishpits.R
import by.ssrlab.fishpits.databinding.BottomFragmentPointDescriptionBinding
import by.ssrlab.fishpits.utils.vm.main.MainVM
import by.ssrlab.fishpits.utils.vm.ui.sub.map.sub.MapPointVM
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PointDescriptionFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomFragmentPointDescriptionBinding
    private val activityVM: MainVM by activityViewModels()
    private val pointVM: MapPointVM by activityViewModels()

    /** Shared with map */

    override fun getTheme() = R.style.DescBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = BottomFragmentPointDescriptionBinding.inflate(layoutInflater)

        val activityMain = activity as MainActivity
        val application = activityMain.provideApplication()

        activityMain.turnOffBottomNav()

        binding.textRiver.text =
            activityVM.waterObjects.value!!.find { it.waterObjectId == pointVM.getPoint().point.waterObjId && it.languageId == application.getLanguage() }?.waterObjectName
        binding.textRegion.text =
            activityVM.regions.value!!.find { region -> region.regionId == activityVM.districts.value!!.find { it.district.id == pointVM.getPoint().point.pointDistrictId }?.district?.regionId && region.languageId == application.getLanguage() }?.regionName
        binding.textDistrict.text =
            activityVM.districts.value!!.find { it.district.id == pointVM.getPoint().point.pointDistrictId && it.languageId == application.getLanguage() }?.districtName
        if (pointVM.getPoint().point.lat3 == 0.0 && pointVM.getPoint().point.lng3 == 0.0) {
            binding.textPitStarted.text = "\n${pointVM.getPoint().point.lat1}," +
                    "\n${pointVM.getPoint().point.lng1}\n"
        } else {
            binding.textPitStarted.text = "${pointVM.getPoint().point.lat1}," +
                    "\n${pointVM.getPoint().point.lng1},\n" +
                    "${pointVM.getPoint().point.lat3},\n" +
                    "${pointVM.getPoint().point.lng3}"
        }
        if (pointVM.getPoint().point.lat4 == 0.0 && pointVM.getPoint().point.lng4 == 0.0) {
            binding.textPitFinished.text = "\n${pointVM.getPoint().point.lat2}," +
                    "\n${pointVM.getPoint().point.lng2}\n"
        } else {
            binding.textPitFinished.text = "${pointVM.getPoint().point.lat2}," +
                    "\n${pointVM.getPoint().point.lng2},\n" +
                    "${pointVM.getPoint().point.lat4},\n" +
                    "${pointVM.getPoint().point.lng4}"
        }
        binding.textDescription.text =
            Html.fromHtml(pointVM.getPoint().pointName, Html.FROM_HTML_MODE_LEGACY)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        dialog?.let {

            val bottomSheet =
                it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        (activity as MainActivity).turnOnBottomNav()
    }
}