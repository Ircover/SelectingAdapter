package ru.ircover.selectionmanager.selectingadapter

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import ru.ircover.selectionmanager.livesource.LiveDataSource

fun RecyclerView.Adapter<*>.observeSelectionChange(lifecycleOwner: LifecycleOwner,
                                                   liveDataSource: LiveDataSource<*>) {
    liveDataSource.observeSelectionChange(lifecycleOwner) { position, _ ->
        notifyItemChanged(position)
    }
}

fun <T> SelectingListAdapter<T>.observeItemsChange(lifecycleOwner: LifecycleOwner,
                                                   liveDataSource: LiveDataSource<T>) {
    liveDataSource.allItems.observe(lifecycleOwner, { items -> setListItems(items) })
}

fun <T, TAdapter> TAdapter.observeAllChanges(lifecycleOwner: LifecycleOwner,
                                             liveDataSource: LiveDataSource<T>)
        where TAdapter : RecyclerView.Adapter<*>,
              TAdapter : SelectingListAdapter<T> {
    observeSelectionChange(lifecycleOwner, liveDataSource)
    observeItemsChange(lifecycleOwner, liveDataSource)
}