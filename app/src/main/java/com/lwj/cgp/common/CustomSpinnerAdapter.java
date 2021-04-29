package com.lwj.cgp.common;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lwj.cgp.R;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    LayoutInflater inflater;
    List<String> spinnerItems;

    public CustomSpinnerAdapter(Context applicationContext, int resource, List<String> spinnerItems) {
        super(applicationContext, resource, spinnerItems);
        this.spinnerItems = spinnerItems;
        inflater = (LayoutInflater.from(applicationContext));
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.spinner_item, null);
        TextView type = (TextView) view.findViewById(R.id.spinner_item_text);
        type.setText(spinnerItems.get(i));
        return view;
    }
}