package by.ssrlab.fishpits.fragments.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import by.ssrlab.fishpits.MainActivity
import by.ssrlab.fishpits.R
import by.ssrlab.fishpits.databinding.FragmentPreviewBinding
import by.ssrlab.fishpits.utils.base.BaseFragment
import by.ssrlab.fishpits.utils.vm.PreviewUIVM

class PreviewFragment : BaseFragment() {

    private lateinit var binding: FragmentPreviewBinding
    override val viewModel: PreviewUIVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPreviewBinding.inflate(layoutInflater)

        viewModel.hideUI(activity as MainActivity)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.startFragmentEnter(binding, requireContext()) {
            viewModel.navigate(R.id.action_previewFragment_to_mapFragment)
        }
    }
}