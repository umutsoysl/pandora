package com.laks.tvseries.pandora.home

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.laks.tvseries.core.base.fragment.CategoryBaseFragment
import com.laks.tvseries.pandora.R

class RecyclerFragmentAdapter(private val fragments: ArrayList<CategoryBaseFragment<*>>, private val fragmentManager: FragmentManager) : RecyclerView.Adapter<RecyclerFragmentAdapter.FragmentHolder>() {
    var fHolder: FragmentHolder? = null
    var parentGroup: ViewGroup? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FragmentHolder {
        parentGroup = parent
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_row, parent, false)
        fHolder = FragmentHolder(view)
        return fHolder!!
    }

    override fun getItemCount(): Int = fragments.size

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onBindViewHolder(holder: FragmentHolder, position: Int) {

        val fragmentItem = fragments[position]
        val fragmentName = fragmentItem::class.java.simpleName as String

        holder.bindFragment(fragmentItem, fragmentManager, fragmentName)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        recyclerView.setOnScrollListener(recyclerViewOnScrollListener)
    }

    class FragmentHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private var fragment: Fragment? = null
        private var fragmentTransaction: FragmentTransaction? = null

        init {
            view.setOnClickListener(this)
        }

        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        fun bindFragment(fragment: Fragment, fragmentManager: FragmentManager, fragmentName: String) {
            this.fragment = fragment
            this.fragmentTransaction = fragmentTransaction

            view.id = View.generateViewId()

            val fragmentTransaction = fragmentManager.beginTransaction()

            if (!fragmentManager.fragments.contains(fragment)) {
                fragmentTransaction.add(view.id, fragment, fragmentName)
                fragmentTransaction.commitAllowingStateLoss()
            }
        }

        override fun onClick(v: View?) {}
    }

    private val recyclerViewOnScrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            var linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager

            val visibleItemCount = linearLayoutManager.childCount
            val totalItemCount = linearLayoutManager.itemCount
            val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
            val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
            val firstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition()
            val lastCompletelyVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()

            val lastItem = firstVisibleItemPosition + visibleItemCount

            Log.d("ZZa visibleItemCount", "\n -----------------")
            Log.d("ZZa visibleItemCount", visibleItemCount.toString())
            Log.d("ZZa totalItemCount", totalItemCount.toString())
            Log.d("ZZa firstVisibleItemPos", firstVisibleItemPosition.toString())
            Log.d("ZZa lastVisibleItemPosi", lastVisibleItemPosition.toString())
            Log.d("ZZa firstCompletelyVisi", firstCompletelyVisibleItemPosition.toString())
            Log.d("ZZa lastCompletelyVisi", lastCompletelyVisibleItemPosition.toString())
        }
    }
}