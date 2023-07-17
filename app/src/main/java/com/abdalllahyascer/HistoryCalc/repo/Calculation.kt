package com.abdalllahyascer.HistoryCalc.repo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Calculations_table")
data class Calculation(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id :Int=0,
    val calculationText:String,
    val result: Double,
    val date :String?=""
)