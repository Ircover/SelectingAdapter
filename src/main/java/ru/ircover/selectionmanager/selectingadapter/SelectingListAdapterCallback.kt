package ru.ircover.selectionmanager.selectingadapter

interface SelectingListAdapterCallback {
    fun isItemSelected(position: Int): Boolean
    fun clickItem(position: Int)
}