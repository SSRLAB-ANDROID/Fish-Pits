package by.ssrlab.fishpits.fragments.search.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ssrlab.fishpits.databinding.FragmentByRiverBinding

class ByRiverFragment: Fragment() {

    private lateinit var binding: FragmentByRiverBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentByRiverBinding.inflate(layoutInflater)

        return binding.root
    }
}