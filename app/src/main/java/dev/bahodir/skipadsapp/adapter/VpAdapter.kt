package dev.bahodir.skipadsapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.bahodir.skipadsapp.fragment.vp.VpFragment
import dev.bahodir.skipadsapp.gnews.Article

class VpAdapter(var NUM: Int, var fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return NUM
    }

    override fun createFragment(position: Int): Fragment {
        return VpFragment()
    }
}