package nz.carso.thedict

import nz.carso.thedict.dicts.Dict
import nz.carso.thedict.dicts.freedictionaryapi.FreeDictionaryAPI
import nz.carso.thedict.dicts.merriamwebsterapi.MerriamWebsterAPI

data class DictConf(val prefTestKey: String, val enabledKey: String, val klass: Dict, val name: String)

object Config {
    val enabledDict = listOf(
        DictConf("test_free_dictionary_api", "enabled_free_dictionary", FreeDictionaryAPI, "Free Dictionary"),
        DictConf("test_merriam_webster_api", "enabled_merriam_webster", MerriamWebsterAPI, "Merriam-Webster"),
    )
}