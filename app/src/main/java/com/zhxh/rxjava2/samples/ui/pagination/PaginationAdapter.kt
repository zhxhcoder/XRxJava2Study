package com.zhxh.rxjava2.samples.ui.pagination

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.zhxh.rxjava2.samples.R

import java.util.ArrayList

/**
 * Created by zhxh on 15/03/17.
 */

class PaginationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal var items: MutableList<String> = ArrayList()

    internal fun addItems(items: List<String>) {
        this.items.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private class ItemViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal fun bind(content: String) {
            (itemView as TextView).text = content
        }

        companion object {

            internal fun create(parent: ViewGroup): ItemViewHolder {
                return ItemViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_pagination, parent, false))
            }
        }
    }
}
