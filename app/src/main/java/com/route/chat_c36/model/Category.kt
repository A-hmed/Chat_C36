package com.route.chat_c36.model

import com.route.chat_c36.R

data class Category(
    var id: String,
    var title: String,
    var imageId: Int
){
    companion object{
      val  categories = mutableListOf<Category>(
         Category(id = "sports", title = "Sports", imageId = R.drawable.sports),
          Category(id = "music", title = "Music", imageId = R.drawable.music),
        Category(id = "sports", title = "Sports", imageId = R.drawable.movies)
      )
      fun getCategoryFromId(id: String): Category{
       when (id){
        "sports" -> {
            return categories[0];
        }
        "music" -> {return  categories[1]}
        "movies" -> {return categories[2]}
       }
          return  categories[0]
      }

    }
}
