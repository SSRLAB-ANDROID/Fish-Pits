package by.ssrlab.fishpits.utils.tools.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import by.ssrlab.fishpits.databinding.ChosenItemBinding
import by.ssrlab.fishpits.fragments.bychosen.sub.MapPointFragment

class ChosenAdapter(private val list: List<Any>, private val childFragmentManager: FragmentManager) :
    RecyclerView.Adapter<ChosenAdapter.ChosenHolder>() {

    inner class ChosenHolder(val binding: ChosenItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChosenHolder {
        val binding = ChosenItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ChosenHolder(binding)
    }

    override fun onBindViewHolder(holder: ChosenHolder, position: Int) {

        with(holder) {
            with(list[position]) {
                binding.number.text = (position + 1).toString()
                binding.background.setOnClickListener {
                    MapPointFragment().show(childFragmentManager, "mapPoint")
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size
}