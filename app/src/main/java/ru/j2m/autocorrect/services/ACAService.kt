package ru.j2m.autocorrect.services

import android.accessibilityservice.AccessibilityService
import android.os.Bundle
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.EditText
import com.google.gson.Gson
import ru.j2m.autocorrect.R
import ru.j2m.autocorrect.models.Dictionary
import ru.j2m.autocorrect.models.Word

class ACAService : AccessibilityService() {

    companion object {
        private val TAG = ACAService::class.simpleName
        private lateinit var dictionary: Dictionary
    }

    override fun onServiceConnected() {
        super.onServiceConnected()

        val dictionaryChangeJson = applicationContext.getString(R.string.dictionary_json)
        dictionary = Gson().fromJson(dictionaryChangeJson, Dictionary::class.java)
    }

    override fun onInterrupt() {
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

        val packageName = event?.packageName as String
        println("${TAG}: app packageName = ${packageName}")

        //является ли событие от EditText
        val isEditText = event.className == EditText::class.qualifiedName

        if (event.eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED && isEditText) {
            println("${TAG}: TYPE_VIEW_TEXT_CHANGED ${event.text} Added count [${event.addedCount}]  packagename : ${packageName}")

            //получить добавленный текст
            val text = event.text
            var addedText: String? = null
            if (!text.isNullOrEmpty()) {
                addedText = text[0]?.toString()
            }

            //обойти словарь и сравнить с добавленным текстом
            dictionary.words.forEach { word: Word? ->

                if (word?.original == addedText) {
                    val source: AccessibilityNodeInfo = event.source

                    //замена оригинального текста на "to"(если "to" пустая строка, то означает "удаление")
                    val arguments = Bundle()
                    arguments.putCharSequence(
                        AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
                        word?.to
                    )
                    source.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
                }
            }

        }

    }

}
