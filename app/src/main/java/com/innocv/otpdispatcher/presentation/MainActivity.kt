package com.innocv.otpdispatcher.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.innocv.otpdispatcher.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        val hasReadSmsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
        val hasReceiveSmsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED

        if (hasReadSmsPermission && hasReceiveSmsPermission) {
            infoLabel.text = "Running..."
        } else {
            val permissions = arrayOf(
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS
            )
            ActivityCompat.requestPermissions(this, permissions, 100)
            infoLabel.text = "Request Permissions"
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode != 100) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }

        if (grantResults.contains(PackageManager.PERMISSION_DENIED)) {
            infoLabel.text = "Permission Denied!!!"
        } else {
            infoLabel.text = "Running..."
        }
    }
}
