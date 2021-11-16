package dev.bahodir.skipadsapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao

    companion object {
        private var instance : UserDatabase? = null

        @Synchronized
        fun getInstance(context: Context) : UserDatabase {
            if (instance == null) {
                instance =  Room
                    .databaseBuilder(context, UserDatabase::class.java, "helper")
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}