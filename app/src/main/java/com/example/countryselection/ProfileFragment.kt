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
import com.bumptech.glide.Glide
import com.example.countryselection.databinding.FragmentProfileScreenBinding
import com.example.countryselection.ui.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue


@AndroidEntryPoint
class ProfileScreen : Fragment() {


    private var _binding: FragmentProfileScreenBinding? = null
    private val  binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCollapsibleHeader()
        moveToCountrySelectionScreen()
        observeSelectedCountry()
        onClickEditButton()

    }

    private fun setupCollapsibleHeader() {
        val appBar = binding.appBar
        val closeBtn = binding.closeButton
        val bgImage = binding.bgImg

        // 1. Collapse the header when clicking the background image
        bgImage.setOnClickListener {
            appBar.setExpanded(false, true) // false = collapse, true = animate
        }

        // 2. Expand the header when clicking the "X" button
        closeBtn.setOnClickListener {
            appBar.setExpanded(true, true) // true = expand, true = animate
        }

        // 3. Listen to the scroll/offset changes to toggle "X" visibility
        appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            // totalScrollRange is the max distance the header can move
            val isCollapsed = Math.abs(verticalOffset) >= appBarLayout.totalScrollRange

            // Toggle visibility: Only show "X" when the header is fully tucked away
            closeBtn.visibility = if (isCollapsed) View.VISIBLE else View.GONE

            // Optional: Fade the background image out as it collapses for a smoother look
            val alpha = 1.0f - (Math.abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange)
            bgImage.alpha = alpha
        }
    }

    private fun onClickEditButton(){
        binding.editBtn.setOnClickListener {
            val action = ProfileScreenDirections.actionProfileScreenToCountrySelectionFragment()
            findNavController().navigate(action)
            sharedViewModel.selectCountry(null)
        }
    }

    private fun observeSelectedCountry(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.selectedCountry.collect { country ->
                    if (country != null) {
                          binding.searchLayout.visibility = View.GONE
                          binding.selectedCountryHeader.visibility = View.VISIBLE

                        binding.selectedCountryName.text = "${country.name} (+${country.callingCodes[0]})"

                        // 3. Load the circle image (using Glide or Coil)
                        Glide.with(binding.selectedCountryImage.context)
                            .load(country.flags.png)
                            .circleCrop()
                            .into(binding.selectedCountryImage)

                    }else{
                        binding.searchLayout.visibility = View.VISIBLE
                        binding.selectedCountryHeader.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun moveToCountrySelectionScreen() {
        binding.searchArea.setOnClickListener {
            val action = ProfileScreenDirections.actionProfileScreenToCountrySelectionFragment()
            findNavController().navigate(action)
        }
    }
}