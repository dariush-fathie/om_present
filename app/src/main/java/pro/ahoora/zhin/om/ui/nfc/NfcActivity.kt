package pro.ahoora.zhin.om.ui.nfc

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_nfc.*
import pro.ahoora.zhin.om.R
import pro.ahoora.zhin.om.ui.nfc.fragment.NFCReadFragment
import pro.ahoora.zhin.om.ui.nfc.fragment.NFCWriteFragment

class NfcActivity : AppCompatActivity() {


    private var mNfcWriteFragment: NFCWriteFragment? = null
    private var mNfcReadFragment: NFCReadFragment? = null

    //private var isDialogDisplayed = false
    private var isWrite = false

    private var mNfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc)

        setSupportActionBar(toolbar)

        initViews()
        initNFC()

        showReadFragment()
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

    private fun initViews() {
        btn_write.setOnClickListener { showWriteFragment() }
        btn_read.setOnClickListener { showReadFragment() }
    }

    private fun initNFC() {
        val manager = getSystemService(Context.NFC_SERVICE) as NfcManager
        mNfcAdapter = manager.defaultAdapter
        //So the service is not null
        Log.i(TAG, "NFC  Manager working")
    }

    private fun showWriteFragment() {
        isWrite = true
        mNfcWriteFragment = supportFragmentManager.findFragmentByTag(NFCWriteFragment.TAG) as NFCWriteFragment?
        if (mNfcWriteFragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainer, mNfcWriteFragment!!,
                            NFCWriteFragment.TAG).commit()
        } else {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainer, NFCWriteFragment(),
                            NFCWriteFragment.TAG).commit()
        }
    }

    private fun showReadFragment() {
        isWrite = false
        mNfcReadFragment = supportFragmentManager.findFragmentByTag(NFCReadFragment.TAG) as NFCReadFragment?
        if (mNfcReadFragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainer, mNfcReadFragment!!,
                            NFCReadFragment.TAG).commit()
        } else {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainer, NFCReadFragment(),
                            NFCReadFragment.TAG).commit()
        }
    }


    override fun onResume() {
        super.onResume()
        val tagDetected = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        val ndefDetected = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        val techDetected = IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
        val nfcIntentFilter = arrayOf(techDetected, tagDetected, ndefDetected)

        val pendingIntent = PendingIntent.getActivity(
                this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
        if (mNfcAdapter != null)
            mNfcAdapter!!.enableForegroundDispatch(this, pendingIntent, nfcIntentFilter, null)
    }

    override fun onPause() {
        super.onPause()
        if (mNfcAdapter != null)
            mNfcAdapter!!.disableForegroundDispatch(this)
    }

    @SuppressLint("MissingSuperCall")
    override fun onNewIntent(intent: Intent) {
        Log.e("tech", intent.toString())
        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        if (tag != null) {
            Toast.makeText(this, getString(R.string.message_tag_detected), Toast.LENGTH_SHORT).show()
            val ndef = Ndef.get(tag)
            if (ndef != null) {
                Log.e("NDEF", ndef.toString())
                Log.e("NDEF TAG", "isWrite = $isWrite")
                if (isWrite) {
                    mNfcWriteFragment = supportFragmentManager.findFragmentByTag(NFCWriteFragment.TAG) as NFCWriteFragment?
                    mNfcWriteFragment?.onNfcDetected(ndef)
                } else {
                    mNfcReadFragment = supportFragmentManager.findFragmentByTag(NFCReadFragment.TAG) as NFCReadFragment?
                    mNfcReadFragment?.onNfcDetected(ndef)
                }
            } else {
                Toast.makeText(this, getString(R.string.message_tag_notReadable), Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        val TAG = NfcActivity::class.java.simpleName
    }
}

