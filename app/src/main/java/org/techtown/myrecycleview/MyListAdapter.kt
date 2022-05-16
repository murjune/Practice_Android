package org.techtown.myrecycleview

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.techtown.myrecycleview.databinding.ItemSampleListBinding

class MyListAdapter(
    private val itemClick: (UserData) -> (Unit)
) : ListAdapter<UserData, RecyclerView.ViewHolder>(homeDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserDataViewHolder(
            ItemSampleListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClick
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserDataViewHolder) {
            holder.onBind(getItem(position))
        }
    }

    class UserDataViewHolder(
        private val binding: ItemSampleListBinding,
        private val itemClick: ((UserData) -> (Unit))? = null
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: UserData) {
            binding.ivProfile.setImageResource(data.gender)
            binding.tvName.text = data.name
            binding.tvIntroduce.text = data.introduce

            binding.root.setOnClickListener {
                itemClick?.invoke(data)
            }
        }
    }

    companion object {
        private val homeDiffUtil = object : DiffUtil.ItemCallback<UserData>() {
            override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
                return oldItem.name === newItem.name
            }

            override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean {
                return oldItem == newItem
            }
        }
    }
}
