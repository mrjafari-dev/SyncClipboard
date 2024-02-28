import java.net.ServerSocket
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter


class Server {
    val PORT = 8381
    fun getSocketValue(onGetData:(String)->Unit){

        try {
            val serverSocket = ServerSocket(PORT)
            println("Server is running on port $PORT")

            val clientSocket = serverSocket.accept()
            println("Client connected: ${clientSocket.inetAddress.hostAddress}")

            val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
            val writer = PrintWriter(clientSocket.getOutputStream(), true)

            var message: String?

            do {
                message = reader.readLine()
                println("Received from client: $message")
                writer.println("Server received: $message")
                onGetData(message)
            } while (message != null && message != "bye")

            reader.close()
            writer.close()
            clientSocket.close()
            serverSocket.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}