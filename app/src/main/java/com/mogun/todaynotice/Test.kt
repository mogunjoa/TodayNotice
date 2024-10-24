package com.mogun.todaynotice

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket

fun main() {
    Thread {
        val port = 8080
        val server = ServerSocket(port)

        while (true) {
            val socket = server.accept()

            // 클라이언트로부터 들어오는 스트림 == 클라이언트의 socket.outputStream
            // 클라이언트에게 데이터를 주는 스트림 == 클라이언트의 socket.inputStream
            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            val printer = PrintWriter(socket.getOutputStream())

            var input: String? = "-1"

            while (input != null && input != "") {
                input = reader.readLine()
            }

            println("READ DATE $input")

            // Header
            printer.println("HTTP/1.1 200 OK")
            printer.println("Content-Type: text/html\r\n")

            // Body
            printer.println("<h1>Hello World</h1>")
            printer.println("\r\n")
            printer.flush()
            printer.close()

            reader.close()

            socket.close()
        }
    }.start()
}