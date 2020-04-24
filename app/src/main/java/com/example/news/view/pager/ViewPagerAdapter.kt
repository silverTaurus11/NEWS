package com.example.news.view.pager

import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.news.view.everything.EverythingFragment
import com.example.news.view.topheadline.TopHeadlineFragment

class ViewPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            1 -> EverythingFragment.newInstance()
            else -> TopHeadlineFragment.newInstance()
        }
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return ""
    }

    override fun getCount(): Int {
        return 2
    }
}