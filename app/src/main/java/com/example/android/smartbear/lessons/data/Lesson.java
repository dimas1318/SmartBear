package com.example.android.smartbear.lessons.data;

import java.util.List;

/**
 * Created by parsh on 19.11.2017.
 */

public class Lesson {
    private String Name;
    private List<Material> Materials;
    private int Order;

    public Lesson() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Material> getMaterials() {
        return Materials;
    }

    public void setMaterials(List<Material> materials) {
        Materials = materials;
    }

    public int getOrder() {
        return Order;
    }

    public void setOrder(int order) {
        Order = order;
    }
}
