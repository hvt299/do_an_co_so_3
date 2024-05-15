package com.example.quanlyclbbongda.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.quanlyclbbongda.data.converters.DataTypeConverter
import com.example.quanlyclbbongda.data.daos.FundDAO
import com.example.quanlyclbbongda.data.daos.MemberDAO
import com.example.quanlyclbbongda.data.daos.ScheduleDAO
import com.example.quanlyclbbongda.data.daos.TeamDAO
import com.example.quanlyclbbongda.data.daos.UserDAO
import com.example.quanlyclbbongda.data.models.Fund
import com.example.quanlyclbbongda.data.models.Member
import com.example.quanlyclbbongda.data.models.Schedule
import com.example.quanlyclbbongda.data.models.Team
import com.example.quanlyclbbongda.data.models.User
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [User::class, Team::class, Member::class, Schedule::class, Fund::class], version = 1, exportSchema = true)
@TypeConverters(DataTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDAO(): UserDAO
    abstract fun teamDAO(): TeamDAO
    abstract fun memberDAO(): MemberDAO
    abstract fun scheduleDAO(): ScheduleDAO
    abstract fun fundDAO(): FundDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): AppDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "quanlyclbbongda")
                    .allowMainThreadQueries()
                    .createFromAsset("database/quanlyclbbongda.db")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}