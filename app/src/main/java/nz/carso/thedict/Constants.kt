package nz.carso.thedict

object Constants {
    const val ANKI_PERMISSION_REQUEST_CODE = 1
    const val ANKI_PERMISSION = "com.ichi2.anki.permission.READ_WRITE_DATABASE"
    const val ANKI_MODEL_CSS = """
div.free-dictionary .card {
  /* border: 1px solid #ccc; */
  /* border-radius: 10px; */
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
  margin: 10px;
  padding: 10px;
  /* width: 300px; */
}

div.free-dictionary .pronunciation-title {
  font-size: 18px;
  margin-bottom: 5px;
  color: #4285F4;
}

div.free-dictionary .audio {
  margin-bottom: 5px;
}

div.free-dictionary .meanings-title {
  font-size: 18px;
  margin-bottom: 5px;
  color: #4285F4;
}

div.free-dictionary .part-of-speech {
  font-weight: bold;
  margin-bottom: 5px;
}

div.free-dictionary .definition {
  margin: 0;
}

div.free-dictionary .example {
  margin: 0;
  font-style: italic;
}

div.free-dictionary .example-tag {
  font-weight: bold;
  color: #34A853;
}
"""

}