package pro.ahoora.zhin.om.ui.detail

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_patient_detail.*
import pro.ahoora.zhin.om.R
import pro.ahoora.zhin.om.adapters.DetailPagerAdapter

class PatientDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_detail)

        setSupportActionBar(toolbar)

        val pagerAdapter = DetailPagerAdapter(supportFragmentManager)
        detailPager.adapter = pagerAdapter
        tbl_detail.setupWithViewPager(detailPager)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

}
