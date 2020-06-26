package ru.j2m.autocorrect.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import ru.j2m.autocorrect.R
import ru.j2m.autocorrect.models.Dictionary
import ru.j2m.autocorrect.models.Word


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bAccessibilitySettings.setOnClickListener { v: View? ->
            val actionAccessibilitySettings = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            actionAccessibilitySettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(actionAccessibilitySettings)
        }

        //отобразить словарь
        val dictionaryChangeJson = applicationContext.getString(R.string.dictionary_json)
        val dictionary = Gson().fromJson(dictionaryChangeJson, Dictionary::class.java)

        val stringBuilder = StringBuilder()
        dictionary?.words?.forEach { word: Word? ->
            stringBuilder.appendln(word?.original?.plus(" -> ").plus(word?.to))
        }
        tvDictionary.text = stringBuilder.toString()
    }
}
