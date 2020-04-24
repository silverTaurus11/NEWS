package com.example.news.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.news.R
import com.example.news.view.pager.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter by lazy {ViewPagerAdapter(supportFragmentManager) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pager.adapter = adapter
        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
                if (pager.currentItem == 0) {
                    top_headline_button.setBackgroundDrawable(resources.getDrawable(R.drawable.background_bottom_border_for_tab))
                    by_category_button.setBackgroundColor(resources.getColor(R.color.white))
                } else{
                    by_category_button.setBackgroundDrawable(resources.getDrawable(R.drawable.background_bottom_border_for_tab))
                    top_headline_button.setBackgroundColor(resources.getColor(R.color.white))
                }
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
            }

        })

        top_headline_button.setOnClickListener { pager.setCurrentItem(0, true) }
        by_category_button.setOnClickListener { pager.setCurrentItem(1, true) }
    }

    override fun onBackPressed() {
        if(pager.currentItem == 0){
            super.onBackPressed()
        } else{
            pager.setCurrentItem(0, true)
        }
    }
}
