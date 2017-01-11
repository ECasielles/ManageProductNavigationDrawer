package com.example.usuario.manageproductsnavigationdrawer.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.usuario.manageproductsnavigationdrawer.R;
import com.example.usuario.manageproductsnavigationdrawer.interfaces.IProduct;
import com.example.usuario.manageproductsnavigationdrawer.interfaces.ProductPresenter;
import com.example.usuario.manageproductsnavigationdrawer.model.Product;

public class ConfirmDialog extends DialogFragment {

    private Product product;
    private ProductPresenter presenter;

    public interface OnDeleteProductListener {
        void deleteProduct(Product product);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        product = getArguments().getParcelable(IProduct.PRODUCT_KEY);
        return onCreateConfirmDialog();
    }

    public void setPresenter(ProductPresenter presenter) {
        this.presenter = presenter;
    }

    public Dialog onCreateConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.delete);
        builder
                .setMessage(getString(R.string.delete_product))
                .setCancelable(false)
                .setPositiveButton(R.string.yes,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        presenter.deleteProduct(product);
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }
}
