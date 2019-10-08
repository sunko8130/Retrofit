package com.example.retrofit.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.retrofit.R;
import com.example.retrofit.model.StateResponse;

import java.util.ArrayList;
import java.util.List;

public class StateAdapter extends ArrayAdapter<StateResponse> {
    private List<StateResponse> stateList = new ArrayList<>();

    public StateAdapter(@NonNull Context context, int resource, @NonNull List<StateResponse> objects) {
        super(context, resource, objects);
        stateList = objects;
    }

    @Nullable
    @Override
    public StateResponse getItem(int position) {
        return stateList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return init(position);
    }

    private View init(int position) {
        StateResponse state = getItem(position);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.state_list, null);
        TextView textView = v.findViewById(R.id.spinnerText);
        textView.setText(state.getState());
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position);
    }

    private View initView(int position) {
        StateResponse state = getItem(position);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.state_list, null);
        TextView textView = v.findViewById(R.id.spinnerText);
        textView.setText(state.getState());
        if (position % 2 == 1) {
            // Set those items text color
            textView.setTextColor(Color.RED);
            // Set the spinner items background color
            textView.setBackgroundColor(Color.parseColor("#beffbd"));

        } else {
            // Set alternate items text color
            textView.setTextColor(Color.BLUE);
            // Set the spinner alternate items background color
            textView.setBackgroundColor(Color.parseColor("#FF8AAB89"));
        }
        return v;
    }

}
