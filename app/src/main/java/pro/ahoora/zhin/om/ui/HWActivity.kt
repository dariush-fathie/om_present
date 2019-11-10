package pro.ahoora.zhin.om.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_hw.*
import pro.ahoora.zhin.om.R
import pro.ahoora.zhin.om.base.BaseActivity

class HWActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hw)

        setSupportActionBar(toolbar)

        btn_chooseKeyboard.setOnClickListener {
            val imeManager = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imeManager.showInputMethodPicker()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}
