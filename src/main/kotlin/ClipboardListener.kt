import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection

class ClipboardListener {
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    var CLP_FLAG = ""
    fun getClipboardValue(onChangeClipboardValue: (String) -> Unit) {
        Thread {
            while (true) {
                try {
                    // Check if there is a new data on the clipboard
                    if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                        val data = clipboard.getData(DataFlavor.stringFlavor) as String
                        if (!data.equals(CLP_FLAG)){
                            onChangeClipboardValue(data)
                            CLP_FLAG = data
                        }
                    }
                    // Sleep for some time before checking again
                    Thread.sleep(300)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }.start()
    }
    fun setClipboardValue(value: String) {
        val selection = StringSelection(value)
        if (CLP_FLAG!= value){
            clipboard.setContents(selection, selection)
            CLP_FLAG = value
        }

    }
}