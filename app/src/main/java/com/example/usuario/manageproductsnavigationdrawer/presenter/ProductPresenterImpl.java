package com.example.usuario.manageproductsnavigationdrawer.presenter;

import com.example.usuario.manageproductsnavigationdrawer.dialog.ConfirmDialog;
import com.example.usuario.manageproductsnavigationdrawer.interfaces.ProductPresenter;
import com.example.usuario.manageproductsnavigationdrawer.model.Product;
import com.example.usuario.manageproductsnavigationdrawer.repository.ProductRepository;

public class ProductPresenterImpl implements ConfirmDialog.OnDeleteProductListener, ProductPresenter {

    private ProductPresenter.View view;
    private ProductRepository repository;

    public ProductPresenterImpl(ProductPresenter.View view) {
        this.view = view;
        this.repository = ProductRepository.getInstance();
    }

    //Este método es importantísimo a la hora de paginar
    @Override
    public void loadProducts() {
        if(repository.getProducts().isEmpty())
            view.showEmptyText(true);
        else
            view.showProducts(repository.getProducts());
    }
    @Override
    public Product getProduct(String id) {
        //return repository.getProducts(id);
        return null;
    }
    @Override
    public void deleteProduct(Product product) {

        repository.deleteProduct(product);

        //DEPENDE DE LA IMPLEMENTACIÓN repository vs adapter
        //loadProducts(); ó view.deleteProduct();
        //El repositorio hace una segunda comprobación ó
        //El adaptador se borra, pero entonces implementamos
        //un getAdapter.

        //view.getAdapter().deleteProduct();
        //if (view.getAdapter().isEmpty())
            //view.showEmptyText(true);

        view.showMessageDelete(product);
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }

    @Override
    public void addProduct(Product product) {
        repository.addProduct(product);
        loadProducts();

    }

}
