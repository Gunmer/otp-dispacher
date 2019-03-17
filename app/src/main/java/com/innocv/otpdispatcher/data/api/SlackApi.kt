package com.innocv.otpdispatcher.data.api

import com.innocv.otpdispatcher.data.model.SlackMessage
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

interface SlackApi {

    @POST("services/T4YL8A9QT/BH2KTK2MU/3DabkoBlXicfql5QbzxGWW2n")
    fun postMessageAsync(@Body slackMessage: SlackMessage): Deferred<String>

}