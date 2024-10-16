package com.psik.uas_runfit_zidanindranugraha_psik22a;

import androidx.appcompat.app.AppCompatActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView totalLangkahTextView;
    private TextView durasiLariTextView;
    private TextView totalJarakTextView;
    private Button startButton;
    private Button pauseButton;
    private Button stopButton;

    private int totalLangkah = 0;
    private int durasiMenit = 0;
    private int durasiDetik = 0;
    private float totalJarak = 0;
    private boolean isRunning = false;
    private boolean hasStarted = false;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long startTime;

    private float previousMagnitude = 0;
    private int stepThreshold = 15;

    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalLangkahTextView = findViewById(R.id.totalLangkahTextView);
        durasiLariTextView = findViewById(R.id.durasiLariTextView);
        totalJarakTextView = findViewById(R.id.totalJarakTextView);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        stopButton = findViewById(R.id.stopButton);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasStarted) {
                    Toast.makeText(MainActivity.this, "Latihan sudah dimulai", Toast.LENGTH_SHORT).show();
                } else {
                    vibrateDevice();
                    Toast.makeText(MainActivity.this, "Latihan dimulai", Toast.LENGTH_SHORT).show();
                    startExercise();
                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasStarted) {
                    Toast.makeText(MainActivity.this, "Mulai latihan terlebih dahulu", Toast.LENGTH_SHORT).show();
                    return;
                }
                vibrateDevice();
                if (isRunning) {
                    Toast.makeText(MainActivity.this, "Latihan dijeda", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Latihan dilanjutkan", Toast.LENGTH_SHORT).show();
                }
                pauseExercise();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasStarted) {
                    Toast.makeText(MainActivity.this, "Mulai latihan terlebih dahulu", Toast.LENGTH_SHORT).show();
                    return;
                }
                vibrateDevice();
                Toast.makeText(MainActivity.this, "Latihan dihentikan", Toast.LENGTH_SHORT).show();
                stopExercise();
            }
        });
    }

    private void startExercise() {
        totalLangkah = 0;
        durasiMenit = 0;
        durasiDetik = 0;
        totalJarak = 0;
        isRunning = true;
        hasStarted = true;
        startTime = System.currentTimeMillis();
        previousMagnitude = 0;

        updateUI();

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void pauseExercise() {
        if (isRunning) {
            isRunning = false;
            sensorManager.unregisterListener(this);
        } else {
            isRunning = true;
            startTime = System.currentTimeMillis() - ((durasiMenit * 60000) + (durasiDetik * 1000));
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private void stopExercise() {
        isRunning = false;
        sensorManager.unregisterListener(this);
        totalLangkah = 0;
        durasiMenit = 0;
        durasiDetik = 0;
        totalJarak = 0;
        hasStarted = false;
        updateUI();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            if (isRunning) {
                float magnitude = (float) Math.sqrt(x * x + y * y + z * z);
                if (Math.abs(magnitude - previousMagnitude) > stepThreshold) {
                    totalLangkah++;
                    totalJarak = (totalLangkah * 0.7f) / 1000;
                }
                previousMagnitude = magnitude;
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

    private void vibrateDevice() {
        if (vibrator != null && vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(100);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
        sensorManager.unregisterListener(this);
    }
}
