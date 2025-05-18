package com.piyush.moviedbtask.data.local.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@TypeConverters
class Converters {

    @TypeConverter
    fun fromIntList(list: List<Int>?): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toIntList(json: String?): List<Int> {
        if (json.isNullOrEmpty()) return emptyList()
        val type = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(json, type)
    }
}
