package by.ssrlab.fishpits.fragments.bychosen.sub

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import by.ssrlab.fishpits.MainActivity
import by.ssrlab.fishpits.R
import by.ssrlab.fishpits.databinding.BottomFragmentMapPointBinding
import by.ssrlab.fishpits.utils.vm.main.MainVM
import by.ssrlab.fishpits.utils.vm.ui.sub.bychosen.ChosenUIVM
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MapPointFragment : BottomSheetDialogFragment(), OnMapReadyCallback {

    private lateinit var binding: BottomFragmentMapPointBinding
    private lateinit var map: GoogleMap
    private val activityVM: MainVM by activityViewModels()
    private val chosenUIVM: ChosenUIVM by activityViewModels()

    /** Common with ChosenFragment */

    override fun getTheme() = R.style.DescBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = BottomFragmentMapPointBinding.inflate(layoutInflater)

        (activity as MainActivity).turnOffBottomNav()

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_bottom) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        dialog?.let {
            val bottomSheet =
                it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                (activity as MainActivity),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }

        map.isMyLocationEnabled = true
        map.uiSettings.setAllGesturesEnabled(false)

        activityVM.points.observe(viewLifecycleOwner) { points ->
            map.clear()

            val selectedPoint = points.find {
                it.point.lat1 == chosenUIVM.getPointGeo().lat1 &&
                        it.point.lng1 == chosenUIVM.getPointGeo().lng1
            }

            selectedPoint?.let { point ->
                map.addMarker(
                    MarkerOptions()
                        .position(LatLng(point.point.lat1, point.point.lng1))
                        .icon(
                            bitmapDescriptorFromVector(
                                requireContext(),
                                R.drawable.ic_map_point_unactivated
                            )
                        )
                        .title(point.id.toString())
                )

                when (point.point.pointGeoType) {
                    "Polygon" -> {
                        val boundsBuilder = LatLngBounds.builder()
                        boundsBuilder.include(LatLng(point.point.lat1, point.point.lng1))
                        if (point.point.lat2 != 0.0 && point.point.lng2 != 0.0) {
                            boundsBuilder.include(LatLng(point.point.lat2, point.point.lng2))
                        }
                        if (point.point.lat3 != 0.0 && point.point.lng3 != 0.0) {
                            boundsBuilder.include(LatLng(point.point.lat3, point.point.lng3))
                        }
                        if (point.point.lat4 != 0.0 && point.point.lng4 != 0.0) {
                            boundsBuilder.include(LatLng(point.point.lat4, point.point.lng4))
                        }

                        val bounds = boundsBuilder.build()
                        val width = resources.displayMetrics.widthPixels
                        val height = resources.displayMetrics.heightPixels
                        val padding = (height * 0.05).toInt()

                        map.moveCamera(
                            CameraUpdateFactory.newLatLngBounds(
                                bounds,
                                width / 2,
                                height / 2,
                                padding
                            )
                        )
                        map.addPolyline(
                            PolylineOptions()
                                .add(
                                    LatLng(point.point.lat1, point.point.lng1),
                                    LatLng(point.point.lat2, point.point.lng2)
                                )
                                .color(R.color.marker_unactivated)
                                .width(10F)
                        )
                        if (!(point.point.lat3 == 0.0 && point.point.lng3 == 0.0
                                    && point.point.lat4 == 0.0 && point.point.lng4 == 0.0)
                        ) {
                            map.addPolyline(
                                PolylineOptions()
                                    .add(
                                        LatLng(point.point.lat3, point.point.lng3),
                                        LatLng(point.point.lat4, point.point.lng4)
                                    )
                                    .color(R.color.marker_unactivated)
                                    .width(10F)
                            )
                        }
                    }

                    "Point" -> {
                        map.addCircle(
                            CircleOptions()
                                .center(LatLng(point.point.lat1, point.point.lng1))
                                .fillColor(R.color.marker_unactivated)
                                .radius(60.0)
                                .strokeColor(R.color.marker_unactivated)
                        )
                        map.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(point.point.lat1, point.point.lng1),
                                13.5f
                            )
                        )
                    }

                    "Line" -> {
                        map.addPolyline(
                            PolylineOptions()
                                .add(
                                    LatLng(point.point.lat1, point.point.lng1),
                                    LatLng(point.point.lat2, point.point.lng2)
                                )
                                .color(R.color.marker_unactivated)
                                .width(10F)
                        )
                        map.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(point.point.lat1, point.point.lng1),
                                14f
                            )
                        )
                    }
                }
            }
        }
    }


    private fun bitmapDescriptorFromVector(
        context: Context,
        @DrawableRes vectorDrawableResourceId: Int
    ): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onDestroy() {
        super.onDestroy()

        (activity as MainActivity).turnOnBottomNav()
    }
}