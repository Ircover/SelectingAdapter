package ru.ircover.selectionmanager.selectingadapter

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import ru.ircover.selectionmanager.livesource.LiveDataSource

abstract class BaseSelectingListHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bindItem(item: T, isSelected: Boolean, onClick: (() -> Unit)?)
}

abstract class BaseSelectingListAdapter<T, VH: BaseSelectingListHolder<T>> : RecyclerView.Adapter<VH>(),
        SelectingListAdapter<T> {
    var items = arrayListOf<T>()
        private set(value) {
            field = value
            notifyDataSetChanged()
        }
    var callback: SelectingListAdapterCallback? = null

    override fun onBindViewHolder(holder: VH, position: Int) {
        val onClick = callback?.let { notNullCallback ->
            { notNullCallback.clickItem(position) }
        }
        holder.bindItem(items[position],
              callback?.isItemSelected(position) ?: false,
                        onClick)
    }

    override fun getItemCount() = items.size

    override fun setListItems(items: ArrayList<T>) {
        this.items = items
    }

    fun <T> setCallback(liveDataSource: LiveDataSource<T>) {
        callback = object : SelectingListAdapterCallback {
            override fun isItemSelected(position: Int) = liveDataSource.isItemSelected(position)
            override fun clickItem(position: Int) {
                liveDataSource.clickPosition(position)
            }
        }
    }

    fun fullyInitialize(lifecycleOwner: LifecycleOwner, liveDataSource: LiveDataSource<T>) {
        observeAllChanges(lifecycleOwner, liveDataSource)
        setCallback(liveDataSource)
    }
}