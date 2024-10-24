package com.mogun.todaynotice

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.google.gson.Gson
import com.mogun.todaynotice.databinding.ActivityMainBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var serverHost = ""

        binding.serverHostEditText.addTextChangedListener {
            serverHost = it.toString()
        }

        binding.confirmButton.setOnClickListener {
            if(serverHost.isEmpty()) {
                Toast.makeText(this, "서버 주소를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            requestServer(serverHost)
        }
    }

    private fun requestServer(serverHost: String) {
        val request = okhttp3.Request.Builder()
            .url("http://$serverHost:8080")
            .build()

        val callback = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "수신에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
                Log.e("Client", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful) {
                    val response = response.body?.string()  // runOnUiThread에 네트워크 작업을 넣어줘서는 안된다.
                    val message = Gson().fromJson(response, Message::class.java)

                    runOnUiThread { // 비동기 작업내의 UI 작업은 runOnUiThread에 넣어줘야 한다.
                        binding.informationTextView.isVisible = true
                        binding.informationTextView.text = message.message

                        binding.serverHostEditText.isVisible = false
                        binding.confirmButton.isVisible = false
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "수신에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        client.newCall(request).enqueue(callback)
    }
}