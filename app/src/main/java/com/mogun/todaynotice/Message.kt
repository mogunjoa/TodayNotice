package com.mogun.todaynotice

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("message")  // 해당 명칭으로 받겠다라는 의미
    val message: String,
)