package pro.ahoora.zhin.om.ui.splash

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash.*
import pro.ahoora.zhin.om.R
import pro.ahoora.zhin.om.ui.MainActivity
import su.levenetc.android.textsurface.TextBuilder
import su.levenetc.android.textsurface.animations.*
import su.levenetc.android.textsurface.contants.Align
import su.levenetc.android.textsurface.contants.Pivot
import su.levenetc.android.textsurface.contants.Side


class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2500)


        val title = TextBuilder
                .create("Zhin Pro")
                .setSize(16f)
                .setAlpha(0)
                .setColor(Color.BLACK)
                .setPosition(Align.SURFACE_CENTER).build()

        val zhinCo = TextBuilder
                .create("Zhin Pro")
                .setSize(14f)
                .setAlpha(0)
                .setColor(Color.BLACK)
                .setPosition(Align.SURFACE_CENTER).build()

        val zhinWeb = TextBuilder
                .create("www.MyJin.ir")
                .setSize(12f)
                .setAlpha(0)
                .setColor(Color.RED)
                .setPosition(Align.SURFACE_CENTER).build()

        titleSurface.play(Sequential(
                Slide.showFrom(Side.RIGHT, title, 500)))

        bottomSurface.play(Sequential(Delay.duration(500),Slide.showFrom(Side.RIGHT , zhinWeb , 200)))

    }


}
