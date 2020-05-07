package com.example.news.view.everything

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.news.R
import com.example.news.helper.CategoryConstanta


class EverythingFragment: Fragment() {
    companion object{
        fun newInstance(): EverythingFragment {
            return EverythingFragment()
        }
    }

    private val categoryList = listOf(
        CategoryConstanta.BUSSINES,
        CategoryConstanta.ENTERTAIMENT,
        CategoryConstanta.GENERAL,
        CategoryConstanta.HEALTH,
        CategoryConstanta.SCIENCE,
        CategoryConstanta.SPORTS,
        CategoryConstanta.TECHNOLOGY
    )

    private val categoryAdapter by lazy { CategoryListAdapter() }

    private lateinit var categoryListView: ListView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.constraint_fragment_by_category, container, false) as ViewGroup
        categoryListView = view.findViewById(R.id.category_list)
        categoryListView.adapter = categoryAdapter
        categoryListView.setOnItemClickListener { p0, p1, position, p3 ->
            val label = categoryList[position]
            val intent = Intent(activity, SourceListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.putExtra(SourceListActivity.SOURCES_STRING_KEY, label)
            intent.putExtra(SourceListActivity.CATEGORY_LABEL_KEY, categoryAdapter.getCategoryName(label))
            startActivity(intent)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        categoryAdapter.updateItems(categoryList)
    }

    inner class CategoryListAdapter(): BaseAdapter() {
        private var categoryList: List<String> = listOf()
        private var categoryMap: MutableMap<String, String> = HashMap()

        init {
            initCategoryMap()
        }
        open fun updateItems(categoryList: List<String>){
            this.categoryList = categoryList
            notifyDataSetChanged()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var rowView: View
            // reuse views
            rowView = if (convertView == null) {
                val inflater: LayoutInflater = activity!!.layoutInflater
                inflater.inflate(R.layout.text_list_item, null)
            } else{
                convertView
            }

            val descriptionView = rowView.findViewById(R.id.item_text) as TextView
            descriptionView.text = categoryMap[categoryList[position]]

            return rowView
        }

        override fun getItem(position: Int): Any {
            return categoryList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return categoryList.size
        }

        private fun initCategoryMap(){
            categoryMap[CategoryConstanta.BUSSINES] = resources.getString(R.string.bussiness_label)
            categoryMap[CategoryConstanta.ENTERTAIMENT] = resources.getString(R.string.entertaiment_label)
            categoryMap[CategoryConstanta.GENERAL] = resources.getString(R.string.general_label)
            categoryMap[CategoryConstanta.HEALTH] = resources.getString(R.string.health_label)
            categoryMap[CategoryConstanta.SCIENCE] = resources.getString(R.string.science_label)
            categoryMap[CategoryConstanta.SPORTS] = resources.getString(R.string.sports_label)
            categoryMap[CategoryConstanta.TECHNOLOGY] = resources.getString(R.string.technology_label)
        }

        fun getCategoryName(id: String): String?{
            return categoryMap[id]
        }

    }
}