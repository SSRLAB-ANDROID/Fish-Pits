package by.ssrlab.fishpits.fragments.bychosen.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import by.ssrlab.fishpits.MainActivity
import by.ssrlab.fishpits.R
import by.ssrlab.fishpits.databinding.BottomFragmentMapPointBinding
import by.ssrlab.fishpits.utils.vm.ui.sub.bychosen.ChosenUIVM
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MapPointFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomFragmentMapPointBinding
    private val chosenUIVM: ChosenUIVM by activityViewModels() /** Common with ChosenFragment */

    override fun getTheme() = R.style.MapPointBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = BottomFragmentMapPointBinding.inflate(layoutInflater)

        (activity as MainActivity).turnOffBottomNav()

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        dialog?.let {

            val bottomSheet = it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        (activity as MainActivity).turnOnBottomNav()
    }
}