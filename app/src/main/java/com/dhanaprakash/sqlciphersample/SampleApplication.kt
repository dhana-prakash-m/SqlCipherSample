package com.dhanaprakash.sqlciphersample

import android.app.Application

class SampleApplication : Application() {
    private var database: Database? = null

    companion object {
        private lateinit var instance: SampleApplication

        fun getInstance(): SampleApplication = instance
    }

    override fun onCreate() {
        super.onCreate()
        initInstance()
    }

    private fun initInstance() {
        instance = this
        database = Database.getInstance(this, "Secret_key".encodeToByteArray())
    }

    fun getDatabase(): Database? = database
}