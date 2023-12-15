package com.example.testapi.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapi.data.viewmodel.DetailViewModel
import com.example.testapi.databinding.FragmentFollowingBinding
import kotlin.properties.Delegates

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private var position by Delegates.notNull<Int>()
    private val fragmentViewModel by activityViewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var username = ""
        arguments?.let {
            position = it.getInt(POSITION)
            username = it.getString(USERNAME)!!
        }
        if (position == 1) {
            subscribe1()
        } else {
           subscribe2()
        }
    }

    companion object {
        const val POSITION = "position"
        const val USERNAME = "username"
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.memuat.visibility = View.VISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.memuat.visibility = View.GONE
        }
    }

    private fun subscribe1() {
        val fragmentAdapter = ApiAdapter()
        binding.rvHero.adapter = fragmentAdapter
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvHero.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvHero.addItemDecoration(itemDecoration)

        fragmentViewModel.setApiFragment.observe(viewLifecycleOwner) {
            fragmentAdapter.submitList(it)
        }
        fragmentViewModel.isLoadings.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        fragmentViewModel.showError.observe(viewLifecycleOwner) {
            binding.sectionLabel.isVisible = it
        }
        fragmentViewModel.showFailed.observe(viewLifecycleOwner) {
            binding.sectionLabelFailed.isVisible = it
        }
    }

    private fun subscribe2() {
        val fragmentAdapter = ApiAdapter()
        binding.rvHero.adapter = fragmentAdapter
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvHero.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvHero.addItemDecoration(itemDecoration)

        fragmentViewModel.setApiFragments.observe(viewLifecycleOwner) {
            fragmentAdapter.submitList(it)
        }
        fragmentViewModel.isLoadingss.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        fragmentViewModel.showErrors.observe(viewLifecycleOwner) {
            binding.difollow.isVisible = it
        }
        fragmentViewModel.showFaileds.observe(viewLifecycleOwner) {
            binding.sectionLabelFailed.isVisible = it
        }
    }

}