package by.ssrlab.fishpits.fragments.search.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ssrlab.fishpits.databinding.FragmentByRegionBinding

class ByRegionFragment: Fragment() {

    private lateinit var binding: FragmentByRegionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentByRegionBinding.inflate(layoutInflater)

        return binding.root
    }
}