package com.example.testapi.data.viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testapi.ui.FollowingFragment

class FragmentAdapter(activity: AppCompatActivity, users: String) : FragmentStateAdapter(activity) {

    private var username: String = users

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowingFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowingFragment.POSITION, position + 1)
            putString(FollowingFragment.USERNAME, username)
        }
        return fragment
    }
}