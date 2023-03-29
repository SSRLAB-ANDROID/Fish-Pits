package by.ssrlab.fishpits.fragments.map.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import by.ssrlab.fishpits.R
import by.ssrlab.fishpits.databinding.BottomFragmentPointDescriptionBinding
import by.ssrlab.fishpits.utils.vm.sub.map.MapPointVM
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val COLLAPSED_HEIGHT = 228

class PointDescriptionFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomFragmentPointDescriptionBinding
    private val pointVM: MapPointVM by activityViewModels() //Shared with map

    override fun getTheme() = R.style.PointDescBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = BottomFragmentPointDescriptionBinding.inflate(layoutInflater)

        binding.pointDescription.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val density = requireContext().resources.displayMetrics.density

        dialog?.let {

            val bottomSheet = it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.peekHeight = (COLLAPSED_HEIGHT * density).toInt()
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }
}