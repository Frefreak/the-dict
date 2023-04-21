package nz.carso.thedict

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.runBlocking

class ProcessTextActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val selectedText = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)
        Toast.makeText(applicationContext, "ProcessTextActivity: $selectedText", Toast.LENGTH_SHORT).show()
        runBlocking{
            Utils.addCardWithWord(this@ProcessTextActivity, selectedText.toString())
        }
        finish()
    }
}