package com.bahardev.storyapp.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bahardev.storyapp.data.StoryRepository
import com.bahardev.storyapp.data.api.ListStoryItem
import com.bahardev.storyapp.devin.Injection


class StoryViewModel(repository: StoryRepository) : ViewModel() {
    val story: LiveData<PagingData<ListStoryItem>> = repository.getStory().cachedIn(viewModelScope)
}

class StoryViewModelFactory(private val token: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StoryViewModel(Injection.storyProvideRepository(token)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}