package pro.ahoora.zhin.om.util

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import java.util.*

/**
 * This class is used to change your application locale and persist this change for the next time
 * that your app is going to be used.
 *
 *
 * You can also change the locale of your application on the fly by using the setLocale method.
 *
 *
 * Created by gunhansancar on 07/10/15.
 */
object LocaleHelper {

    private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
    lateinit var sp: SharedPreferences


    fun onAttach(context: Context, defaultLanguage: String): Context {
        sp = context.getSharedPreferences("local_helper", 0)
        return setLocale(context, defaultLanguage)
    }

    fun getLanguage(): String? {
        return getPersistedData(Locale.getDefault().language)
    }

    private fun setLocale(context: Context, language: String): Context {
        persist(context, language)

        return updateResources(context, language)

    }

    private fun getPersistedData(defaultLanguage: String): String? {
        return sp.getString(SELECTED_LANGUAGE, defaultLanguage)
    }

    private fun persist(context: Context, language: String) {
        val editor = sp.edit()

        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return context.createConfigurationContext(configuration)
    }


}