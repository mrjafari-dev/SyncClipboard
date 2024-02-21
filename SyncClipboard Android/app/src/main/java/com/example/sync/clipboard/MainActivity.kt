package com.example.sync.clipboard

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var clipboardManager: ClipboardManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService(Intent(this, ClipboardService::class.java))
        val server = Server()
        clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.addPrimaryClipChangedListener {
            val clipData = clipboardManager.primaryClip
            val item = clipData?.getItemAt(0)
            val clipboardText = item?.text.toString()
            server.clientSever(clipboardText)
            Log.i("4564564664564",clipboardText)
        }
    }
}