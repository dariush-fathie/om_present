package pro.ahoora.zhin.om.ui.qr

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_qr.*
import org.greenrobot.eventbus.EventBus
import pro.ahoora.zhin.om.R
import pro.ahoora.zhin.om.adapters.PagerAdapter
import pro.ahoora.zhin.om.event.VisibilityQrEvent

class QrActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private val rwRequest = 1080
    private val REQUEST_CAMERA = 1081
    private val cameraPermissions = arrayOf(Manifest.permission.CAMERA)

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        if (position == 0) {
            EventBus.getDefault().post(VisibilityQrEvent(1))
        } else if (position == 1) {

            if (checkStoragePermissions()) {
                EventBus.getDefault().post(VisibilityQrEvent(0))
            }
        }
    }

    private fun checkStoragePermissions(): Boolean {
        val camerap = ActivityCompat.checkSelfPermission(this@QrActivity, Manifest.permission.CAMERA)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (camerap == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                ActivityCompat.requestPermissions(
                        this@QrActivity,
                        cameraPermissions,
                        REQUEST_CAMERA
                )
                false
            }
        } else {
            // API < 23
            true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        setSupportActionBar(toolbar)

        vp_mainContainer.adapter = PagerAdapter(supportFragmentManager)

        vp_mainContainer.addOnPageChangeListener(this)
        vp_mainContainer.offscreenPageLimit = 2
        tbl_main.setupWithViewPager(vp_mainContainer)

        tbl_main.getTabAt(0)
        tbl_main.getTabAt(1)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == rwRequest) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                EventBus.getDefault().post(VisibilityQrEvent(2))
            } else {
                Toast.makeText(this@QrActivity, "اجازه دسترسی به حافظه داده نشد", Toast.LENGTH_LONG).show()
            }
        } else if (requestCode == REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                EventBus.getDefault().post(VisibilityQrEvent(0))
            } else {
                Toast.makeText(this@QrActivity, "اجازه دسترسی به دوربین داده نشد", Toast.LENGTH_LONG).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}
