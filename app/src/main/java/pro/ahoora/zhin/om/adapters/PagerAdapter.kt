package pro.ahoora.zhin.om.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import pro.ahoora.zhin.om.ui.fragment.CreateFragment
import pro.ahoora.zhin.om.ui.fragment.ScanFragment

class PagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
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
            0 -> "ایجاد کردن"
            else -> "اسکن کردن"
        }
    }


}
