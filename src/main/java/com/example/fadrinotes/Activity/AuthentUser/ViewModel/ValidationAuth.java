package com.example.fadrinotes.Activity.AuthentUser.ViewModel;

import android.content.Context;
import android.widget.Toast;

import com.example.fadrinotes.Activity.AuthentUser.Repository.UserManager;

public class ValidationAuth {
    Context context;
    UserManager userManager;

    public ValidationAuth(Context context) {
        this.context = context;
        this.userManager = new UserManager(context);
    }

    public boolean validasiInputRegister(String username, String email, String password) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showToast(context, "Isi semua field terlebih dahulu");
            return false;
        }

        if (username.length() < 5) {
            showToast(context, "Username harus lebih dari 5 karakter");
            return false;
        }

        String invalidPatterns = "@#$%&*-,.!";
        for (char pattern : invalidPatterns.toCharArray()) {
            if (username.indexOf(pattern) >= 0) {
                showToast(context, "Username tidak boleh mengandung simbol");
                return false;
            }
        }

        if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            showToast(context, "Email tidak valid");
            return false;
        }

        if (password.length() < 5) {
            showToast(context, "Password harus lebih dari 5 karakter");
            return false;
        }

        if (!password.matches(".*[0-9].*")) {
            showToast(context, "Password harus mengandung angka");
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            showToast(context, "Password harus mengandung Huruf Besar");
            return false;
        }

        return true;
    }

    public boolean validasiLogin(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            showToast(context, "Isi semua field terlebih dahulu");
            return false;
        }

        if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            showToast(context, "Email tidak valid");
            return false;
        }

        if (password.length() < 5) {
            showToast(context, "Password harus lebih dari 5 karakter");
            return false;
        }

        if (!password.matches(".*[0-9].*")) {
            showToast(context, "Password harus mengandung angka");
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            showToast(context, "Password harus mengandung Huruf Besar");
            return false;
        }

        return true;
    }

    private void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
