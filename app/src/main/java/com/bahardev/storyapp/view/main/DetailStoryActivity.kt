package com.bahardev.storyapp.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bahardev.storyapp.databinding.DetailItemBinding
import com.bumptech.glide.Glide

class DetailStoryActivity: AppCompatActivity() {
    private lateinit var binding: DetailItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvDetailDescription.text = intent.getStringExtra("description").toString()
        Glide.with(this@DetailStoryActivity).load(intent.getStringExtra("photo").toString()).into(binding.ivItemPhoto)
        binding.tvItemName.text = intent.getStringExtra("name").toString()
    }
}