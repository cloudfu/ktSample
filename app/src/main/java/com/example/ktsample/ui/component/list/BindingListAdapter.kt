package com.example.ktsample.ui.component.list

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BindingListAdapter<T, VH: RecyclerView.ViewHolder> constructor(
    callback: DiffUtil.ItemCallback<T>
): ListAdapter<T, VH>(callback) {

    private val lock: Any = Any()
    private var isSubmitted: Boolean = false
        private set(value) {
            if (field != value) {
                field = value
            }
        }
    /**
     * Submits a new list to be diffed, and displayed.
     */
    override fun submitList(list: List<T>?) {
        super.submitList(list)
        isSubmitted = list != null
    }

    /**
     * Set the new list to be displayed.
     */
    override fun submitList(list: List<T>?, commitCallback: Runnable?) {
        super.submitList(list, commitCallback)
        isSubmitted = list != null
    }
}