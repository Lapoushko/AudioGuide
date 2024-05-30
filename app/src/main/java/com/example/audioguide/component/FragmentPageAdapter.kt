package com.example.audioguide.component

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.audioguide.fragment.YandexMapFragment
import com.example.audioguide.fragment.RouteFragment
import com.example.audioguide.model.Route

/**
 * Адаптер для перелистывания фрагментов
 */
class FragmentPageAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val route: Route
) : FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0){
            RouteFragment.newInstance(route, 0)
        }
        else{
            YandexMapFragment.newInstance(route, 0)
        }
    }
}