package pro.ahoora.zhin.om.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import pro.ahoora.zhin.om.R
import pro.ahoora.zhin.om.adapters.SearchAdapter
import pro.ahoora.zhin.om.model.Patient
import pro.ahoora.zhin.om.ui.decoration.VerticalLinearLayoutMangerDecoration
import pro.ahoora.zhin.om.util.Converter
import pro.ahoora.zhin.om.viewModels.MainViewModel
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener, ViewTreeObserver.OnGlobalLayoutListener {

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initVMs()

        toolbar.title = "پرونده بیماران"
        val tv = toolbar.getChildAt(0) as TextView
        //tv.typeface = ResourcesCompat.getFont(this , R.font.ir_sans)

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

        drawerLayout.openDrawer(GravityCompat.START)
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
        when(item.itemId) {
            R.id.nav_view_patient -> {
                toolbar.title = "پرونده بیماران"
            }
            R.id.nav_view_handwrite -> {

            }

            R.id.nav_view_nfc -> {
                // start nfc activity
            }
            R.id.nav_view_qr -> {
                // start qr activity
            }

            R.id.nav_view_rec ->{

            }

            R.id.nav_view_draw -> {

            }

        }
        drawerLayout.closeDrawers()
        return true
    }



}
