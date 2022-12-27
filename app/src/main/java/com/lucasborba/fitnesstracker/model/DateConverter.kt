package com.lucasborba.fitnesstracker.model

import androidx.room.TypeConverter
import java.util.Date

class DateConverter { // Foi alterado de object para class, provavelmente alteração na versão tenha causado este erro
    @TypeConverter
    fun toDate(dateLong: Long): Date? {
        return if (dateLong != null) Date(dateLong) else null
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}