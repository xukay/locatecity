package com.psalata.ready4s.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Paweł on 18.06.2016.
 */
public class CustomArrayAdapter extends ArrayAdapter {
    public CustomArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        if (position % 2 == 1) {
            view.setBackgroundColor(Color.GRAY);
        } else {
            view.setBackgroundColor(Color.LTGRAY);
        }
        return view;
    }
}
