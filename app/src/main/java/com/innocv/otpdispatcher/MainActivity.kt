package com.innocv.otpdispatcher

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        val smsMessage = SmsMessage(
            "EvoBanco",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ligula neque, egestas vitae mauris quis, luctus blandit quam. Fusce vehicula diam a varius aliquet. Suspendisse suscipit congue metus, eu finibus lectus maximus sed. Nullam eget enim semper, ornare magna in, tincidunt ante. Aliquam porttitor accumsan varius. Curabitur nec convallis erat. Pellentesque non sagittis justo. Nam ullamcorper arcu non aliquet iaculis. Pellentesque cursus turpis a semper porttitor. Maecenas felis tortor, interdum non libero ut, egestas euismod sem."
        )

        val messages = Collections.nCopies(10, smsMessage)

        messagesRecyclerView.adapter = SmsMessageAdapter(messages)
        messagesRecyclerView.layoutManager = LinearLayoutManager(this)
    }

}
