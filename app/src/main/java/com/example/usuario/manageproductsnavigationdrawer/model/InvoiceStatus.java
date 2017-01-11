package com.example.usuario.manageproductsnavigationdrawer.model;

/**
 * Created by usuario on 11/01/17.
 */

public class InvoiceStatus {
    int id;
    String name;

    public InvoiceStatus(int id, String name) {
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
