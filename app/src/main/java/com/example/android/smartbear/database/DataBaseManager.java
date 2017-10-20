package com.example.android.smartbear.database;

/**
 * Created by parsh on 20.10.2017.
 */

public class DataBaseManager {
    public static void saveDataIntoBase(String... data) {
        for (String dataPiece : data) {
            save(dataPiece);
        }
    }

    private static void save(String data) {}

    public static boolean validateUser(String email, String password) {
        return true;
    }
}
