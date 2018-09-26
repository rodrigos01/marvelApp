package io.rodrigo.agimarveltest.ui.characterslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import io.rodrigo.agimarveltest.databinding.CharacterListItemBinding
import io.rodrigo.agimarveltest.model.data.MarvelCharacter
import io.rodrigo.agimarveltest.ui.BindingViewHolder
import io.rodrigo.agimarveltest.ui.Listing

class CharacterListAdapter(
        private val listing: Listing<MarvelCharacter>,
        lifecycleOwner: LifecycleOwner,
        itemCallback: DiffUtil.ItemCallback<MarvelCharacter>
) : PagedListAdapter<MarvelCharacter, BindingViewHolder<CharacterListItemBinding>>(itemCallback) {

    var onItemClicked: ((MarvelCharacter, View) -> Unit)? = null

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<CharacterListItemBinding> {
        val binding = CharacterListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<CharacterListItemBinding>, position: Int) {
        if (status == Listing.Status.STATUS_LOADING) {
            return
        }

        val character = getItem(position)
        holder.binding.character = character

        ViewCompat.setTransitionName(holder.binding.avatar, character?.id.toString())

        character?.let {
            holder.binding.root.setOnClickListener { _ ->
                onItemClicked?.invoke(it, holder.binding.avatar)
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
}