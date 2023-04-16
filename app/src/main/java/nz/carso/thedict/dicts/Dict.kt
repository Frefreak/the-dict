package nz.carso.thedict.dicts

import android.content.Context
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import kotlinx.serialization.json.Json
import kotlinx.serialization.*

interface Dict {
    suspend fun testAPI(context: Context, word: String): Pair<String, Boolean>
    suspend fun lookupInHTML(context: Context, word: String): String?
}

inline fun <reified T: Any> getThis(url: String): Pair<T?, String> {
    val (_, _, result) = Fuel.get(url).responseJson()
    val (payload, err) = result
    if (err != null) {
        return Pair(null, err.toString())
    }
    val json = Json { ignoreUnknownKeys = true }
    val jsonData = try {
        json.decodeFromString<T>(payload!!.content)
    } catch (exception: IllegalArgumentException) {
        return Pair(null, exception.toString())
    }
    return Pair(jsonData, "")
}
