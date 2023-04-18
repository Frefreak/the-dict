package nz.carso.thedict

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val search = findViewById<SearchView>(R.id.search)
        search.setOnQueryTextListener(SearchListener())
        search.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS


        val addBtn = findViewById<Button>(R.id.add_btn)
        addBtn.setOnClickListener(AddBtnListener())
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Constants.ANKI_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(applicationContext, "permission request succeed", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "permission request failed", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    inner class SearchListener: SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            Toast.makeText(applicationContext, query, Toast.LENGTH_SHORT).show()
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return true
        }
    }
    inner class AddBtnListener: View.OnClickListener {
        override fun onClick(v: View?) {
            val search = findViewById<SearchView>(R.id.search)
            val word = search.query.toString()
            if (word == "") {
                Toast.makeText(this@MainActivity, getString(R.string.search_query_is_empty), Toast.LENGTH_SHORT).show()
                return
            }
            val placeholer = """<!DOCTYPE html>
<html>
<head>
<style>
  body { font-family: Arial, sans-serif; }
  h1 { color: blue; }
  p { font-size: 18px; }
  a { color: green; text-decoration: none; }
  .highlight { background-color: yellow; }
  body, div, p {
    text-align: left;
  }
</style>
</head>
<body>
  <h1>Welcome to WebView!</h1>
  <p>This is an example of <span class="highlight">styled</span> HTML content.</p>
  <p>Visit <a href="https://www.example.com">Example.com</a> for more information.</p>
</body>
</html>
"""
            Utils.addCard(this@MainActivity, word, placeholer)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}