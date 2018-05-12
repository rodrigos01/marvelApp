package io.rodrigo.agimarveltest.ui.characterdetails

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import io.rodrigo.agimarveltest.databinding.ComicGridItemBinding
import io.rodrigo.agimarveltest.model.data.Comic
import io.rodrigo.agimarveltest.ui.BindingViewHolder

class ComicGridAdapter(
        comics: LiveData<List<Comic>>,
        lifecycleOwner: LifecycleOwner
) : ListAdapter<Comic, BindingViewHolder<ComicGridItemBinding>>(
        object : DiffUtil.ItemCallback<Comic>() {
            override fun areItemsTheSame(oldItem: Comic?, newItem: Comic?) = oldItem?.id == newItem?.id
            override fun areContentsTheSame(oldItem: Comic?, newItem: Comic?) = oldItem == newItem
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