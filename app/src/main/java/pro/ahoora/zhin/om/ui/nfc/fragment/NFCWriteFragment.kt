package pro.ahoora.zhin.om.ui.nfc.fragment


import android.content.Context
import android.nfc.FormatException
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.tech.Ndef
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_write.*
import pro.ahoora.zhin.om.R
import java.io.IOException
import java.nio.charset.Charset

class NFCWriteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_write, container, false)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        et_message.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val im = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                im.hideSoftInputFromWindow(et_message.windowToken, 0)
            }
            return@setOnEditorActionListener true
        }
    }

    fun onNfcDetected(ndef: Ndef) {
        progress!!.visibility = View.VISIBLE
        val text = et_message.text?.toString()
        if (text == null) {
            Toast.makeText(activity, "پیام نباید خالی باشد", Toast.LENGTH_LONG).show()
        } else {
            writeToNfc(ndef, text)
        }
    }

    private fun writeToNfc(ndef: Ndef, message: String) {
        tv_message!!.text = getString(R.string.message_write_progress)
        try {
            ndef.connect()
            val mimeRecord = NdefRecord.createMime("text/plain", message.toByteArray(Charset.forName("UTF-8")))
            ndef.writeNdefMessage(NdefMessage(mimeRecord))
            ndef.close()
            tv_message!!.text = getString(R.string.message_write_success)
        } catch (e: IOException) {
            e.printStackTrace()
            tv_message!!.text = getString(R.string.message_write_error)
        } catch (e: FormatException) {
            e.printStackTrace()
            tv_message!!.text = getString(R.string.message_write_error)
        } finally {
            progress!!.visibility = View.GONE
        }

    }

    companion object {

        val TAG = NFCWriteFragment::class.java.simpleName
    }
}
