package pro.ahoora.zhin.om.base

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle

import androidx.appcompat.app.AppCompatActivity

import com.franmontiel.localechanger.LocaleChanger

abstract class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        val newBase2 = LocaleChanger.configureBaseContext(newBase)
        super.attachBaseContext(newBase2)
    }

}
