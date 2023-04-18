package by.ssrlab.fishpits.app

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.Locale

class Application: Application() {

    val PREFERENCES = "preferences"
    val LANGUAGE = "language"
    val LOCALE = "locale"

    private var locale = Locale("en")

    private var language = 0
    private val config = Configuration()

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

    override fun onCreate() {
        super.onCreate()

        Locale.setDefault(locale)

    }

    fun setLocale(loc: Locale){
        locale = loc
        config.setLocale(loc)
        context.resources.configuration.setLocale(loc)
    }
}