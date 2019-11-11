package pro.ahoora.zhin.om.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder
import cafe.adriel.androidaudiorecorder.model.AudioChannel
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate
import cafe.adriel.androidaudiorecorder.model.AudioSource
import com.franmontiel.localechanger.LocaleChanger
import com.franmontiel.localechanger.utils.ActivityRecreationHelper
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import pro.ahoora.zhin.om.R
import pro.ahoora.zhin.om.adapters.SearchAdapter
import pro.ahoora.zhin.om.base.BaseActivity
import pro.ahoora.zhin.om.model.Patient
import pro.ahoora.zhin.om.ui.decoration.VerticalLinearLayoutMangerDecoration
import pro.ahoora.zhin.om.ui.draw.DrawActivity
import pro.ahoora.zhin.om.ui.nfc.NfcActivity
import pro.ahoora.zhin.om.ui.qr.QrActivity
import pro.ahoora.zhin.om.util.Converter
import pro.ahoora.zhin.om.util.LocaleHelper
import pro.ahoora.zhin.om.util.SharedPrefs
import pro.ahoora.zhin.om.viewModels.MainViewModel
import java.util.*


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener, ViewTreeObserver.OnGlobalLayoutListener {

    var width = 0


    override fun onStart() {
        super.onStart()
        shimmerLayout.visibility = View.VISIBLE
        shimmerLayout.startShimmer()

    }

    override fun onStop() {
        super.onStop()
        shimmerLayout.stopShimmer()
    }


    override fun attachBaseContext(newBase: Context) {
        val x = LocaleChanger.configureBaseContext(newBase)
        super.attachBaseContext(x)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initVMs()

        toolbar.title = getString(R.string.PatientReception)

        width = Converter.getScreenWidthPx(this) - 300
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)

        nav_view.setNavigationItemSelectedListener(this)

        drawerLayout.addDrawerListener(this)
        //drawerLayout.setScrimColor(ContextCompat.getColor(this, R.color.transparent))
        //drawerLayout.drawerElevation = 0f

        val toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        cloud.setOnClickListener {
            getDataFromCloudDB()
        }

        local.setOnClickListener {
            getDataFromLocalDB()
        }

        et_search.setOnEditorActionListener { _, _, _ ->
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            return@setOnEditorActionListener true
        }

        // drawerLayout.openDrawer(GravityCompat.START)
    }

    private lateinit var patientViewModel: MainViewModel

    private fun initVMs() {
        patientViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        patientViewModel.patinet.observe(this, Observer {
            stopShimmer()
            setAdapter(it)
        })
        patientViewModel.patientProgressVisibility.observe(this, Observer {
            pb_1.visibility = it
        })
    }

    private fun getDataFromLocalDB() {
        patientViewModel.getPatient(1)
    }

    private fun getDataFromCloudDB() {
        // todo
    }

    private fun stopShimmer() {
        shimmerLayout.stopShimmer()
        shimmerLayout.visibility = View.GONE
    }

    lateinit var searchAdapter: SearchAdapter
    private fun setAdapter(items: List<Patient>) {
        searchAdapter = SearchAdapter(items, this)
        rv_search.layoutManager = LinearLayoutManager(this)
        rv_search.adapter = searchAdapter
        while (rv_search.itemDecorationCount > 0) {
            rv_search.removeItemDecorationAt(0)
        }
        rv_search.addItemDecoration(VerticalLinearLayoutMangerDecoration(this, 16, 8, 16, 8))
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item?.itemId
        //noinspection SimplifiableIfStatement
        if (id == R.id.changeIp) {
            showIpDialog()
            return true
        }

        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showIpDialog() {
        val alert = AlertDialog.Builder(this)
        alert.setMessage(getString(R.string.EnterIp))
        val view = LayoutInflater.from(this).inflate(R.layout.ip_edit_text, null)
        val et: AppCompatEditText = view.findViewById(R.id.et_ip)
        alert.setView(view)
        alert.setPositiveButton(getString(R.string.Action)) { dialog, which ->
            patientViewModel.ip = et.text.toString()
            dialog.dismiss()
        }.setNegativeButton(getString(R.string.Return)) { dialog, which ->
            dialog.dismiss()
        }.show()
    }

    override fun onGlobalLayout() {
        width = drawerLayout.measuredWidth
        drawerLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
    }

    override fun onDrawerStateChanged(newState: Int) {
    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        //cl_container.translationX = slideOffset * width
    }

    override fun onDrawerClosed(drawerView: View) {
    }

    override fun onDrawerOpened(drawerView: View) {
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_view_patient -> {
                toolbar.title = getString(R.string.PatientReception)
            }
            R.id.nav_view_handwrite -> {
                startActivity(Intent(this@MainActivity, HWActivity::class.java))
            }

            R.id.nav_view_nfc -> {
                // start nfc activity
                if (checkHasNfcAdapter()) {
                    startActivity(Intent(this@MainActivity, NfcActivity::class.java))
                } else {
                    Log.e(javaClass.simpleName, "sorry. this device does'nt support NFC")
                }
            }
            R.id.nav_view_qr -> {
                // start qr activity
                startActivity(Intent(this@MainActivity, QrActivity::class.java))
            }

            R.id.nav_view_rec -> {
                val filePath = "${Environment.getExternalStorageDirectory()}/recorded_audio.wav"
                val color = ResourcesCompat.getColor(resources, R.color.green, null)
                val requestCode = 0
                AndroidAudioRecorder.with(this)
                        // Required
                        .setFilePath(filePath)
                        .setColor(color)
                        .setRequestCode(requestCode)

                        // Optional
                        .setSource(AudioSource.MIC)
                        .setChannel(AudioChannel.STEREO)
                        .setSampleRate(AudioSampleRate.HZ_48000)
                        .setAutoStart(true)
                        .setKeepDisplayOn(true)
                        // Start recording
                        .record()
            }

            R.id.nav_view_draw -> {
                startActivity(Intent(this@MainActivity, DrawActivity::class.java))
            }

            R.id.nav_view_lang -> {

                var lang = "en"
                var country = "US"

                if (SharedPrefs(this).getLocaleSelectedLanguage() == "en") {
                    lang = "fa"
                    country = "IR"
                }
                SharedPrefs(this).setLocaleSelectedLanguage(lang)


                val locale = Locale(lang, country)
                LocaleChanger.setLocale(locale)
                ActivityRecreationHelper.recreate(this, true)
            }

        }
        drawerLayout.closeDrawers()
        return true
    }


    private fun checkHasNfcAdapter(): Boolean = if (packageManager.hasSystemFeature(PackageManager.FEATURE_NFC)) {
        Log.i("nfc", "Has NFC functionality")
        true
    } else {
        Log.e("nfc", "Has No NFC functionality")
        false
    }


}
