package com.innocv.otpdispatcher.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.innocv.otpdispatcher.R
import com.innocv.otpdispatcher.data.RetrofitApiFactory
import com.innocv.otpdispatcher.data.repositories.SlackRepository
import com.innocv.otpdispatcher.domain.OtpMessage
import com.innocv.otpdispatcher.domain.events.OtpMessageEvent
import com.innocv.otpdispatcher.presentation.adapters.OtpMessageAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onResume() {
        super.onResume()

        messagesRecyclerView.adapter = OtpMessageAdapter()
        messagesRecyclerView.layoutManager = LinearLayoutManager(this)

        //postMessageToSlack()
    }

    private fun postMessageToSlack() {
        val slackRepository = SlackRepository(RetrofitApiFactory())
        val otpMessage = OtpMessage("Cristiam", "Mensaje de prueba")
        (messagesRecyclerView.adapter as OtpMessageAdapter).addSmsMessage(otpMessage)

        CoroutineScope(Dispatchers.Default).launch {
            try {
                slackRepository.sendSlackMessage(otpMessage.message)
            } catch (e: Exception) {
                Log.e("SmsBroadcastReceiver", e.localizedMessage, e)
            }
        }
    }

    @Subscribe
    fun onSmsMessageEvent(otpMessageEvent: OtpMessageEvent) {
        (messagesRecyclerView.adapter as OtpMessageAdapter).addSmsMessage(otpMessageEvent.otpMessage)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

}
