package by.ssrlab.fishpits.fragments.search.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.ssrlab.fishpits.fragments.appdrawer.AboutProject
import by.ssrlab.fishpits.fragments.search.sub.ByRegionFragment
import by.ssrlab.fishpits.fragments.search.sub.ByRiverFragment

class PagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position){

            0 -> ByRegionFragment()

            1 -> ByRiverFragment()

            else -> AboutProject()
        }
    }
}