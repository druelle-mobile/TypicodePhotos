package ovh.geoffrey_druelle.typicodephotos.ui.fragment.list.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ovh.geoffrey_druelle.typicodephotos.data.local.model.PhotoEntity
import ovh.geoffrey_druelle.typicodephotos.ui.fragment.list.ListViewModel

@BindingAdapter(value = ["list","listViewModel"], requireAll = true)
fun setRecyclerViewSource(
    recyclerView: RecyclerView,
    list: List<PhotoEntity>?,
    viewModel: ListViewModel
) {
    recyclerView.adapter?.run {
        if (this is ListAdapter) {
            if (list != null) {
                this.photoList = list as MutableList<PhotoEntity>
            }
            this.notifyDataSetChanged()
        }
    } ?: run {
        list?.let {
            ListAdapter(it as MutableList<PhotoEntity>, viewModel).apply {
                recyclerView.adapter = this
            }
        }
    }
}