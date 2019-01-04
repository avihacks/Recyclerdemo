package com.example.shinda16.mykotlin

import android.os.Environment
import android.util.Log
import java.io.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.nio.file.Files.delete




/*
* Copyright (c) 2019 Pfizer. All rights reserved.
* Medic
* Author: SHINDA16
* Authored Date: 1/2/2019
* Description: Logger enables to print logs in system and also save log file in Medic app folder.
*/
class Logger {

    companion object {
        private val isLogEnabled:Boolean = true
        // Debug logs
        @JvmStatic
        fun d(tag: String, message: String) {
            if(isLogEnabled)
            Log.d(tag,message)
            writeLogsToFile(tag,message)

        }
        // Error logs
        @JvmStatic
        fun e(tag: String, message: String) {
            if(isLogEnabled)
            Log.e(tag,message)

            writeLogsToFile(tag,message)
        }

        // Information logs
        @JvmStatic
        fun i(tag: String, message: String) {
            if(isLogEnabled)
            Log.i(tag,message)
            writeLogsToFile(tag,message)
        }

        // Warning logs
        @JvmStatic
        fun w(tag: String, message: String) {
            if(isLogEnabled)
            Log.w(tag,message)
            writeLogsToFile(tag,message)

        }

        // Verbose logs
        @JvmStatic
        fun v(tag: String, message: String) {
            if(isLogEnabled)
            Log.v(tag,message)
            writeLogsToFile(tag,message)
        }

        private fun writeLogsToFile(tag: String,message: String){
            val pathToExternalStorage = Environment.getExternalStorageDirectory()
            //to this path add a new directory path and create new App dir (Medic) in SdCard
            val appDirectory = File(pathToExternalStorage.getAbsolutePath() + "/Medic/Logs")
            // have the object build the directory structure, if needed.
            if (!appDirectory.exists()) {
                appDirectory.mkdirs()
            }
           // val sdf = SimpleDateFormat("dd-MM-yyyy")
          //  val currentDate = sdf.format(Date())
            var currentDate:String? = getCurrentDate().toString()

            Log.e(" Current DATE is  " ,""+ currentDate)

            //test to see if it is a Text file
            val myNewFileName = "Log"+currentDate+".txt"
                //Create a File for the output file data
                val saveFilePath = File(appDirectory, myNewFileName)
                try {

                    var currentTimestamp:String = getCurrentTimeStamp()
                    val newline = "\r\n"
                    val writer = FileWriter(saveFilePath,true)
                    writer.append(" $currentTimestamp $tag  $message $newline")
                    writer.flush()
                    writer.close()

                } catch (e: Exception) {
                    e.printStackTrace()
                }


        }



        fun getCurrentDate():String {
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val currentDate = sdf.format(Date())
            return currentDate.toString()
        }
        fun getCurrentTimeStamp():String {
            val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            return currentDate.toString()
        }

    }

}