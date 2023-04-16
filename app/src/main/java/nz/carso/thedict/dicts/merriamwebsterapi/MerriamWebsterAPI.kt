package nz.carso.thedict.dicts.merriamwebsterapi

import android.content.Context
import androidx.preference.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import nz.carso.thedict.dicts.Dict
import nz.carso.thedict.dicts.getThis

@Serializable
data class MerriamWebsterAPIResponse(val meta: Meta, val hom: Int, val hwi: Hwi, val fl: String,
                                    val def: List<Def>)

@Serializable
data class Meta(val id: String, val src: String, val stems: List<String>)

@Serializable
data class Hwi(val hw: String, val prs: List<Prs>)

@Serializable
data class Prs(val mw: String, val sound: Sound)

@Serializable
data class Sound(val audio: String, val ref: String, val stat: String)

@Serializable
data class Def(val sseq: List<List<Pair<String, List<Pair<String, String>>>>>)


object MerriamWebsterAPI: Dict {
    // TODO
    override suspend fun testAPI(context: Context, word: String): Pair<String, Boolean> {
        var content = "word: $word\n"
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val dictKey = prefs.getString("merriam_webster_dictionary_key", "")
        val thesaurusKey = prefs.getString("merriam_webster_thesaurus_key", "")
        if (dictKey == "" || thesaurusKey == "") {
            return Pair(content + "missing dictionary key or thesaurus key", false)
        }
        val dictUrl = "https://dictionaryapi.com/api/v3/references/collegiate/json/${word}?key=${dictKey}"
        val thesaurusUrl = "https://dictionaryapi.com/api/v3/references/thesaurus/json/${word}?key=${thesaurusKey}"

        return withContext(Dispatchers.IO) {
            val (dictData, dictErr) = getThis<MerriamWebsterAPIResponse>(dictUrl)
            if (dictErr != "") {
                return@withContext Pair("dict result:\n$dictErr", false)
            }
            val (thesaurusData, thesaurusErr) = getThis<MerriamWebsterAPIResponse>(thesaurusUrl)
            if (thesaurusErr != "") {
                return@withContext Pair("thesaurus result:\n$thesaurusErr", false)
            }
            content += "dictionary:\n${dictData.toString()}"
            content += "thesaurus:\n${thesaurusData.toString()}"
            Pair(content, true)
        }
    }

    override suspend fun lookupInHTML(context: Context, word: String): String? {
        TODO("Not yet implemented")
    }

}