package pro.ahoora.zhin.om.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import pro.ahoora.zhin.om.R
import pro.ahoora.zhin.om.ui.qr.QrActivity
import pro.ahoora.zhin.om.ui.qr.fragments.CreateFragment
import pro.ahoora.zhin.om.ui.qr.fragments.ScanFragment

class PagerAdapter(fm: FragmentManager, val context: Context) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> CreateFragment()
            else -> ScanFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.Create)
            else -> context.getString(R.string.scan)
        }
    }


}
