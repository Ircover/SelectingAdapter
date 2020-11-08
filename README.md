# SelectingAdapter

[ ![Download](https://api.bintray.com/packages/ircover/selection-manager/selectingadapter/images/download.svg?version=1.0.0) ](https://bintray.com/ircover/selection-manager/selectingadapter/1.0.0/link)

Gradle dependencies

    implementation 'ru.ircover.selectionmanager:core:1.1.0'
    implementation 'ru.ircover.selectionmanager:livesource:1.0.1'
    implementation 'ru.ircover.selectionmanager:selectingadapter:1.0.0'

It's an extension for [LiveDataSource](https://github.com/Ircover/LiveSource) library for easy creating `RecyclerView.Adapter` class.

## Create Adapter

The easiest way to create list adapter: inherit your class from `BaseSelectingListAdapter`. The only thing you need to do then - to write code for you holder.

    class MyAdapter : BaseSelectingListAdapter<User, MyHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            MyHolder(...)//create your holder view
    }

    class MyHolder(itemView: View) : BaseSelectingListHolder<User>(itemView) {
        override fun bindItem(item: User, isSelected: Boolean, onClick: (() -> Unit)?) {
            //bind your data
        }
    }
    
## Setup Adapter with LiveDataSource

There are 3 things you can easily setup:

    val users = LiveDataSource<User>(MultipleSelection())
    adapter.observeItemsChange(this, users) //after any data source changes it will be delivered into `BaseSelectingListAdapter.items` property
    adapter.observeSelectionChange(this, users) //after any selection changed `Adapter.notifyItemChanged` will be called
    adapter.setCallback(users) //sets callback for `BaseSelectingListAdapter` to direct all selection methods to `LiveDataSource` object

There are 2 additional methods to make more action in 1 string:

    adapter.observeAllChanges(this, users) //it calls `observeItemsChange` and `observeSelectionChange`
    //or
    adapter.fullyInitialize(this, users) //it calls all described above methods
    
## Create your own Adapter

If `BaseSelectingListHolder` is not applicable for some reason just implement `SelectingListAdapter` interface. While `observeItemsChange` and `observeSelectionChange` are extension methods, they will work.

    class MyAdapter : RecyclerView.Adapter<MyHolder>(), SelectingListAdapter<T> { ... }
    //still works
    adapter.observeItemsChange(this, users)
    adapter.observeSelectionChange(this, users)
    //doesn't work as it's not `BaseSelectingListAdapter` anymore
    adapter.setCallback(users)
    adapter.fullyInitialize(this, users)
