package com.example.usuario.manageproductsnavigationdrawer.model;

import java.util.Date;

/**
 * Created by usuario on 11/01/17.
 */

public class Invoice {
    int id;
    int idPharma;
    private Date date;

    public Invoice(int id, int idPharma, Date date) {
        this.id = id;
        this.idPharma = idPharma;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
