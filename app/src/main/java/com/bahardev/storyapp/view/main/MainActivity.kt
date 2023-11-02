package com.bahardev.storyapp.view.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bahardev.storyapp.data.adapter.ListStories
import com.bahardev.storyapp.data.api.ApiConfig
import com.bahardev.storyapp.data.pref.StoryItem
import com.bahardev.storyapp.databinding.HomeBinding
import com.bahardev.storyapp.view.ViewModelFactory
import com.bahardev.storyapp.view.maps.MapsActivity
import com.bahardev.storyapp.view.welcome.WelcomeActivity
import kotlinx.coroutines.launch

class MainActivity: AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: HomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
        binding.rvStories.setHasFixedSize(true)
        getAllStories()
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
                    viewModel.deleteSession()
                }
            }
            maps.setOnClickListener {
                startActivity(Intent(this@MainActivity, MapsActivity::class.java))
            }
        }
    }

    private fun getAllStories() {
        binding.loading.visibility = View.VISIBLE
        viewModel.getSession().observe(this) { user ->
            lifecycleScope.launch {
                try{
                    val service = ApiConfig().getApiServiceAuth(user.token)
                    val response = service.getStories()
                    val list = ArrayList<StoryItem>()
                    for( i in response.data ) {
                        list.add(StoryItem(i.id, i.name, i.description, i.photo))
                    }
                    showRecycler(list)

                    binding.loading.visibility = View.GONE
                }catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "Error fetch data : ${e.message}", Toast.LENGTH_SHORT).show()
                    binding.loading.visibility = View.GONE
                }
            }
        }
    }

    private fun showRecycler(list: ArrayList<StoryItem>){
        binding.rvStories.layoutManager = LinearLayoutManager(this)
        val lStories = ListStories(list)
        binding.rvStories.adapter = lStories

        lStories.setOnItemClickCallback(object: ListStories.OnItemClickCallback{
            override fun onItemClicked(data: StoryItem) {
                Toast.makeText(this@MainActivity, "Kamu memeilih : " + data.id, Toast.LENGTH_SHORT).show()

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