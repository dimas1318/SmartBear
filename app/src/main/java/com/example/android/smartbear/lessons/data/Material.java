package com.example.android.smartbear.lessons.data;

import java.io.Serializable;

/**
 * Created by parsh on 19.11.2017.
 */

public class Material implements Serializable {
    private String Name;
    private String Type;
    private String Reference;
    private int Order;

    public Material() {
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }

    public String getReference() {
        return Reference;
    }
}
