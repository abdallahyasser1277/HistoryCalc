package com.abdalllahyascer.HistoryCalc.repo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Calculation::class],
    version = 1,
    exportSchema = false
    )
abstract class CalculationDB : RoomDatabase() {

    abstract val dao:CalculationDAO

    companion object{
        @Volatile
        private var daoInstance:CalculationDAO?=null

        fun getDaoInstance(context: Context):CalculationDAO{
            synchronized(this){
                if (daoInstance==null){
                    daoInstance=buildDatabase(context).dao
                }
                return daoInstance as CalculationDAO
            }
        }

        private fun buildDatabase(context: Context):CalculationDB = Room.databaseBuilder(
                context.applicationContext,
                CalculationDB::class.java,
                "calcutations_table"
            ).fallbackToDestructiveMigration().build()
        }
    }

