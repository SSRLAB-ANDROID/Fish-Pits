package by.ssrlab.fishpits.app

import android.app.Application
import android.content.Context
import io.reactivex.rxjava3.subjects.PublishSubject

class Application: Application() {

    val PREFERENCES = "preferences"
    val LANGUAGE = "language"

    private var language = 0

    fun getLanguage() = language
    fun setLanguage(language: Int){
        this.language = language
    }

    var languageSubj = PublishSubject.create<Int>()

    private lateinit var context: Context

    fun getContext() = context
    fun setContext(context: Context){
        this.context = context
    }
}