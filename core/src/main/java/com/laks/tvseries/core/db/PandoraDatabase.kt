package com.laks.tvseries.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.laks.tvseries.core.dao.MediaDao
import com.laks.tvseries.core.data.db.DBMediaEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [DBMediaEntity::class], version = 2, exportSchema = false)
abstract class PandoraDatabase: RoomDatabase()  {

    abstract fun mediaDao(): MediaDao?

    companion object {
        @Volatile
        private var instance: PandoraDatabase? = null

        val execute: ExecutorService = Executors.newSingleThreadExecutor()

        fun getDatabase(context: Context): PandoraDatabase? {
            if(instance == null) {
                synchronized(PandoraDatabase::class.java) {
                    if (instance == null) {
                        instance = buildDatabase(context)
                    }
                }
            }
            return instance
        }

        private fun buildDatabase(context: Context): PandoraDatabase? {
            return Room.databaseBuilder(context, PandoraDatabase::class.java, "pandoraCoreDB").fallbackToDestructiveMigration().allowMainThreadQueries().build()
        }
    }
}