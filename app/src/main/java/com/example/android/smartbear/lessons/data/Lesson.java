package com.example.android.smartbear.lessons.data;

import java.util.List;

/**
 * Created by parsh on 19.11.2017.
 */

public class Lesson {
    private final String name;
    private final List<Material> materials;
    private final int order;

    public Lesson(String name, List<Material> materials, int order) {
        this.name = name;
        this.materials = materials;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public int getOrder() {
        return order;
    }
}
