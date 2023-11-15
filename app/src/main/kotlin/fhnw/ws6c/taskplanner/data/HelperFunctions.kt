package fhnw.ws6c.taskplanner.data

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object HelperFunctions {
    fun getFormattedDate(date: LocalDate): String {
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yy"))
    }
    fun getFormattedTime(time: LocalTime): String {
        return time.format(DateTimeFormatter.ofPattern("HH:mm"))
    }
}