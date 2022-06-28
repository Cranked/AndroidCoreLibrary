package com.cranked.androidcorelibrary.binding

import android.widget.BaseAdapter
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

object AdapterBindings {
    @BindingAdapter("setAdapter")
    fun bindRecyclerViewAdapter(view: RecyclerView, mAdapter: RecyclerView.Adapter<*>) {
        view.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = mAdapter
        }
    }
    @BindingAdapter("setSpinnerAdapter")
    fun bindSpinnerViewAdapter(view: AppCompatSpinner, adapter: BaseAdapter) {
        view.adapter = adapter
    }

    @BindingAdapter("setPagerAdapter")
    fun bindPagerAdapter(view: ViewPager, adapter: PagerAdapter) {
        view.adapter = adapter
    }
}