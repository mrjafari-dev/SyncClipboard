package com.example.sync.clipboard

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class Server {
fun clientSever(ClipboardValue : String){
    val host = "192.168.80.182" // Change this to your server's IP address
    val port = 8381 // Change this to your server's port

    try {
        val socket = Socket(host, port)
        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
        val writer = PrintWriter(socket.getOutputStream(), true)

        val message = ClipboardValue
        writer.println(message)

        var response: String?
        do {
            response = reader.readLine()
            println("Server says: $response")
        } while (response != null)

        reader.close()
        writer.close()
        socket.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
}