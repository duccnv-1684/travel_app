package vn.sunasterisk.travelapp.base

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

open class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val fragmentList: List<Fragment>,
    private val titles: List<String>
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int) = fragmentList.get(position)

    override fun getCount() = fragmentList.size

    override fun getPageTitle(position: Int) = titles.get(position)

    override fun saveState(): Parcelable? {
        return null
    }
}
