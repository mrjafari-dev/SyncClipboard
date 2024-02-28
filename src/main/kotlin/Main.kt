import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }
    val arrayList = remember { mutableStateListOf("") }
    val clipboardListener = ClipboardListener()
    val serverSocket = Server()
    serverSocket.getSocketValue {
        clipboardListener.setClipboardValue(it)
    }
    clipboardListener.getClipboardValue {
        print(it + "\n")
        arrayList.add(it)
    }
    MaterialTheme {
        LazyColumn {
            itemsIndexed(arrayList){index,value->
                Text(value)
            }
        }
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
    }
}

fun main() = application {

    Window(onCloseRequest = ::exitApplication, resizable = false, title = "SyncClipboard") {
        App()
    }

    // Create a thread to continuously monitor the clipboard

}
