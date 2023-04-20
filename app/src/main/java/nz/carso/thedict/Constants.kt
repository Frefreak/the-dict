package nz.carso.thedict

object Constants {
    const val ANKI_PERMISSION_REQUEST_CODE = 1
    const val ANKI_PERMISSION = "com.ichi2.anki.permission.READ_WRITE_DATABASE"
    const val ANKI_MODEL_CSS = """
div.card-front {
  font-size: 30px;
  text-align: center;
}
div.free-dictionary {
    font-family: Arial, sans-serif;
    background-color: #f0f0f0;
    padding: 8px;
}

div.free-dictionary .card {
    width: 400px;
    padding: 20px;
    background-color: #ffffff;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    margin: 50px auto;
}

div.free-dictionary .word {
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 10px;
    color: #2c3e50;
}

div.free-dictionary .phonetics {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
}

div.free-dictionary .phonetic {
    font-size: 18px;
    font-style: italic;
    margin-right: 10px;
    color: #8e44ad;
}

div.free-dictionary .origin {
    font-size: 14px;
    color: #777;
    margin-bottom: 20px;
}

div.free-dictionary .origin-label {
    font-weight: bold;
    color: #34495e;
}

div.free-dictionary .meanings {
    border-top: 1px solid #eee;
    padding-top: 20px;
}

div.free-dictionary .meaning:not(:last-child) {
    margin-bottom: 20px;
}

div.free-dictionary .partOfSpeech {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 5px;
    color: #2980b9;
}

div.free-dictionary .definition {
    font-size: 16px;
    margin-bottom: 5px;
    color: #2c3e50;
}

div.free-dictionary .example {
    font-size: 14px;
    color: #777;
    margin-bottom: 5px;
}

div.free-dictionary .synonyms, .antonyms {
    font-size: 14px;
    color: #34495e;
}
"""

}