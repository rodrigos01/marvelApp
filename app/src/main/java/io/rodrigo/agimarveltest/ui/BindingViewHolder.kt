package io.rodrigo.agimarveltest.ui

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BindingViewHolder<T : ViewDataBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)