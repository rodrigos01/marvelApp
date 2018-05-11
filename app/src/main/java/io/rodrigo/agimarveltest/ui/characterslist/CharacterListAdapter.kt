package io.rodrigo.agimarveltest.ui.characterslist

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.rodrigo.agimarveltest.databinding.CharacterListItemBinding
import io.rodrigo.agimarveltest.model.data.MarvelCharacter
import io.rodrigo.agimarveltest.ui.Listing

class CharacterListAdapter(
        private val listing: Listing<MarvelCharacter>,
        lifecycleOwner: LifecycleOwner,
        itemCallback: DiffUtil.ItemCallback<MarvelCharacter>
) : PagedListAdapter<MarvelCharacter, CharacterListAdapter.CharacterViewHolder>(itemCallback) {

    var onItemClicked: ((MarvelCharacter) -> Unit)? = null

    private var status: Listing.Status? = null

    init {
        listing.pagedList.observe(lifecycleOwner, Observer {
            submitList(it)
        })
        listing.status.observe(lifecycleOwner, Observer {
            status = it
            notifyDataSetChanged()
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
        if (status == Listing.Status.STATUS_LOADING) {
            return
        }

        val character = getItem(position)
        holder.binding.character = character

        character?.let {
            holder.binding.root.setOnClickListener { _ ->
                onItemClicked?.invoke(it)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (status != Listing.Status.STATUS_LOADING) {
            super.getItemCount()
        } else {
            listing.pageSize
        }
    }

    class CharacterViewHolder(val binding: CharacterListItemBinding) : RecyclerView.ViewHolder(binding.root)
}