package com.psik.uas_runfit_zidanindranugraha_psik22a;

import androidx.appcompat.app.AppCompatActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView totalLangkahTextView;
    private TextView durasiLariTextView;
    private TextView totalJarakTextView;
    private Button startButton;

    private int totalLangkah = 0;
    private int durasiMenit = 0;
    private int durasiDetik = 0;
    private float totalJarak = 0;
    private boolean isRunning = false;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long startTime;

    private float previousZ = 0;
    private int stepThreshold = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi UI elements
        totalLangkahTextView = findViewById(R.id.totalLangkahTextView);
        durasiLariTextView = findViewById(R.id.durasiLariTextView);
        totalJarakTextView = findViewById(R.id.totalJarakTextView);
        startButton = findViewById(R.id.startButton);

        // Inisialisasi sensor accelerometer
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // btn onClickListener
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startExercise();
            }
        });
    }

    private void startExercise() {
        totalLangkah = 0;
        durasiMenit = 0;
        durasiDetik = 0;
        totalJarak = 0;
        isRunning = true;
        startTime = System.currentTimeMillis();
        previousZ = 0;

        updateUI();

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            if (isRunning) {
                // Mengkalkulasi perubahan akselerasi
                float magnitude = (float) Math.sqrt(x * x + y * y + z * z);

                // kalkulasi perubahan signifikan
                if (Math.abs(magnitude - previousZ) > stepThreshold) {
                    totalLangkah++;
                    totalJarak = (totalLangkah * 0.7f) / 1000;
                }
                previousZ = magnitude;
            }

            updateUI();
        }
    }

    private void updateUI() {
        if (isRunning) {
            long elapsedMillis = System.currentTimeMillis() - startTime;
            durasiMenit = (int) (elapsedMillis / 60000);
            durasiDetik = (int) ((elapsedMillis / 1000) % 60);
        }

        totalLangkahTextView.setText(String.valueOf(totalLangkah));
        durasiLariTextView.setText(String.format("%d:%02d", durasiMenit, durasiDetik));
        totalJarakTextView.setText(String.format("%.2f", totalJarak));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not implemented
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
        sensorManager.unregisterListener(this);
    }
}
