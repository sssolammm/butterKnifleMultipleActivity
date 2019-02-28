package com.mtp.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mtp.DAO.StaticLocationDao;
import com.mtp.Model.StaticLocation;


public class MapFragmentActivity extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        getMapAsync(this);
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (StaticLocation staticLocation : StaticLocationDao.getAll()) {
            mMap.addMarker(new MarkerOptions().position(
                    new LatLng(staticLocation.getLatitude(), staticLocation.getLongitude()))
                    .title(staticLocation.getName()));
        }
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(
//                new LatLng(staticLocation.getLatitude(), staticLocation.getLongitude())));
    }
}