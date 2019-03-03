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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mtp.DAO.StaticLocationDao;
import com.mtp.Model.StaticLocation;
import com.mtp.R;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MapFragmentActivity extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_map_fragment, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                for (StaticLocation staticLocation : StaticLocationDao.getAll()) {
                    MarkerOptions markerOptions = new MarkerOptions().position(
                            new LatLng(staticLocation.getLatitude(), staticLocation.getLongitude()))
                            .title(staticLocation.getName())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bar));
                    mMap.addMarker(markerOptions);
                }


                //Fer servir aquesta animacio en un futur
                // For zooming automatically to the location of the marker
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
//                        String locAddress = marker.getTitle();
//                        fillTextViews(locAddress);
//                        if (previousMarker != null) {
//                            previousMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                        }
//                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//                        previousMarker = marker;


                        googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(LayoutInflater.from(getActivity())));


                        Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_LONG).show();

                        return true;
                    }
                });

            }
        });


        return rootView;
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