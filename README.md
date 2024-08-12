# FadriNotes
 Test pembuatan Aplikasi
Rahmat Fadri Alkahfi
Test Pembuatan Aplikasi.

Assalamuallaikum wr wb

Terima kasih kepada Bpk/Ibu yang terhormat atas kesempatannya, saya akan menjelaskan tata cara pengunaan dan Struktur proyek aplikasi ini ini.

Fadri Notes App

Terima kasih kepada Bapak/Ibu yang terhormat atas kesempatannya. Saya akan menjelaskan tata cara penggunaan dan struktur proyek aplikasi ini.

Deskripsi Aplikasi

Fadri Notes App adalah aplikasi catatan yang dirancang untuk membantu pengguna dalam mengelola catatan mereka dengan mudah. Aplikasi ini menyediakan fitur-fitur utama seperti menambah, mengedit, menghapus, mencari catatan, dan membuat alarm pengingat sederhana. Pengguna harus login untuk mengakses fitur-fitur utama aplikasi.

Tata Cara Penggunaan

Memulai Aplikasi

Setelah membuka aplikasi, akan disambut dengan tampilan splash screen selama 3 detik.
Login atau Registrasi

Jika pengguna baru, akan diarahkan ke halaman login atau registrasi. Masukkan email dan kata sandi untuk membuat akun baru atau login dengan akun yang sudah ada.

Halaman Utama

Setelah login, akan masuk ke halaman utama yang menampilkan daftar catatan. Di sini dapat melihat catatan yang telah buat di akun yang sama sebelumnya.

Menambah Catatan

Tekan tombol "+" atau tombol tambah untuk membuat catatan baru. dapat mengisi judul, headline, konten, dan dapat menambahkan pengingat tambahan dengan waktu dan tanggal.

Melihat Catatan

Ketuk salah satu catatan dari daftar untuk melihat detailnya. Di sini dapat membaca isi catatan.
Memperbarui Catatan

Untuk memperbarui catatan yang ada, pilih catatan yang ingin diedit dan perbarui informasi yang diperlukan. Setelah selesai, simpan perubahan.
Menghapus Catatan

Jika ingin menghapus catatan, tahan dan tekan catatan yang ingin dihapus dan konfirmasikan tindakan penghapusan.
Mencari Catatan

Gunakan fitur pencarian untuk menemukan catatan dengan cepat. dapat mencari berdasarkan judul atau konten catatan.
Struktur Proyek

Activity

Regristrasi & Login user

Saat regristrasi, user diminta memasukan username, email, dan password.

dan sudah diimplementasikan validasi validasi pendaftaran akun.


saat Login, user akan memasukan email dan password dan sudah diimplementasikan validasi login.

MainActivity.java: Menampilkan daftar catatan dan mengelola navigasi utama aplikasi.

SplashScreen.java: Menampilkan splash screen saat aplikasi dimulai.

Model

NotesModel.java: Model data yang menyimpan informasi tentang catatan, seperti ID, judul, headline, konten, dan tanggal.

Repository

NotesManager.java: Kelas yang bertanggung jawab untuk operasi database pada catatan, seperti menambah, mengedit, menghapus, dan mencari catatan.

ViewModel

NotesViewModel.java: Kelas yang mengelola data dan operasi terkait catatan, serta menyediakan LiveData untuk interaksi antara View dan Model.
Database

DataBaseNotesApp.java: Kelas helper untuk mengelola database SQLite, termasuk pembuatan dan pemeliharaan tabel catatan dan pengguna.                              
