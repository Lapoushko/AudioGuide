package com.example.audioguide.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.audioguide.R
import com.example.audioguide.databinding.FragmentMapBinding
import com.example.audioguide.model.Route
import com.example.audioguide.utils.LoaderImage
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GoogleMapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var binding: FragmentMapBinding
    private lateinit var route: Route
    private lateinit var loaderImage: LoaderImage
    private var level = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)

        loaderImage = LoaderImage()
        val mapFragment = childFragmentManager.findFragmentById(R.id.googlemap) as SupportMapFragment
        mapFragment.getMapAsync(this, )

        setupTargetImage()

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(route: Route, pos: Int) =
            GoogleMapFragment().apply {
                this.route = route
                level = pos
            }
    }

    private fun setupTargetImage(){
        loaderImage.loadImage(route.listPathsImage[level + 1], binding.imageTarget)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val point = LatLng(56.78434825667237, 60.543613102223105)
        map.addMarker(MarkerOptions().position(point).title("Мой дом"))
        map.isBuildingsEnabled = true
        map.isIndoorEnabled = true
    }
}