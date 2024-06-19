package app.test.xyz.adapter

import SettingsFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import app.test.xyz.fragment.CampaingFragment
import app.test.xyz.fragment.HomeFragment
import app.test.xyz.fragment.JoinFragment

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val fragmentList = listOf(
        HomeFragment(),
        JoinFragment(),
        CampaingFragment(), // Placeholder for CampaignFragment
        SettingsFragment()  // Placeholder for SettingsFragment
    )

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Home"
            1 -> "Join"
            2 -> "Campaign"
            3 -> "Settings"
            else -> null
        }
    }
}
