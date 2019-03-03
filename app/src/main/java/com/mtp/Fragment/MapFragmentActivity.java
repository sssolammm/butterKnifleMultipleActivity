package com.mtp.Fragment;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.mtp.DAO.StaticLocationDao;
//import com.mtp.Model.StaticLocation;
//import com.mtp.R;
//
//
//public class MapFragmentActivity extends Fragment implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View rootView = super.onCreateView(inflater, container, savedInstanceState);
////        getMapAsync(this);
//        return rootView;
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        for (StaticLocation staticLocation : StaticLocationDao.getAll()) {
//            MarkerOptions markerOptions = new MarkerOptions().position(
//                    new LatLng(staticLocation.getLatitude(), staticLocation.getLongitude()))
//                    .title(staticLocation.getName())
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bar));
//            mMap.addMarker(markerOptions);
//        }
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(
////                new LatLng(staticLocation.getLatitude(), staticLocation.getLongitude())));
//    }
//}

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mtp.Adapter.CustomInfoStaticEvent;
import com.mtp.DAO.EventStaticDao;
import com.mtp.Model.EventStatic;
import com.mtp.R;

public class MapFragmentActivity extends Fragment{

    MapView mMapView;
    private GoogleMap googleMap;
    private FloatingActionButton btAdd;
    private Boolean activatedAddMarker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_map_fragment, container, false);

        activatedAddMarker = false;
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        btAdd = rootView.findViewById(R.id.bt_add);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewMarker();
            }
        });

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;


                CustomInfoStaticEvent customInfoStaticEvent = new CustomInfoStaticEvent(getActivity());
                mMap.setInfoWindowAdapter(customInfoStaticEvent);


                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {

                        Toast.makeText(getContext(), ((EventStatic) marker.getTag()).getDetail(), Toast.LENGTH_SHORT).show();
                    }
                });

                for (EventStatic eventStatic : EventStaticDao.getAll()) {
                    MarkerOptions markerOptions = new MarkerOptions().position(
                            new LatLng(eventStatic.getLatitude(), eventStatic.getLongitude()))
                            .title(eventStatic.getName())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bar));
//                    mMap.addMarker(markerOptions);

//                    InfoWindowData info = new InfoWindowData();
//                    info.setHotel("Hotel : excellent hotels available");

                    Marker m = mMap.addMarker(markerOptions);
                    m.setTag(eventStatic);
//                    m.showInfoWindow();

                }


                //Fer servir aquesta animacio en un futur
                // For zooming automatically to the location of the marker
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        marker.showInfoWindow();
                        return true;
                    }
                });

            }
        });


        return rootView;
    }

    private void createNewMarker() {

        if (activatedAddMarker) {
            Resources res = getResources();
            btAdd.setBackgroundDrawable(res.getDrawable(android.R.drawable.ic_input_add));

            activatedAddMarker = false;
            googleMap.setOnMapClickListener(null);
        } else {
            Resources res = getResources();
            btAdd.setBackgroundDrawable(res.getDrawable(android.R.drawable.ic_input_delete));

            activatedAddMarker = true;
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    MarkerOptions marker = new MarkerOptions().position(
                            latLng)
                            .title("Hello Maps ");
                    marker.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    googleMap.addMarker(marker);
                }
            });

        }

//        btAdd.setBackgroundDrawable();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}