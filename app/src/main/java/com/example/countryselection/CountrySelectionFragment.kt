package com.example.countryselection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.countryselection.databinding.FragmentCountrySelectionBinding
import com.example.countryselection.ui.CountryAdapter
import com.example.countryselection.ui.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CountrySelectionFragment : Fragment() {

    private var _binding : FragmentCountrySelectionBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var recyclerView : CountryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountrySelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        moveToProfileScreen()
        initializeUI()
        observeSelection()
        confirmButtonSetup()
        swipeToRefresh()
    }

    private fun swipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            sharedViewModel.refreshCountries()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun confirmButtonSetup() {
        binding.btnConfirm.setOnClickListener {
            val action = CountrySelectionFragmentDirections.actionCountrySelectionFragmentToProfileScreen()
            findNavController().navigate(action)
        }
    }

    private fun initializeUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.countries.collect { list ->
                    if (list.isNotEmpty()) {
                        recyclerView.submitList(list)
                    }
                }
            }
        }
    }

    private fun observeSelection() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.selectedCountry.collect { country ->
                    if (country != null) {

                        binding.selectedCountryHeader.visibility = View.VISIBLE
                        binding.btnConfirm.visibility = View.VISIBLE

                        binding.selectedCountryName.text = "${country.name} (+${country.callingCodes[0]})"

                        Glide.with(binding.selectedCountryImage.context)
                            .load(country.flags.png)
                            .circleCrop()
                            .into(binding.selectedCountryImage)
                    } else {
                        binding.selectedCountryHeader.visibility = View.GONE
                        binding.btnConfirm.visibility = View.GONE
                    }
                }
            }
        }
    }


    private fun setUpRecyclerView() {
        recyclerView = CountryAdapter( onCountryClicked = {
            sharedViewModel.selectCountry(it)
        })
        binding.recyclerView.apply {
            adapter = recyclerView
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun moveToProfileScreen(){
        binding.backButton.setOnClickListener {
            val action =
                CountrySelectionFragmentDirections.actionCountrySelectionFragmentToProfileScreen()
            findNavController().navigate(action)
        }
    }
}