package by.ssrlab.fishpits.fragments.map

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ssrlab.fishpits.MainActivity
import by.ssrlab.fishpits.databinding.FragmentMapBinding

class MapFragment: Fragment() {

    private lateinit var binding: FragmentMapBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMapBinding.inflate(layoutInflater)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            (activity as MainActivity).window.decorView.windowInsetsController!!.show(
                android.view.WindowInsets.Type.statusBars()
            )
        } else {
            (activity as MainActivity).window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_VISIBLE
        }

        return binding.root
    }
}