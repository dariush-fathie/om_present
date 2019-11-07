package pro.ahoora.zhin.om

import android.app.Application
import android.content.Context
import pro.ahoora.zhin.om.util.LocaleHelper

class App : Application() {


    override fun onCreate() {
        super.onCreate()
    }


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base,LocaleHelper.getLanguage(base)))

    }
}
