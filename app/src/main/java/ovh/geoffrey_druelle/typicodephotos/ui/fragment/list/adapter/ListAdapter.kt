package ovh.geoffrey_druelle.typicodephotos.ui.fragment.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import ovh.geoffrey_druelle.typicodephotos.R
import ovh.geoffrey_druelle.typicodephotos.data.local.model.PhotoEntity
import ovh.geoffrey_druelle.typicodephotos.databinding.ListItemBinding
import ovh.geoffrey_druelle.typicodephotos.ui.activity.MainActivity
import ovh.geoffrey_druelle.typicodephotos.ui.fragment.details.DetailsFragment
import ovh.geoffrey_druelle.typicodephotos.ui.fragment.list.ListViewModel
import ovh.geoffrey_druelle.typicodephotos.utils.ext.paint
import kotlin.coroutines.CoroutineContext

class ListAdapter(
    var photoList: MutableList<PhotoEntity> = mutableListOf(),
    private val listViewModel: ListViewModel
) : RecyclerView.Adapter<ListAdapter.ListViewHolder>(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var job: Job = Job()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = photoList.size

    override fun onBindViewHolder(holder: ListAdapter.ListViewHolder, position: Int) {
        val photo = photoList[position]
        holder.binding.photoListItem = photo

        holder.binding.albumId.text = photo.albumId.toString()
        holder.binding.titleItem.text = photo.title
        paint(photo.thumbnailUrl, holder.binding.thumbnailItem)

        holder.binding.photoCard.setOnClickListener {
            openDetails(photo)
        }
    }

    private fun openDetails(photo: PhotoEntity) {
        val fragmentTransaction = MainActivity.instance.supportFragmentManager.beginTransaction()
        DetailsFragment.show(fragmentTransaction, photo)
    }

    inner class ListViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding: ListItemBinding = DataBindingUtil.bind(view)!!
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        job.cancel()
        super.onDetachedFromRecyclerView(recyclerView)
    }
}