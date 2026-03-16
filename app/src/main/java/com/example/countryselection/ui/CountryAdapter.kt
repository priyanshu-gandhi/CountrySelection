package com.example.countryselection.ui

import android.media.browse.MediaBrowser
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.R
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.countryselection.databinding.ItemCountryRvBinding
import com.example.countryselection.domain.Country

class CountryAdapter(
    private val onCountryClicked: (Country) -> Unit
) : ListAdapter<Country, CountryAdapter.CountryViewHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryAdapter.CountryViewHolder {
        val binding = ItemCountryRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryAdapter.CountryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CountryViewHolder(private val binding: ItemCountryRvBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(country: Country){
            binding.countryName.text = country.name
            binding.countryCode.text = "(+" +  country.callingCodes[0] +")"

            Glide.with(binding.countryImage.context)
                .load(country.flags.png)
                .circleCrop()
                .into(binding.countryImage)


            binding.root.setOnClickListener {
                onCountryClicked(country)
            }
        }


    }


    class DiffUtilCallBack(): DiffUtil.ItemCallback<Country>(){
        override fun areItemsTheSame(
            oldItem: Country,
            newItem: Country
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Country,
            newItem: Country
        ): Boolean {
           return oldItem == newItem
        }

    }
}