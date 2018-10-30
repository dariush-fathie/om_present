package pro.ahoora.zhin.om.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import pro.ahoora.zhin.om.ui.detail.fragments.ChartsFragment
import pro.ahoora.zhin.om.ui.detail.fragments.HistoryFragment
import pro.ahoora.zhin.om.ui.detail.fragments.InformationFragment
import pro.ahoora.zhin.om.ui.detail.fragments.TajvizFragment

class DetailPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> InformationFragment()
            0 -> HistoryFragment()
            0 -> TajvizFragment()
            else -> ChartsFragment()
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "اطلاعات بیمار"
            1 -> "مراجعات قبلی"
            2 -> "تجویزات"
            else -> "نمودارها"
        }
    }

}