package pro.ahoora.zhin.om.util

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(context: Context) {

    val hostIP = "default_host_ip"
    var sp: SharedPreferences = context.getSharedPreferences("def", 0)


    fun setLocalHostIP(ip: String) {
        sp.edit().putString(hostIP, ip).apply()
    }

    fun getLocalHostIP(): String {
        return sp.getString(hostIP, "192.168.1.241")!!
    }

}