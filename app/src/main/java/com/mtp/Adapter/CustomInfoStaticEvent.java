package com.mtp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.mtp.MainActivity;
import com.mtp.Model.EventStatic;
import com.mtp.R;

import butterknife.BindView;

public class CustomInfoStaticEvent implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoStaticEvent(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.map_event_static_infowindow, null);


        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_detail = view.findViewById(R.id.tv_detail);

//        name_tv.setText(marker.getTitle());
//        details_tv.setText(marker.getSnippet());

        EventStatic eventStatic = (EventStatic) marker.getTag();
        tv_name.setText(eventStatic.getName());
        tv_detail.setText(eventStatic.getDetail());


        return view;
    }
}