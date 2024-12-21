package com.example.sensordisplayapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private RecyclerView sensorList;
    private LinearLayout sensorDetailContainer;
    private TextView sensorName, sensorData;
    private Sensor selectedSensor;
    private List<Sensor> sensorListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorList = findViewById(R.id.sensor_list);
        sensorDetailContainer = findViewById(R.id.sensor_detail_container);
        sensorName = findViewById(R.id.sensor_name);
        sensorData = findViewById(R.id.sensor_data);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorListData = sensorManager.getSensorList(Sensor.TYPE_ALL);
        SensorAdapter adapter = new SensorAdapter(sensorListData, sensor -> {
            selectedSensor = sensor;
            sensorDetailContainer.setVisibility(View.VISIBLE);
            sensorName.setText(sensor.getName());
            sensorData.setText("Waiting for data...");
            sensorManager.unregisterListener(this);
            sensorManager.registerListener(this, selectedSensor, SensorManager.SENSOR_DELAY_UI);
        });

        sensorList.setLayoutManager(new LinearLayoutManager(this));
        sensorList.setAdapter(adapter);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == selectedSensor) {
            StringBuilder data = new StringBuilder();
            for (int i = 0; i < event.values.length; i++) {
                data.append("Value[").append(i).append("]: ").append(event.values[i]).append("\n");
            }
            sensorData.setText(data.toString());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (selectedSensor != null) {
            sensorManager.registerListener(this, selectedSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }
}
