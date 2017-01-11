package com.example.usuario.manageproductsnavigationdrawer.fragment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.usuario.manageproductsnavigationdrawer.R;
import com.example.usuario.manageproductsnavigationdrawer.interfaces.IProduct;
import com.example.usuario.manageproductsnavigationdrawer.model.Product;

public class ManageProductFragment extends Fragment implements IProduct.View{

    private Product product;
    private ImageView imageView;
    private TextInputLayout tilName;
    private TextInputLayout tilBrand;
    private TextInputLayout tilDescription;
    private TextInputLayout tilDosage;
    private TextInputLayout tilPrice;
    private TextInputLayout tilStock;
    private Button btnAction;
    private boolean addAction;
    private ManageProductListener mCallBack;

    public interface ManageProductListener {
        void showListProduct();
    }

    //Singleton que evita la duplicidad de gestores
    public static ManageProductFragment getInstance(Bundle args) {
        ManageProductFragment fragment = new ManageProductFragment();
        if(args != null)
            fragment.setArguments(args);
        return fragment;
    }

    //Faltaba terminar el onAttach
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallBack = (ManageProductListener) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException(ex.getMessage() + " activity must implement ManageProductListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Comprueba que no sea nulo antes de añadir el producto
        if (getArguments() != null)
            product = getArguments().getParcelable(IProduct.PRODUCT_KEY);
        else
            //Nuevo producto
            product = new Product("Nombre", "Descripción", "Dosis", "Marca", 0.0d, 0, R.drawable.pill);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_manage_product, container, false);

        imageView = (ImageView) rootView.findViewById(R.id.ivwItemProductImage);
        tilName = (TextInputLayout) rootView.findViewById(R.id.tilManageName);
        tilDescription = (TextInputLayout) rootView.findViewById(R.id.tilManageDescription);
        tilDosage = (TextInputLayout) rootView.findViewById(R.id.tilManageDosage);
        tilBrand = (TextInputLayout) rootView.findViewById(R.id.tilManageBrand);
        tilPrice = (TextInputLayout) rootView.findViewById(R.id.tilManagePrice);
        tilStock = (TextInputLayout) rootView.findViewById(R.id.tilManageStock);
        btnAction = (Button) rootView.findViewById(R.id.btnManageProductOk);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProduct();
            }
        });

        if(product != null) {
            imageView.setImageResource(product.getmImage());
            tilName.getEditText().setText(product.getmName());
            tilDescription.getEditText().setText(product.getmDescription());
            tilDosage.getEditText().setText(product.getmDosage());
            tilBrand.getEditText().setText(product.getmBrand());
            tilPrice.getEditText().setText(product.getFormatedPrice());
            tilStock.getEditText().setText(product.getFotmattedUnitsInStock());
        }
    }

    @Override
    public void setMessageError(String messageError, int viewId) {
        switch (viewId) {
            case R.id.tilManageName:
                tilName.setError(messageError);
                break;
            case R.id.tilManageDescription:
                tilDescription.setError(messageError);
                break;
            case R.id.tilManageDosage:
                tilDosage.setError(messageError);
                break;
            case R.id.tilManageBrand:
                tilBrand.setError(messageError);
                break;
            case R.id.tilManagePrice:
                tilPrice.setError(messageError);
                break;
            case R.id.tilManageStock:
                tilStock.setError(messageError);
                break;
        }

    }

    private void saveProduct() {
        if(product == null)
            product = new Product("Nombre", "Descripción", "Dosis", "Marca", 0.0d, 0, R.drawable.pill);
        product.setmName(tilName.getEditText().getText().toString());
        product.setmDescription(tilDescription.getEditText().getText().toString());
        product.setmBrand(tilBrand.getEditText().getText().toString());
        product.setmDosage(tilDosage.getEditText().getText().toString());
        product.setmPrice(Double.parseDouble(tilPrice.getEditText().getText().toString()));
        product.setmStock(Integer.parseInt(tilPrice.getEditText().getText().toString()));

        mCallBack.showListProduct();
    }

}
