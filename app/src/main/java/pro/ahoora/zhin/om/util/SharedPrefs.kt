package pro.ahoora.zhin.om.util

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(context: Context) {

    private val hostIP = "default_host_ip"
    private  val localeSelectedLanguage="Locale.Selected.Language"
    var sp: SharedPreferences = context.getSharedPreferences("def", 0)


    fun setLocalHostIP(ip: String) {
        sp.edit().putString(hostIP, ip).apply()
    }

    fun getLocalHostIP(): String {
        return sp.getString(hostIP, "192.168.1.241")!!
    }

    fun setLocaleSelectedLanguage(lang: String) {
        sp.edit().putString(localeSelectedLanguage, lang).apply()
    }

    fun getLocaleSelectedLanguage(defaultLanguage:String ): String {
        return sp.getString(localeSelectedLanguage, defaultLanguage)!!
    }

}