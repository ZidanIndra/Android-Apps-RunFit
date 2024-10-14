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

    private int totalLangkah = 0; // Total langkah yang diambil
    private int durasiMenit = 0; // Durasi dalam menit
    private int durasiDetik = 0; // Durasi dalam detik
    private float totalJarak = 0; // Total jarak dalam kilometer
    private boolean isRunning = false; // Status latihan
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long startTime; // Waktu mulai latihan

    private float previousZ = 0; // Nilai akselerasi Z sebelumnya
    private int stepThreshold = 15; // Ambang batas untuk mendeteksi langkah

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Pastikan ini sesuai dengan nama layout XML

        // Inisialisasi elemen UI
        totalLangkahTextView = findViewById(R.id.totalLangkahTextView);
        durasiLariTextView = findViewById(R.id.durasiLariTextView);
        totalJarakTextView = findViewById(R.id.totalJarakTextView);
        startButton = findViewById(R.id.startButton); // Ganti dengan ID tombol Anda

        // Inisialisasi sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Set onClickListener untuk tombol
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startExercise();
            }
        });
    }

    private void startExercise() {
        // Reset langkah dan durasi ketika latihan dimulai
        totalLangkah = 0;
        durasiMenit = 0;
        durasiDetik = 0; // Reset durasi detik
        totalJarak = 0; // Reset total jarak
        isRunning = true;
        startTime = System.currentTimeMillis(); // Simpan waktu mulai
        previousZ = 0; // Reset nilai sebelumnya

        updateUI(); // Perbarui tampilan awal

        // Mendaftar listener untuk sensor accelerometer
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Menghitung langkah berdasarkan perubahan akselerasi
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Menggunakan algoritma deteksi langkah
            if (isRunning) {
                // Deteksi langkah berdasarkan perubahan akselerasi Z
                if (Math.abs(z - previousZ) > stepThreshold) {
                    totalLangkah++;
                    totalJarak = (totalLangkah * 0.7f) / 1000; // Hitung total jarak dalam kilometer
                }
                previousZ = z; // Update nilai akselerasi Z sebelumnya
            }

            // Mengupdate UI setelah langkah dihitung
            updateUI();
        }
    }

    private void updateUI() {
        // Hitung durasi dalam menit dan detik
        if (isRunning) {
            long elapsedMillis = System.currentTimeMillis() - startTime;
            durasiMenit = (int) (elapsedMillis / 60000); // Hitung durasi dalam menit
            durasiDetik = (int) ((elapsedMillis / 1000) % 60); // Hitung durasi dalam detik
        }

        totalLangkahTextView.setText(String.valueOf(totalLangkah));
        durasiLariTextView.setText(String.format("%d:%02d", durasiMenit, durasiDetik)); // Format MM:SS
        totalJarakTextView.setText(String.format("%.2f", totalJarak)); // Total jarak dalam kilometer
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Tidak perlu diimplementasikan
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false; // Hentikan penghitungan durasi
        sensorManager.unregisterListener(this); // Hentikan listener saat aktivitas dihancurkan
    }
}