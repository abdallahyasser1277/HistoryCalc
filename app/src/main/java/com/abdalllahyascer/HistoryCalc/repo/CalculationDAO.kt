package com.abdalllahyascer.HistoryCalc.repo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CalculationDAO {


    @Insert
    suspend fun saveCalculation(calculation: Calculation)

    @Query("select * from Calculations_table" )
    suspend fun getAll():List<Calculation>

    @Query("select * from Calculations_table where id== :calculationId " )
    suspend fun getCalculationById(calculationId :Int):Calculation

    @Delete()
    suspend fun deleteCalculation(calculation: Calculation)
    @Update
    suspend fun updateCalculation(calculation: Calculation)


}