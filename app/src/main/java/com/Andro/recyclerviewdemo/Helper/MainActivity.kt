package com.example.shinda16.mykotlin

import android.Manifest
import android.Manifest.permission.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.widget.Toast



class MainActivity : AppCompatActivity(){

    private val PERMISSION_REQUEST_CODE = 101
    private lateinit var context: Context;
    private val PHONE_STATE_PERMISSION_CODE = 105
    val requiredPermissionsArray: ArrayList<String> = ArrayList()
    val requestSinglePermission:ArrayList<String> =ArrayList()


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                message2.setText(R.string.title_home)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                message2.setText(R.string.title_home)

                if(!PermissionUtils.hasPermission(this,Manifest.permission.CAMERA))
                    PermissionUtils.requestPermissions(this,requestSinglePermission,PERMISSION_REQUEST_CODE)

                else {
                    Toast.makeText(context, " Permission Granted", Toast.LENGTH_SHORT)
                    message.text = "Dashbord Write per granted"
                }

                Logger.e("MainActivity","Test Logs")

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                message2.setText(R.string.title_notifications)


                if(!PermissionUtils.hasPermission(this, READ_PHONE_STATE)||
                        !PermissionUtils.hasPermission(this, WRITE_EXTERNAL_STORAGE))
                    PermissionUtils.requestPermissions(this, requiredPermissionsArray,PHONE_STATE_PERMISSION_CODE)

                else {
                    Toast.makeText(context, " Permission Granted", Toast.LENGTH_SHORT)
                    message2.text = "Dashbord phone per granted"
                }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this

        requiredPermissionsArray.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        requiredPermissionsArray.add(Manifest.permission.READ_PHONE_STATE)

        requestSinglePermission.add(Manifest.permission.CAMERA)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    @SuppressLint("ShowToast")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {

            PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Snackbar.make(findViewById(android.R.id.content), "Permission Granted", Snackbar.LENGTH_SHORT)
                    Toast.makeText(this, " Granted", Toast.LENGTH_SHORT)
                    message.text = "Dashbord Camera per granted"

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Not Granted", Toast.LENGTH_SHORT)
                    message.text = "Dashbord Camera per Denied"

                }
                return
            }

            PHONE_STATE_PERMISSION_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    message2.text = "Dashbord Phone per granted"

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    message2.text = "Dashbord Phone per Denied"

                }
                return
            }


        }


    }

    // request permission sample code

  /*  @JvmStatic
    fun requestPermissions(activity: Activity, permission:String, requestCode: Int) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(activity,
                permission)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    permission)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(permission),
                    requestCode
                )

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(permission),
                    requestCode
                )

                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }
*/

    /* @JvmStatic
        fun requestMultiplePermissions(activity: Activity,args: Array<String>, requestCode: Int) {
           // String strPermisssionsList = null;
            var permissionList:String? =""
            var permissionList2:String =""


            for (item in args){
                println(item)

                // gets the size of array
               // args.size


            }

            permissionList2 =removeLastChar(permissionList)
            Log.e("Permissions", """$permissionList""")

         //   permissionList2 = permissionList2.replace("android.permission.", "")

            Log.e("PermissionsList2", """$permissionList2""")



            ActivityCompat.requestPermissions(activity,
                arrayOf(permissionList2),
                        requestCode)


        }

        private fun removeLastChar(str: String?): String {
            return str!!.substring(0, str!!.length - 1)
        }
*/
}
