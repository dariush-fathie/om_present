package pro.ahoora.zhin.om.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import pro.ahoora.zhin.om.R
import pro.ahoora.zhin.om.ui.detail.fragments.ChartsFragment
import pro.ahoora.zhin.om.ui.detail.fragments.HistoryFragment
import pro.ahoora.zhin.om.ui.detail.fragments.InformationFragment
import pro.ahoora.zhin.om.ui.detail.fragments.TajvizFragment

class DetailPagerAdapter(fm: FragmentManager,val context: Context) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> InformationFragment()
            1 -> HistoryFragment()
            2-> TajvizFragment()
            else -> ChartsFragment()
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.PatientInformation)
            1 -> context.getString(R.string.PreviousRefers)
            2 -> context.getString(R.string.prescriptions)
            else -> context.getString(R.string.Charts)

        }
    }

}