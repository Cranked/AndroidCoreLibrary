package com.cranked.androidcorelibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewpager.widget.PagerAdapter
import com.cranked.androidcorelibrary.extension.setSafeOnClickListener

abstract class BaseViewBindingPagerAdapter<VDB : ViewDataBinding, T>(
    @LayoutRes private val layoutRes: Int, private var items: MutableList<T>
) : PagerAdapter() {

    private var listener : ClickListener<T>?=null

    interface ClickListener<T>{
        fun onItemClick(item:T,position: Int)
    }

    @Suppress("UNCHECKED_CAST")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutRes, container, false) as VDB
        container.tag = binding
        container.addView(binding.root)
        setBindingModel(binding, items[position], position)
        listener?.let {listener->
            binding.root.setSafeOnClickListener {
                listener.onItemClick(getItem(position),position)
            }
        }

        binding.executePendingBindings()
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        destroyView(container.tag as VDB, position, `object` as View)
        container.removeView(`object`)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int = items.size

    fun getItem(position: Int): T {
        return items[position]
    }

    fun getItems(): List<T> {
        return items
    }

    fun clear() {
        items.clear()
    }

    fun setItems(items: List<T>) {
        this.items = items as MutableList<T>
        notifyDataSetChanged()
    }

    fun setListener(listener: ClickListener<T>){
        this.listener = null
        this.listener =listener
    }

    fun unBind(){
        this.listener = null
    }

    protected abstract fun setBindingModel(binding: VDB, item: T, position: Int)

    open fun destroyView(binding: VDB, position: Int, view: View){

    }
}