package by.ssrlab.fishpits.fragments.appdrawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ssrlab.fishpits.databinding.FragmentAboutProjectBinding

class AboutProject: Fragment() {

    private lateinit var binding: FragmentAboutProjectBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAboutProjectBinding.inflate(layoutInflater)

        return binding.root
    }
}