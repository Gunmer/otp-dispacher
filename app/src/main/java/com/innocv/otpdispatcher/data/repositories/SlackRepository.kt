package com.innocv.otpdispatcher.data.repositories

import android.util.Log
import com.innocv.otpdispatcher.data.RetrofitApiFactory
import com.innocv.otpdispatcher.data.api.SlackApi
import com.innocv.otpdispatcher.data.model.SlackMessage

class SlackRepository(private val apiFactory: RetrofitApiFactory) {

    suspend fun sendSlackMessage(message: String) {
        val slackApi = apiFactory.create(SlackApi::class.java)

        val response = slackApi.postMessageAsync(SlackMessage(message)).await()
        Log.d("SlackRepository", "Response: $response")
    }

}