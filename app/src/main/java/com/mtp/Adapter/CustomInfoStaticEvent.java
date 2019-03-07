package com.mtp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.mtp.Model.EventPublished;
import com.mtp.R;

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

        EventPublished eventPublished = (EventPublished) marker.getTag();
        tv_name.setText(eventPublished.getName());
        tv_detail.setText(eventPublished.getDetail());


        return view;
    }
}