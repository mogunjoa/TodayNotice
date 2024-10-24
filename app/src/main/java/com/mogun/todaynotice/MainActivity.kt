package com.mogun.todaynotice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mogun.todaynotice.databinding.ActivityMainBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val client = OkHttpClient()
        val request = okhttp3.Request.Builder()
            .url("http://192.168.0.18:8080")
            .build()

        val callback = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Client", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful) {
                    Log.e("Client", "${response.body?.string()}")
                }
            }
        }

        client.newCall(request).enqueue(callback)

        // 소켓을 이용한 서버 통신
//        Thread {
//            try {
//                val socket = Socket("10.0.2.2", 8080)
//                val printer = PrintWriter(socket.getOutputStream())
//                val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
//
//                printer.println("GET / HTTP/1.1")
//                printer.println("Host: 127.0.0.1:8080")
//                printer.println("User-Agent: android")
//                printer.println("\r\n")
//                printer.flush()
//
//                var input: String? = "-1"
//                while (input != null && input != "") {
//                    input = reader.readLine()
//                    Log.e("Client", input)
//                }
//
//                reader.close()
//                printer.close()
//                socket.close()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }.start()
    }
}