package com.example.audioguide.fragment

import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.audioguide.R
import com.example.audioguide.databinding.FragmentMapBinding
import com.example.audioguide.model.Route
import com.example.audioguide.utils.LoaderImage
import com.example.audioguide.utils.ParserPoints
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.DrivingOptions
import com.yandex.mapkit.directions.driving.DrivingRoute
import com.yandex.mapkit.directions.driving.DrivingRouter
import com.yandex.mapkit.directions.driving.DrivingRouterType
import com.yandex.mapkit.directions.driving.DrivingSession
import com.yandex.mapkit.directions.driving.VehicleOptions
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.CompositeIcon
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.RotationType
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider

/**
 * Фрагмент гугл карт
 *
 */
class YandexMapFragment : Fragment(), UserLocationObjectListener, DrivingSession.DrivingRouteListener {

    private lateinit var currentPosition: Point
    private lateinit var destinationPosition: Point
    private var mapObjects: MapObjectCollection? = null
    private var drivingRouter: DrivingRouter? = null
    private var drivingSession: DrivingSession? = null

    private lateinit var binding: FragmentMapBinding
    private lateinit var route: Route
    private lateinit var loaderImage: LoaderImage
    private var level = 0

    private lateinit var mapView: MapView

    private lateinit var parserPoints: ParserPoints
    private lateinit var userLocationLayer: UserLocationLayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        loaderImage = LoaderImage()
        mapView = binding.mapview
        binding.routeName.text = route.nameRoute

        val mapKit = MapKitFactory.getInstance()
        userLocationLayer = mapKit.createUserLocationLayer(mapView.mapWindow)
        userLocationLayer.isVisible = true
        userLocationLayer.isHeadingEnabled = true

        userLocationLayer.setObjectListener(this)

        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter(DrivingRouterType.ONLINE)
        mapObjects = mapView.map.mapObjects.addCollection()

        submitRequest()

        return binding.root
    }



    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    companion object {
        @JvmStatic
        fun newInstance(route: Route, pos: Int) =
            YandexMapFragment().apply {
                this.route = route
                level = pos
            }
    }

    /**
     * Позиция точки в маршруте
     *
     */
    private fun destinationPosition(level: Int) : Point {
        if (level + 1 == route.pointsRoute.size){
            return Point()
        }
        parserPoints = ParserPoints()
        val points = parserPoints.parsePoints(route.pointsRoute[level])
        val point = Point(points[0], points[1])
        return point
    }

    /**
     * Построение маршрута
     *
     * @param start начало
     * @param end конец
     */
    private fun createPath(current: Point, destination: Point) {
        val center = Point(
            (current.latitude + destination.latitude) / 2,
            (current.longitude + destination.longitude) / 2
        )
    }

    /**
     * Установить отображение стрелки пользователя
     *
     * @param userLocationView
     */
    override fun onObjectAdded(userLocationView: UserLocationView) {
        userLocationLayer.setAnchor(
            PointF((mapView.width * 0.5).toFloat(), (mapView.height * 0.5).toFloat()),
            PointF((mapView.width * 0.5).toFloat(), (mapView.height * 0.83).toFloat())
        )

        userLocationView.arrow.setIcon(
            ImageProvider.fromResource(
                requireContext(), R.drawable.ic_play
            )
        )

        val pinIcon: CompositeIcon = userLocationView.pin.useCompositeIcon()

        pinIcon.setIcon(
            "icon",
            ImageProvider.fromResource(requireContext(), R.drawable.ic_pause),
            IconStyle().setAnchor(PointF(0f, 0f))
                .setRotationType(RotationType.ROTATE)
                .setZIndex(0f)
                .setScale(0.0005f)
        )

        pinIcon.setIcon(
            "pin",
            ImageProvider.fromResource(requireContext(), R.drawable.ic_play),
            IconStyle().setAnchor(PointF(0.5f, 0.5f))
                .setRotationType(RotationType.ROTATE)
                .setZIndex(1f)
                .setScale(0.0005f)
        )

        userLocationView.accuracyCircle.fillColor = Color.BLUE and -0x66000001
    }

    override fun onObjectRemoved(p0: UserLocationView) {

    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {
    }

    /**
     * Добавить линии маршрута
     *
     * @param p0
     */
    override fun onDrivingRoutes(p0: MutableList<DrivingRoute>) {
        for(path in p0){
            mapObjects!!.addPolyline(path.geometry)
        }
    }

    /**
     * Вызов ошибки
     *
     * @param p0
     */
    override fun onDrivingRoutesError(p0: Error) {
        Toast.makeText(requireContext(),"Ошибка",Toast.LENGTH_SHORT).show()
    }

    /**
     * Создание маршрута
     *
     */
    private fun submitRequest() {
        currentPosition = destinationPosition(level)
        destinationPosition = destinationPosition(level + 1)
        val drivingOptions = DrivingOptions()
        val vehicleOptions = VehicleOptions()
        val requestPoints: ArrayList<RequestPoint> = ArrayList()
        requestPoints.add(RequestPoint(currentPosition,RequestPointType.WAYPOINT,null,""))
        requestPoints.add(RequestPoint(destinationPosition,RequestPointType.WAYPOINT,null,""))
        drivingSession = drivingRouter!!.requestRoutes(requestPoints,drivingOptions,vehicleOptions,this)
    }
}