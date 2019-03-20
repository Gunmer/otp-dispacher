package com.innocv.otpdispatcher.data.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import com.innocv.otpdispatcher.data.RetrofitApiFactory
import com.innocv.otpdispatcher.data.repositories.SlackRepository
import com.innocv.otpdispatcher.domain.OtpMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SmsBroadcastReceiver : BroadcastReceiver() {
    private val validSender = listOf("evobanco", "bizum", "halcash")

    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            Log.d("SmsBroadcastReceiver", "Enter in BroadcastReceiver")

            val bundle = intent?.extras ?: throw RuntimeException("Bundle is NULL")
            val pdusObjs = bundle.get("pdus") as? Array<*> ?: throw RuntimeException("PdusObjs is NULL")
            val format = bundle.getString("format")

            pdusObjs.forEach {
                val byteArray = it as ByteArray
                val message = SmsMessage.createFromPdu(byteArray, format)
                val sender = message.displayOriginatingAddress.toLowerCase()
                val messageBody = message.displayMessageBody

                if (validSender.contains(sender)) {

                    Log.d("SmsBroadcastReceiver", "$sender: $messageBody)")

                    val otpMessage = OtpMessage(sender, messageBody)

                    postMessageToSlack(otpMessage)
                }
            }
        } catch (e: Exception) {
            Log.e("SmsBroadcastReceiver", e.localizedMessage, e)
        }
    }

    private fun postMessageToSlack(otpMessage: OtpMessage) {
        val slackRepository = SlackRepository(RetrofitApiFactory())

        CoroutineScope(Dispatchers.Default).launch {
            try {
                slackRepository.sendSlackMessage(otpMessage.message)
            } catch (e: java.lang.Exception) {
                Log.e("SmsBroadcastReceiver", e.localizedMessage, e)
            }
        }
    }

}
