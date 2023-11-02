package com.bahardev.storyapp.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bahardev.storyapp.data.pref.StoryItem
import com.bahardev.storyapp.databinding.CardStoryBinding
import com.bumptech.glide.Glide

class ListStories( private val listStory: ArrayList<StoryItem> ) : RecyclerView.Adapter<ListStories.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: StoryItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = CardStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    inner class ListViewHolder(val binding: CardStoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = listStory.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with(holder) {
            with(listStory[position]) {
                Glide.with(holder.itemView).load(photo).into(binding.imgItemPhoto)
                binding.tvItemName.text = name
                binding.tvItemDescription.text = description

                holder.itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(listStory[holder.adapterPosition])
                }
            }
        }
    }
}