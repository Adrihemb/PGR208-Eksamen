package com.example.pgr208_androideksamn.data.room

import androidx.room.TypeConverter
import com.example.pgr208_androideksamn.data.anime.Images
import com.google.gson.Gson

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun imagesToString(images: Images): String {
        return gson.toJson(images)
    }

    @TypeConverter
    fun toImages(imagesString: String): Images {
        return gson.fromJson(imagesString, Images::class.java)
    }
}
