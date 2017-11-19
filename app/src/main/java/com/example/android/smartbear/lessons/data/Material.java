package com.example.android.smartbear.lessons.data;

/**
 * Created by parsh on 19.11.2017.
 */

public class Material {
    private final String name;
    private final String type;
    private final String reference;

    public Material(String name, String type, String reference) {
        this.name = name;
        this.type = type;
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getReference() {
        return reference;
    }
}
