package com.example.fadrinotes.Activity.AuthentUser.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.fadrinotes.Activity.AuthentUser.DataModels.Users;
import com.example.fadrinotes.Activity.AuthentUser.Repository.UserManager;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel {
    private UserManager userManager;
    private ValidationAuth validationAuth;

    private MutableLiveData<Boolean> registrationStatus = new MutableLiveData<>();
    private MutableLiveData<Boolean> loginStatus = new MutableLiveData<>();

    public UserModel(Context context) {
        this.userManager = new UserManager(context);
        this.validationAuth = new ValidationAuth(context);
    }

    public void createUsers(String username, String email, String password) {
        boolean isValid = validationAuth.validasiInputRegister(username, email, password);
        if (isValid) {
            Users users = new Users(0, email, password);
            boolean isRegisterSuccess = userManager.registerUser(users);
            userManager.checkEmailForRegister(email);
            registrationStatus.setValue(isRegisterSuccess);
        } else {
            registrationStatus.setValue(false);
        }
    }

    public void loginUser(String email, String password) {
        boolean isValid = validationAuth.validasiLogin(email, password);
        if (isValid) {
            boolean isLoginSuccess = userManager.loginUser(email, password);
            userManager.checkEmailForLogin(email);
            loginStatus.setValue(isLoginSuccess);
        } else {
            loginStatus.setValue(false);
        }
    }
}
