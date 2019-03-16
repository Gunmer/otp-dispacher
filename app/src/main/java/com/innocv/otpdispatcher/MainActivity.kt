package com.innocv.otpdispatcher

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
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
