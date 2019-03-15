package com.innocv.otpdispatcher

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.sms_card_layout.*

class SmsMessageAdapter(var datasource: List<SmsMessage>) : RecyclerView.Adapter<SmsMessageAdapter.SmsMessageViewHolder>() {
    class SmsMessageViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindMessage(smsMessage: SmsMessage) {
            senderLabel.text = smsMessage.sender
            messageLabel.text = smsMessage.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsMessageViewHolder {
        val smsCardLayout = LayoutInflater.from(parent.context).inflate(R.layout.sms_card_layout, parent, false)
        return SmsMessageViewHolder(smsCardLayout)
    }

    override fun getItemCount(): Int {
        return datasource.size
    }

    override fun onBindViewHolder(holder: SmsMessageViewHolder, position: Int) {
        val smsMessage = datasource[position]
        holder.bindMessage(smsMessage)
    }

}
