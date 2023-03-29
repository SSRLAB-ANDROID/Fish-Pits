package by.ssrlab.fishpits.fragments.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.ssrlab.fishpits.MainActivity
import by.ssrlab.fishpits.R
import by.ssrlab.fishpits.databinding.FragmentPreviewBinding
import by.ssrlab.fishpits.utils.vm.PreviewVM

class PreviewFragment : Fragment() {

    private lateinit var binding: FragmentPreviewBinding
    private val viewModel: PreviewVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPreviewBinding.inflate(layoutInflater)

        viewModel.hideUI(activity as MainActivity)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.defineNavController(binding.root)
    }

    override fun onResume() {
        super.onResume()

        viewModel.startFragmentEnter(binding, requireContext()) {
            viewModel.navigate(R.id.action_previewFragment_to_mapFragment)
        }
    }
}