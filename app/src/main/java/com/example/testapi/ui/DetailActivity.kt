package com.example.testapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.testapi.R
import com.example.testapi.data.viewmodel.DetailViewModel
import com.example.testapi.data.viewmodel.FragmentAdapter
import com.example.testapi.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var users : String
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follow,
            R.string.folling
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail Profil"
        subscribe()
        fragment()
    }

    private fun subscribe() {
        val username = intent.getStringExtra("login")
        if (username != null) {
            detailViewModel.findApi(username)
            detailViewModel.fragmentApi(username)
            detailViewModel.fragmentUpi(username)
        }

        detailViewModel.setDetailData.observe(this) {
            Glide.with(this).load(it.avatarUrl).into(binding.imgItemPhotoDetail)
            binding.namaOrangDetail.text = it.login
            binding.idOrang.text = it.name
            binding.follower.text = getString(R.string.followers, it.followers.toString())
            binding.following.text = getString(R.string.following, it.following.toString())
        }
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    private fun fragment() {
        users = intent.getStringExtra("login")!!
        val fragmentAdapter = FragmentAdapter(this, users)
        val viewpager : ViewPager2 = binding.viewPager
        viewpager.adapter = fragmentAdapter
        val tabs : TabLayout = binding.tabs
        TabLayoutMediator(tabs,viewpager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBars.visibility = View.VISIBLE
            binding.memuat.visibility = View.VISIBLE
        } else {
            binding.progressBars.visibility = View.GONE
            binding.memuat.visibility = View.GONE
        }
    }
}