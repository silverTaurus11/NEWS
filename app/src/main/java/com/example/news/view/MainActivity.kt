package com.example.news.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.news.R
import com.example.news.view.pager.NewViewPagerAdapter
import kotlinx.android.synthetic.main.constraint_activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter by lazy {NewViewPagerAdapter(this) }
    private val viewPagerCallBack by lazy {object: ViewPager2.OnPageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) {
            if (pager.currentItem == 0) {
                top_headline_button.setBackgroundDrawable(resources.getDrawable(R.drawable.background_bottom_border_for_tab))
                by_category_button.setBackgroundColor(resources.getColor(R.color.white))
            } else{
                by_category_button.setBackgroundDrawable(resources.getDrawable(R.drawable.background_bottom_border_for_tab))
                top_headline_button.setBackgroundColor(resources.getColor(R.color.white))
            }
        }
    } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.constraint_activity_main)
        pager.adapter = adapter
        pager.registerOnPageChangeCallback(viewPagerCallBack)
        pager.isUserInputEnabled = false

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

    override fun onDestroy() {
        pager.unregisterOnPageChangeCallback(viewPagerCallBack)
        super.onDestroy()
    }
}
