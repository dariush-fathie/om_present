package pro.ahoora.zhin.om.ui.splash

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.kotlinpermissions.KotlinPermissions
import kotlinx.android.synthetic.main.activity_splash.*
import pro.ahoora.zhin.om.R
import pro.ahoora.zhin.om.ui.MainActivity
import su.levenetc.android.textsurface.TextBuilder
import su.levenetc.android.textsurface.animations.Delay
import su.levenetc.android.textsurface.animations.Sequential
import su.levenetc.android.textsurface.animations.Slide
import su.levenetc.android.textsurface.contants.Align
import su.levenetc.android.textsurface.contants.Side


class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        /*
        

        
         */

        KotlinPermissions.with(this) // where this is an FragmentActivity instance
                .permissions(Manifest.permission.INTERNET,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.CHANGE_NETWORK_STATE,
                        Manifest.permission.NFC,
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.WAKE_LOCK,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.VIBRATE,
                        Manifest.permission.RECORD_AUDIO)
                .onAccepted { permissions ->
                    doAnimate()
                    //List of accepted permissions
                }
                .onDenied { permissions ->
                    //List of denied permissions
                }
                .onForeverDenied { permissions ->
                    //List of forever denied permissions
                }
                .ask()
    }

    private fun doAnimate() {

        Handler().postDelayed({
            startActivity(Intent(this , MainActivity::class.java))
            finish()
        }, 2500)


        val title = TextBuilder
                .create("Jin Pro")
                .setSize(16f)
                .setAlpha(0)
                .setColor(Color.BLACK)
                .setPosition(Align.SURFACE_CENTER).build()

        val zhinCo = TextBuilder
                .create("Jin Pro")
                .setSize(14f)
                .setAlpha(0)
                .setColor(Color.BLACK)
                .setPosition(Align.SURFACE_CENTER).build()

        val zhinWeb = TextBuilder
                .create(getString(R.string.WebSite_Jin724))
                .setSize(12f)
                .setAlpha(0)
                .setColor(Color.RED)
                .setPosition(Align.SURFACE_CENTER).build()

        titleSurface.play(Sequential(
                Slide.showFrom(Side.RIGHT, title, 500)))

        bottomSurface.play(Sequential(Delay.duration(500), Slide.showFrom(Side.RIGHT, zhinWeb, 200)))

    }

    private val REQUEST_CAMERA: Int = 1046


}
