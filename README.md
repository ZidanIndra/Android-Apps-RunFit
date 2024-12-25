# RunFit

RunFit adalah aplikasi Android yang dikembangkan sebagai proyek akhir untuk mata kuliah **Piranti Bergerak** di Universitas Negeri Medan. Aplikasi ini menggunakan sensor **accelerometer** pada perangkat pengguna untuk mendeteksi langkah saat berolahraga, menghitung durasi aktivitas, dan mengukur total jarak yang ditempuh.

## Fitur Utama
- **Pendeteksi Langkah**: Menggunakan sensor accelerometer untuk menghitung setiap langkah pengguna.
- **Hitungan Waktu Olahraga**: Merekam total waktu pengguna dalam melakukan aktivitas olahraga.
- **Total Jarak Tempuh**: Mengukur jarak yang ditempuh berdasarkan langkah yang diambil.

## Teknologi yang Digunakan
- **Platform**: Android
- **Sensor**: Accelerometer pada perangkat untuk pendeteksian langkah.
- **Bahasa Pemrograman**: Java 

## Cara Kerja
1. **Deteksi Langkah**: RunFit memanfaatkan sensor accelerometer pada smartphone untuk menghitung langkah setiap kali pengguna bergerak.
2. **Hitung Waktu**: Waktu aktivitas olahraga dihitung sejak pengguna memulai hingga berhenti.
3. **Perhitungan Jarak**: Jarak dihitung berdasarkan jumlah langkah pengguna dengan asumsi setiap langkah bernilai 0.7 meter.

## Instalasi
1. Clone repositori ini:
   ```bash
   git clone https://github.com/username/RunFit.git
   ```
2. Buka project di Android Studio.
3. Build dan run aplikasi pada perangkat fisik dengan sensor accelerometer.

## Tampilan
**1. SplashScreen**
![WhatsApp Image 2024-12-25 at 21 47 08_0ff1994c](https://github.com/user-attachments/assets/a0cc900e-5cbb-4333-8c50-ef49576a77a3)

**2. Halaman Latihan Aplikasi**
![WhatsApp Image 2024-12-25 at 21 47 09_3c08692b](https://github.com/user-attachments/assets/828fff9c-4b8c-43ce-a19f-6ca3c6ea21d9)

## Penggunaan
- **Mulai Aktivitas**: Tekan tombol mulai untuk memulai menghitung langkah, waktu, dan jarak.
- **Pantau Aktivitas**: Data langkah, waktu, dan jarak akan ditampilkan secara real-time.
- **Akhiri Aktivitas**: Tekan tombol stop (jika ada) untuk berhenti dan melihat hasil akhir.
