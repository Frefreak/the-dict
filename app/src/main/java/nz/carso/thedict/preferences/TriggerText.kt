package nz.carso.thedict.preferences

import android.content.Context
import android.util.AttributeSet
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import nz.carso.thedict.R
import kotlinx.coroutines.runBlocking
import nz.carso.thedict.Utils

class TriggerText(context: Context, attrs: AttributeSet) : Preference(context, attrs) {
    private var onClickHandler: suspend (Context, String) -> Pair<String, Boolean> = { _: Context, _: String ->
        Pair("on click handler not configured", false)
    }
    fun bindOnClickHandler(func: suspend (Context, String) -> Pair<String, Boolean>) {
        this.onClickHandler = func
    }
    override fun onClick() {
        runBlocking {
            val word = findPreferenceInHierarchy<EditTextPreference>("test_dict_word")?.text
            word?.also {
                val result = this@TriggerText.onClickHandler(context, word)
                if (result.second) {
                    Utils.showMessageDialog(context, "Success", result.first)
                } else {
                    Utils.showMessageDialog(context, "Fail",
                        context.getString(R.string.api_test_failed) + "\n${result.first}")
                }
            } ?: {
                Utils.showMessageDialog(context, "Empty lookup word", "fill the lookup word first")
            }
        }
    }
}
