package nz.carso.thedict

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.ichi2.anki.api.AddContentApi
import nz.carso.thedict.Constants.ANKI_PERMISSION

object Utils {
    fun showMessageDialog(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun checkAndRequestPermission(context: Context): Boolean {
        if (context !is Activity) {
            Toast.makeText(context, "context is not Activity, probably bug", Toast.LENGTH_SHORT).show()
            return false
        }
        val permission = ANKI_PERMISSION
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, arrayOf(permission), Constants.ANKI_PERMISSION_REQUEST_CODE)
            return false
        }
        return true
    }

    fun addSimpleCard(context: Context, front: String, back: String) {
        if (AddContentApi.getAnkiDroidPackageName(context) != null) {
            if (!checkAndRequestPermission(context)) {
                return
            }
            val manager = PreferenceManager.getDefaultSharedPreferences(context)
            val deckName = manager.getString("anki_deck_name", "")
            val modelName = manager.getString("anki_model_name", "")
            if (deckName == "") {
                Toast.makeText(context, context.getString(R.string.deck_name_empty), Toast.LENGTH_SHORT).show()
                return
            }
            if (modelName == "") {
                Toast.makeText(context, context.getString(R.string.model_name_empty), Toast.LENGTH_SHORT).show()
            }
            // API available: Add deck and model if required, then add your note
            val api = AddContentApi(context)
            val deckId = getDeckId(api, deckName!!)
            val modelId = getModelId(api, modelName!!)
            api.addNote(modelId, deckId, arrayOf(front, back), null)
        } else {
            // Fallback on ACTION_SEND Share Intent if the API is unavailable
            val shareIntent = ShareCompat.IntentBuilder(context)
                .setType("text/plain")
                .setSubject(front)
                .setText(back)
                .createChooserIntent()

            if (shareIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(shareIntent)
            }
        }
    }

    private fun getDeckId(api: AddContentApi, deckName: String): Long {
        val deckMap = api.deckList
        deckMap.forEach{
            if (it.value == deckName) {
                return it.key
            }
        }
        return api.addNewDeck(deckName)
    }
    private fun getModelId(api: AddContentApi, modelName: String): Long {
        val deckMap = api.modelList
        deckMap.forEach{
            if (it.value == modelName) {
                return it.key
            }
        }
        return api.addNewBasicModel(modelName)
    }

    fun addCardWithWord(context: Context, word: String) {
        val manager = PreferenceManager.getDefaultSharedPreferences()
        for (conf in Config.enabledDict) {
            conf.enabledKey
        }
    }
}