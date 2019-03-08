package com.mtp.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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
import com.mtp.DAO.EventPublishedDao;
import com.mtp.Model.EventPublished;
import com.mtp.R;

public class MapFragmentActivity extends Fragment{

    MapView mMapView;
    private GoogleMap googleMap;
    private FloatingActionButton btAdd;
    private Boolean activatedAddMarker;
    private CoordinatorLayout coordinatorLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_map_fragment, container, false);

        activatedAddMarker = false;
        mMapView = rootView.findViewById(R.id.mapView);
        coordinatorLayout = rootView.findViewById(R.id.mapCoodinatorLayout);
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
//            createNewMarker();

            // custom dialog
            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.dialog_map_layout);
            dialog.setTitle("Title...");

            // set the custom dialog components - text, image and button
//            TextView text = (TextView) dialog.findViewById(R.id.tv_dialog_name);
//            text.setText("Android custom dialog example!");
//            ImageView image = (ImageView) dialog.findViewById(R.id.image);
//            image.setImageResource(R.drawable.ic_launcher);

            Button btCreateEvent = dialog.findViewById(R.id.bt_create_event_dialog);
                btCreateEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    dialog.dismiss();
                }
            });

            Button btCancel = dialog.findViewById(R.id.bt_cancel_dialog);
                btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
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
                        Toast.makeText(getContext(), ((EventPublished) marker.getTag()).getDetail(), Toast.LENGTH_SHORT).show();
                    }
                });

                for (EventPublished eventPublished : EventPublishedDao.getAll()) {
                    MarkerOptions markerOptions = new MarkerOptions().position(
                            new LatLng(eventPublished.getLatitude(), eventPublished.getLongitude()))
                            .title(eventPublished.getName())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bar));

                    Marker m = mMap.addMarker(markerOptions);
                    m.setTag(eventPublished);
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
            btAdd.setImageResource(android.R.drawable.ic_input_add);

            activatedAddMarker = false;
            googleMap.setOnMapClickListener(null);
        } else {
            btAdd.setImageResource(android.R.drawable.ic_delete);

            activatedAddMarker = true;
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {

                    EventPublished eventStatic = new EventPublished("new marker", latLng.longitude,
                            latLng.latitude, "Esto es un nuevo marker" );
                    MarkerOptions markerOptions = new MarkerOptions().position(
                            new LatLng(eventStatic.getLatitude(), eventStatic.getLongitude()))
                            .title(eventStatic.getName())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bar));

                    Marker marker = googleMap.addMarker(markerOptions);
                    marker.setTag(eventStatic);

                    Snackbar snackbar = Snackbar.make(coordinatorLayout, getString(R.string.map_snack_message_marker_added),
                            Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    View snackBarView = snackbar.getView();
                    CoordinatorLayout.LayoutParams params=(CoordinatorLayout.LayoutParams) snackBarView.getLayoutParams();
                    params.gravity = Gravity.TOP;
                    snackBarView.setLayoutParams(params);

                    int snackbarTextId = android.support.design.R.id.snackbar_text;
                    TextView textView = (TextView)snackBarView.findViewById(snackbarTextId);
                    textView.setTextColor(getResources().getColor(R.color.red_dark));

                    snackBarView.setBackgroundColor(getResources().getColor(R.color.colorWood));


//                    Snackbar snackbar = Snackbar
//                            .make(constraintLayout, "Message is deleted", Snackbar.LENGTH_LONG)
//                            .setAction("UNDO", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Snackbar snackbar1 = Snackbar.make(constraintLayout, "Marker Added", Snackbar.LENGTH_SHORT);
//                                    snackbar1.show();
//                                }
//                            });

                    snackbar.show();
                }
            });
        }
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