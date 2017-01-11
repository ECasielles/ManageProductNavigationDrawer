package com.example.usuario.manageproductsnavigationdrawer.model;

/**
 * Created by usuario on 11/01/17.
 */

public class Category {
    int id;
    int name;

    public Category(int id, int name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
