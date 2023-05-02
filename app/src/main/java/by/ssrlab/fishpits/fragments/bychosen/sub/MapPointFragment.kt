package by.ssrlab.fishpits.fragments.bychosen.sub

import android.content.Context
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
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MapPointFragment : BottomSheetDialogFragment(), OnMapReadyCallback {

    private lateinit var binding: BottomFragmentMapPointBinding
    private lateinit var map: GoogleMap
    private val activityVM: MainVM by activityViewModels()
    private val chosenUIVM: ChosenUIVM by activityViewModels() /** Common with ChosenFragment */

    override fun getTheme() = R.style.DescBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = BottomFragmentMapPointBinding.inflate(layoutInflater)

        (activity as MainActivity).turnOffBottomNav()

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_bottom) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        dialog?.let {

            val bottomSheet = it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != 1) {
            ActivityCompat.requestPermissions((activity as MainActivity), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }

        map.isMyLocationEnabled = true
        map.uiSettings.setAllGesturesEnabled(false)

        val pos: CameraPosition = map.cameraPosition
        val newPos = CameraPosition.Builder(pos).target(LatLng(chosenUIVM.getPointGeo().latStart, chosenUIVM.getPointGeo().lngStart)).zoom(14f).tilt(0f).build()
        map.moveCamera(CameraUpdateFactory.newCameraPosition(newPos))

        activityVM.points.observe(viewLifecycleOwner) {
            for (i in it) {
                if (i.languageId == (activity as MainActivity).provideApplication().getLanguage()) {
                    map.addMarker(MarkerOptions().position(LatLng(i.point.latStart, i.point.lngStart)).icon(bitmapDescriptorFromVector(requireContext(), R.drawable.ic_map_point_unactivated)).title(i.id.toString()))
                    if (i.point.pointGeoType == "Line") {
                        map.addPolyline(PolylineOptions().add(LatLng(i.point.latStart, i.point.lngStart),LatLng(i.point.latFinish, i.point.lngFinish)).color(R.color.marker_unactivated).width(10F))
                    } else if (i.point.pointGeoType == "Point"){
                        map.addCircle(CircleOptions().center(LatLng(i.point.latStart, i.point.lngStart)).fillColor(R.color.marker_unactivated).radius(60.0).strokeColor(R.color.marker_unactivated))
                    }
                }
            }
        }
    }

    private fun bitmapDescriptorFromVector(context: Context, @DrawableRes vectorDrawableResourceId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)

        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onDestroy() {
        super.onDestroy()

        (activity as MainActivity).turnOnBottomNav()
    }
}