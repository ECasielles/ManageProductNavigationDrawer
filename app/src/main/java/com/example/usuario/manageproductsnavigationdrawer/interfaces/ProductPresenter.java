package com.example.usuario.manageproductsnavigationdrawer.interfaces;

import com.example.usuario.manageproductsnavigationdrawer.model.Product;

import java.util.List;

public interface ProductPresenter {

    void loadProducts();
    Product getProduct(String id);
    void deleteProduct(Product product);

    //Hay gente que no pone el onDestroy
    void onDestroy();

    void addProduct(Product product);

    interface View {
        void showProducts(List<Product> products);
        void showEmptyText(boolean show);
        void showMessage(String message);

        void showMessageDelete(final Product product);
    }

}
