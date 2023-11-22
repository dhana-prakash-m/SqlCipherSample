package com.dhanaprakash.sqlciphersample

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory

@androidx.room.Database(
    entities = [Job::class],
    version = 1,
)
@TypeConverters(TypeConverter::class)
abstract class Database : RoomDatabase() {
    companion object {
        private const val DATABASE_NAME = "SampleDatabase.db"

        @Volatile
        private var INSTANCE: Database? = null

        /**
         * To get the database instance
         *
         * @param context       Represent the app context
         * @param encryptionKey The key used for encrypting the database
         * @return database instance
         */
        fun getInstance(context: Context, encryptionKey: ByteArray?): Database {
            System.loadLibrary("sqlcipher")
            val factory = SupportOpenHelperFactory(encryptionKey)
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Database::class.java,
                    DATABASE_NAME
                )
                    .openHelperFactory(factory)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun getJobsDao(): JobsDao
}