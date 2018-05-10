package io.rodrigo.agimarveltest.ui.characterslist

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.paging.PagedList
import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.rodrigo.agimarveltest.databinding.CharacterListItemBinding
import io.rodrigo.agimarveltest.model.data.MarvelCharacter

class CharacterListAdapter(
        data: LiveData<PagedList<MarvelCharacter>>,
        lifecycleOwner: LifecycleOwner,
        itemCallback: DiffUtil.ItemCallback<MarvelCharacter>
) : PagedListAdapter<MarvelCharacter, CharacterListAdapter.CharacterViewHolder>(itemCallback) {

    init {
        data.observe(lifecycleOwner, Observer {
            submitList(it)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = CharacterListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let {
            holder.binding.character = it
        }
    }


    class CharacterViewHolder(val binding: CharacterListItemBinding) : RecyclerView.ViewHolder(binding.root)
}