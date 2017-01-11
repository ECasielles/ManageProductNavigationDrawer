package com.example.usuario.manageproductsnavigationdrawer.model;

/**
 * Created by usuario on 11/01/17.
 */

public class InvoiceLine {
    int idInvoice;
    int orderProduct;
    int idProduct;
    double price;

    public InvoiceLine(int idInvoice, int orderProduct, int idProduct, double price) {
        this.idInvoice = idInvoice;
        this.orderProduct = orderProduct;
        this.idProduct = idProduct;
        this.price = price;
    }

    public int getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(int idInvoice) {
        this.idInvoice = idInvoice;
    }
}
