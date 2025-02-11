package com.poc.pdld.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.poc.pdld.Converter
import com.poc.pdld.data.Results


@TypeConverters(Converter::class)
@Database(entities = [Results::class], version = 2)
abstract class StudentDatabase : RoomDatabase() {

    abstract fun resultDao(): ResultDao

    companion object{

        @Volatile
        private var INSTANCE: StudentDatabase? = null

        fun getInstance(context: Context): StudentDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentDatabase::class.java,
                    "student_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE student_results ADD COLUMN last_synced INTEGER")
                db.execSQL("ALTER TABLE student_results ADD COLUMN is_synced INTEGER DEFAULT 0 NOT NULL")
            }
        }

    }

}