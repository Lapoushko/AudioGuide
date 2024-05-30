package com.example.audioguide.activity

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.audioguide.component.FragmentPageAdapter
import com.example.audioguide.databinding.ActivityRouteBinding
import com.example.audioguide.model.Route
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.yandex.mapkit.MapKitFactory
import java.io.Serializable

/**
 * Активность текущего маршрута
 */
class RouteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRouteBinding
    private lateinit var viewPagerAdapter: FragmentPageAdapter
    private lateinit var route: Route
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("9302dc8f-56ed-442a-b87f-f6c65e38301b")
        MapKitFactory.initialize(this)
        binding = ActivityRouteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        route = getSerializable(this, Route::class.java)

        viewPagerAdapter = FragmentPageAdapter(supportFragmentManager, lifecycle, route)
        binding.tabLayout.addTab(
            binding.tabLayout.newTab().setText("Маршрут")
        )
        binding.tabLayout.addTab(
            binding.tabLayout.newTab().setText("Карта")
        )
        binding.viewPagerTab.adapter = viewPagerAdapter
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.viewPagerTab.currentItem = tab.position
                    if (tab.position == 1){
                        binding.viewPagerTab.isUserInputEnabled = false
                    }else{
                        binding.viewPagerTab.isUserInputEnabled = true
                    }
                }
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

        })
        binding.viewPagerTab.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })
    }

    /**
     * Передача данных из предыдущей активности
     */
    private fun <T : Serializable?> getSerializable(
        activity: Activity,
        clazz: Class<T>
    ): T {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activity.intent.getSerializableExtra("route", clazz)!!
        } else {
            activity.intent.getSerializableExtra("route") as T
        }
    }
}