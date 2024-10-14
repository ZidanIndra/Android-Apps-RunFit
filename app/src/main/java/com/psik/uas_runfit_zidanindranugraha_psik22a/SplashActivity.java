package com.psik.uas_runfit_zidanindranugraha_psik22a;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Menampilkan splash screen selama 2 detik
        new Handler().postDelayed(() -> {
            // Setelah 2 detik, buka MainActivity
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Mengakhiri splash screen agar tidak kembali
        }, 2000); // Ubah 2000 sesuai dengan waktu yang diinginkan
    }
}