package com.bahardev.storyapp.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bahardev.storyapp.data.adapter.ListStories
import com.bahardev.storyapp.data.adapter.LoadingStateAdapter
import com.bahardev.storyapp.data.api.ListStoryItem
import com.bahardev.storyapp.databinding.HomeBinding
import com.bahardev.storyapp.view.StoryViewModel
import com.bahardev.storyapp.view.StoryViewModelFactory
import com.bahardev.storyapp.view.ViewModelFactory
import com.bahardev.storyapp.view.maps.MapsActivity
import com.bahardev.storyapp.view.welcome.WelcomeActivity
import kotlinx.coroutines.launch

class MainActivity: AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel> { ViewModelFactory.getInstance(this) }
    private var storyViewModel: StoryViewModel? = null

    private lateinit var binding: HomeBinding

    // life cycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
            val token = user.token
            Log.d("ZAW", "token in model: $token")
            storyViewModel = StoryViewModelFactory(token).create(StoryViewModel::class.java)
            getStories()
        }

        binding.rvStories.layoutManager = LinearLayoutManager(this)
        action()
    }

    // action
    private fun action() {
        binding.addStory.setOnClickListener {
            startActivity(Intent(this@MainActivity, UploadStoryActivity::class.java))
        }
        binding.apply {
            logout.setOnClickListener {
                lifecycleScope.launch {
                    mainViewModel.deleteSession()
                }
            }
            maps.setOnClickListener {
                startActivity(Intent(this@MainActivity, MapsActivity::class.java))
            }
        }
    }

    private fun getStories(){
        val lStories = ListStories()
        binding.rvStories.adapter = lStories.withLoadStateFooter(
            footer = LoadingStateAdapter {
                lStories.retry()
            }
        )
        storyViewModel?.story?.observe(this) {
            lStories.submitData(lifecycle, it)

            lStories.setOnItemClickCallback(object : ListStories.OnItemClickCallback{
                override fun onItemClicked(data: ListStoryItem) {
                    val detailPage = Intent(this@MainActivity, DetailStoryActivity::class.java)
                    detailPage.putExtra("id", data.id)
                    detailPage.putExtra("name", data.name)
                    detailPage.putExtra("description", data.description)
                    detailPage.putExtra("photo", data.photo)
                    startActivity(detailPage)
                }
            })
        }

    }

}