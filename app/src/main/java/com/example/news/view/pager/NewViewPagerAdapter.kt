package com.example.news.view.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.news.view.everything.EverythingFragment
import com.example.news.view.topheadline.TopHeadlineFragment

class NewViewPagerAdapter(activity: FragmentActivity):FragmentStateAdapter(activity) {
    private val fragmentList: List<Fragment> =
        listOf(TopHeadlineFragment.newInstance(), EverythingFragment.newInstance())

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}