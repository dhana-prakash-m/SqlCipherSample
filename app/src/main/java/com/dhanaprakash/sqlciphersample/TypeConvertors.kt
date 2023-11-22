package com.dhanaprakash.sqlciphersample

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object TypeConverter {
    @TypeConverter
    @JvmStatic
    fun fromDownloadDocument(value: List<DownloadDocument>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<DownloadDocument>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    @JvmStatic
    fun toDownloadDocument(value: String): List<DownloadDocument>? {
        val gson = Gson()
        val type = object : TypeToken<List<DownloadDocument>?>() {}.type
        return gson.fromJson(value, type)
    }
}