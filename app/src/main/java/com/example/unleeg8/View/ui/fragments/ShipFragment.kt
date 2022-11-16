package com.example.unleeg8.View.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.unleeg8.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class ShipFragment : Fragment(), OnMapReadyCallback {

    lateinit var googleMap: GoogleMap

    lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.mapas, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedIstanceState: Bundle?) {
        super.onViewCreated(view,savedIstanceState)
        val mapFragment = this.childFragmentManager.findFragmentById(R.id.mapGoogle) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Open Street Maps
        mapView= view.findViewById(R.id.mapOpenStreet)
        mapView.setTileSource(TileSourceFactory.MAPNIK)

        //Open Street Map Location

        val geoPoint= GeoPoint(5.0700275, -75.513817)
        val mapController = mapView.controller
        mapController.setZoom(16.0)
        mapController.setCenter(geoPoint)

        //Marcador

        val marker = Marker(mapView)
        marker.setPosition(geoPoint)
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.setTitle("UnLeeG8")
        mapView.overlays.add(marker)

    }


    override fun onMapReady(map: GoogleMap) {
        val colombia = LatLng(5.0700275, -75.513817)
        map?.let {
            map.addMarker(MarkerOptions().position(colombia))
        }
    }


}