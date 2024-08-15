package com.siskakhnnisa.userapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.siskakhnnisa.userapp.model.DataItem
import androidx.recyclerview.widget.DiffUtil
import androidx.paging.PagingDataAdapter
import com.siskakhnnisa.userapp.databinding.UserItemBinding

class ListUserAdapter: PagingDataAdapter<DataItem, ListUserAdapter.ListViewHolder>(diffCallback) {

    var onClick: ((DataItem) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user!!, onClick)
    }

    class ListViewHolder(private var binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: DataItem,onClick: ((DataItem) -> Unit)?) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .into(ivAvatar)
                tvEmail.text = user.email
                tvFirstname.text = user.firstName
                tvLastname.text = user.lastName

                itemView.setOnClickListener {
                    onClick?.invoke(user)
                }
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}


