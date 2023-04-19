package by.ssrlab.fishpits.fragments.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import by.ssrlab.fishpits.R
import by.ssrlab.fishpits.databinding.FragmentMapBinding
import by.ssrlab.fishpits.fragments.map.sub.PointDescriptionFragment
import by.ssrlab.fishpits.utils.base.BaseFragment
import by.ssrlab.fishpits.utils.vm.ui.sub.map.MapUIVM
import by.ssrlab.fishpits.utils.vm.ui.sub.map.sub.MapPointVM
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MapFragment : BaseFragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding
    private lateinit var map: GoogleMap

    override val uiVM: MapUIVM by activityViewModels()
    private val pointVM: MapPointVM by activityViewModels() /** Shared with bottom sheet */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMapBinding.inflate(layoutInflater)

        activityMain.turnOnBottomNav()

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityVM.setToolbarTitle(resources.getString(R.string.map_fragment))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != 1
        ) {
            ActivityCompat.requestPermissions(
                activityMain,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }

        map.isMyLocationEnabled = true

        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(activityMain)
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            val pos: CameraPosition = map.cameraPosition
            val newPos =
                CameraPosition.Builder(pos).target(LatLng(it.latitude, it.longitude)).zoom(12f)
                    .tilt(0f).build()
            map.animateCamera(CameraUpdateFactory.newCameraPosition(newPos))
        }

        activityVM.points.observe(viewLifecycleOwner) {
            for (i in it) {
                if (i.languageId == activityMain.provideApplication().getLanguage()) {
                    map.addMarker(
                        MarkerOptions().position(
                            LatLng(i.point.latStart, i.point.lngStart)).icon(uiVM.bitmapDescriptorFromVector(requireContext(), R.drawable.ic_map_point_unactivated)).title(i.id.toString()))
                    if (i.point.latFinish != 0.0) {
                        map.addPolyline(PolylineOptions().add(LatLng(i.point.latStart, i.point.lngStart), LatLng(i.point.latFinish, i.point.lngFinish)).color(R.color.marker_unactivated).width(10F))
                    }
                }
            }
        }

        map.setOnMarkerClickListener { marker ->
            activityVM.points.value!!.find { it.id == (marker.title?.toInt() ?: 0) }?.let { pointVM.setPoint(it) }
            PointDescriptionFragment().show(childFragmentManager, "${marker.id}_pointDescription")

            true
        }
    }
}