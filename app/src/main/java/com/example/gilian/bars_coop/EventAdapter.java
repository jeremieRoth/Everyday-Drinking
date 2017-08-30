package com.example.gilian.bars_coop;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gilian.bars_coop.Entity.Event;
import com.example.gilian.bars_coop.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JérémieRoth on 30/08/2017.
 */

public class EventAdapter extends ArrayAdapter<Event> {
    public List<Event> eventList;
    private Activity activity;
    private int layout;

    public EventAdapter(Activity activity, int layout, List<Event> eventList){
        super(activity, layout, eventList);
        this.eventList = eventList;
        this.activity = activity;
        this.layout = layout;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View view = convertView;
        if(convertView == null){
            LayoutInflater inflater = (this.activity).getLayoutInflater();
            view = inflater.inflate(this.layout, parent, false);
        }

        TextView eventTxt = (TextView) view.findViewById(R.id.body_event);
        eventTxt.setText(eventList.get(position).getName());

        return view;
    }
}
