package com.example.fadrinotes.Activity.AuthentUser.DataModels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Users {
    private int id;
    private String email;
    private String password;

    public Users(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

}
