package com.airthings.openapi.test.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airthings.openapi.test.Greeting
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        lifecycleScope.launch {
            tv.text = "Loading.. ⏱"
            try {
                tv.text = Greeting().greetPet()
            } catch (throwable: Throwable) {
                tv.text = "Something went wrong. 💩"
            }
        }
    }
}
