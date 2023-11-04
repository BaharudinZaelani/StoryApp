package com.bahardev.storyapp

import com.bahardev.storyapp.data.api.ListStoryItem

object StoryDataDummy {
    fun generateDummyStory(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for ( i in 0..100 ) {
            val item = ListStoryItem(
                i.toString(),
                "name $i",
                "description $i",
                "photo $i",
                "createdAt $i",
                null,
                null
            )
            items.add(item)
        }
        return items
    }
}