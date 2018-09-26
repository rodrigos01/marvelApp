package io.rodrigo.agimarveltest.ui.characterdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import io.rodrigo.agimarveltest.databinding.ComicGridItemBinding
import io.rodrigo.agimarveltest.model.data.Comic
import io.rodrigo.agimarveltest.ui.BindingViewHolder

class ComicGridAdapter(
        comics: LiveData<List<Comic>>,
        lifecycleOwner: LifecycleOwner
) : ListAdapter<Comic, BindingViewHolder<ComicGridItemBinding>>(
        object : DiffUtil.ItemCallback<Comic>() {
            override fun areItemsTheSame(oldItem: Comic, newItem: Comic) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Comic, newItem: Comic) = oldItem == newItem
        }
) {

    init {
        comics.observe(lifecycleOwner, Observer {
            submitList(it)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<ComicGridItemBinding> {
        val binding = ComicGridItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<ComicGridItemBinding>, position: Int) {
        val item = getItem(position)
        holder.binding.comic = item
    }

}