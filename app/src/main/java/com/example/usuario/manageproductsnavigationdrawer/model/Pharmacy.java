package com.example.usuario.manageproductsnavigationdrawer.model;

/**
 * Created by usuario on 11/01/17.
 */

public class Pharmacy {
    int id;
    String cif;
    String address;
    String phoneNumber;
    String email;

    public Pharmacy(int id, String cif, String address, String phoneNumber, String email) {
        this.id = id;
        this.cif = cif;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
