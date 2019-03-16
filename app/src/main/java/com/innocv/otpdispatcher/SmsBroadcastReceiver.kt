package com.innocv.otpdispatcher

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import org.greenrobot.eventbus.EventBus

class SmsBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            val bundle = intent?.extras ?: throw RuntimeException("Bundle is NULL")
            val pdusObjs = bundle.get("pdus") as? Array<*> ?: throw RuntimeException("PdusObjs is NULL")
            val format = bundle.getString("format")

            pdusObjs.forEach {
                val byteArray = it as ByteArray
                val message = SmsMessage.createFromPdu(byteArray, format)
                val phoneNumber = message.displayOriginatingAddress
                val messageBody = message.displayMessageBody

                Log.d("SmsBroadcastReceiver", "$phoneNumber: $messageBody)")

                val otpMessage = OtpMessage(phoneNumber, messageBody)
                EventBus.getDefault().post(OtpMessageEvent(otpMessage))
            }
        } catch (e: Exception) {
            Log.e("SmsBroadcastReceiver", e.localizedMessage, e)
        }
    }

}
