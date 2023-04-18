package nz.carso.thedict.dicts.freedictionaryapi

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.html.DIV
import kotlinx.html.TagConsumer
import kotlinx.html.audio
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.h3
import kotlinx.html.html
import kotlinx.html.p
import kotlinx.html.source
import kotlinx.html.stream.createHTML
import kotlinx.serialization.Serializable
import nz.carso.thedict.dicts.Dict
import nz.carso.thedict.dicts.getThis

@Serializable
data class FreeDictionaryAPIResponse(val word: String, val phonetic: String? = null,
                                     val phonetics: List<Phonetic>, val origin: String? = null,
                                    val meanings: List<Meaning>)

@Serializable
data class Phonetic(val text: String, val audio: String? = null)
@Serializable
data class Meaning(val partOfSpeech: String, val definitions: List<Definition>)
@Serializable
data class Definition(val definition: String, val example: String? = null, val synonyms: List<String>,
                      val antonyms: List<String>)

object FreeDictionaryAPI: Dict {
    override suspend fun testAPI(context: Context, word: String): Pair<String, Boolean> {
        var content = "word: $word\n"
        val url = "https://api.dictionaryapi.dev/api/v2/entries/en/$word"

        return withContext(Dispatchers.IO) {
            val (jsonData, err) = getThis<List<FreeDictionaryAPIResponse>>(url)
            if (err != "") {
                return@withContext Pair(err, false)
            }
            if (jsonData!!.isNotEmpty()) {
                val data = jsonData[0]
                content += data.toString()
                Pair(content, true)
            } else {
                val error = "got empty result list"
                Pair(error, false)
            }
        }
    }

    override suspend fun lookupInHTML(context: Context, word: String): String? {
        val url = "https://api.dictionaryapi.dev/api/v2/entries/en/$word"
        val obj = withContext(Dispatchers.IO) {
            val (jsonData, err) = getThis<List<FreeDictionaryAPIResponse>>(url)
            if (err != "") {
                Toast.makeText(context, err.toString(), Toast.LENGTH_SHORT).show()
                return@withContext null
            }
            if (jsonData!!.isEmpty()) {
                Toast.makeText(context, "result is empty", Toast.LENGTH_SHORT).show()
                return@withContext null
            }
            jsonData[0]
        } ?: return null
        val html = createHTML().div {
            div(classes="free-dictionary") {
                div(classes="card") {
                    div(classes="card-body") {
                        div(classes="pronunciation") {
                            h3(classes = "pronunciation-title") {
                                +"Pronunciation"
                            }
                            if (obj.phonetics.isNotEmpty() && obj.phonetics[0].audio != null) {
                                audio(classes = "audio") {
                                    controls = true
                                    source {
                                        src = obj.phonetics[0].audio!!
                                        type = "audio/mp3"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return html
    }
}