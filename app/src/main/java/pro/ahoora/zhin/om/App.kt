package pro.ahoora.zhin.om

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import pro.ahoora.zhin.om.util.LocaleHelper
import com.franmontiel.localechanger.LocaleChanger
import java.util.*


class App : Application() {


    private val supportedLocales = listOf(
            Locale("fa", "rIR"),
            Locale("en", "US")

    )


    override fun onCreate() {
        super.onCreate()
        LocaleChanger.initialize(baseContext,supportedLocales)
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleChanger.onConfigurationChanged()
    }
}
