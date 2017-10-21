package com.example.android.smartbear;

/**
 * Created by parsh on 21.10.2017.
 */

public class UserModel {
    private final String name;
    private final String email;
    private final boolean isAdmin;

    public UserModel(String name, String email, boolean isAdmin) {
        this.name = name;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
