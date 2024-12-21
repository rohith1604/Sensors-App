package com.example.sensordisplayapp;

import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.SensorViewHolder> {
    private final List<Sensor> sensors;
    private final OnSensorClickListener listener;

    public interface OnSensorClickListener {
        void onSensorClick(Sensor sensor);
    }

    public SensorAdapter(List<Sensor> sensors, OnSensorClickListener listener) {
        this.sensors = sensors;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SensorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new SensorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorViewHolder holder, int position) {
        Sensor sensor = sensors.get(position);
        holder.sensorName.setText(sensor.getName());
        holder.itemView.setOnClickListener(v -> listener.onSensorClick(sensor));
    }

    @Override
    public int getItemCount() {
        return sensors.size();
    }

    static class SensorViewHolder extends RecyclerView.ViewHolder {
        final TextView sensorName;

        SensorViewHolder(@NonNull View itemView) {
            super(itemView);
            sensorName = itemView.findViewById(android.R.id.text1);
        }
    }
}
