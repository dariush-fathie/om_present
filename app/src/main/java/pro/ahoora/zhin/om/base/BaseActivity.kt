package pro.ahoora.zhin.om.base

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import com.franmontiel.localechanger.LocaleChanger
import com.franmontiel.localechanger.utils.ActivityRecreationHelper
import pro.ahoora.zhin.om.util.SharedPrefs

abstract class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        val newBase2 = LocaleChanger.configureBaseContext(newBase)
        super.attachBaseContext(newBase2)
    }

    override fun onDestroy() {
        ActivityRecreationHelper.onDestroy(this)
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        ActivityRecreationHelper.onResume(this)
    }

}
