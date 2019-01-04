package com.example.shinda16.mykotlin


import android.content.pm.PackageManager
import android.app.Activity
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat


class PermissionUtils {
    companion object {


        @JvmStatic
        fun hasPermission(activity: Activity, permission: String): Boolean {
            return  ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
        }

        @JvmStatic
        fun requestPermissions(activity: Activity, requiredPermissionsList: ArrayList<String>, requestcode: Int) {
            ActivityCompat.requestPermissions(activity, requiredPermissionsList.toTypedArray(), requestcode)
        }
    }
}