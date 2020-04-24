package com.example.news.helper

import java.text.SimpleDateFormat
import java.util.*

/***
 * Sebenarnya bisa pake jodaTime tapi karena Librarynya dibatasin makanya dibuat manual :)
 */

class Helper{
    companion object{
        private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
        private val outputFormat = SimpleDateFormat("dd/MM/yyyy")

        fun convertToDate(dateString: String):Date{
            return inputFormat.parse(dateString)
        }

        fun printDate(date: Date): String{
            return outputFormat.format(date)
        }

        fun convertAndPrintDate(dateString: String): String{
            val date = convertToDate(dateString)
            return printDate(date)
        }
    }
}