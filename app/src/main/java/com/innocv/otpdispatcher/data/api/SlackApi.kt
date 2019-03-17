package com.innocv.otpdispatcher.data.api

import com.innocv.otpdispatcher.data.model.SlackMessage
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

interface SlackApi {

    @POST("services/T80G4P1R8/BH2KR4F0F/63u0jMeZuLfqEUM56VeMeIs2")
    fun postMessageAsync(@Body slackMessage: SlackMessage): Deferred<String>

}