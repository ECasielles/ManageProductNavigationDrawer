package com.example.usuario.manageproductsnavigationdrawer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usuario.manageproductsnavigationdrawer.R;
import com.example.usuario.manageproductsnavigationdrawer.model.Product;
import com.example.usuario.manageproductsnavigationdrawer.repository.ProductRepository;

import java.util.Collections;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    private static boolean SORTED_ASC = false;

    public ProductAdapter(Context context) {
        // El ArrayList interno es igual al que se obtiene con getProducts
        super(context, R.layout.item_product, (List<Product>) ProductRepository.getInstance());
    }

    // Ejemplo de método de filtrado que debe ir en el constructor
    // Con estos métodos ya empezamos a jugar con los datos del adapter
    public void addAllProducts(List<Product> origin){
        addAll();
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ProductHolder productHolder;

        if (view == null) {
            //LayoutInflater layoutInflater = (LayoutInflater.from(context));
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Se inflan tantos elementos view como tengamos
            view = layoutInflater.inflate(R.layout.item_product, null);

            productHolder = new ProductHolder();
            // Error típico de examen no igualar el imageView de la vista
            productHolder.productImage = (ImageView)view.findViewById(R.id.ivwItemProductImage);
            productHolder.txvName = (TextView)view.findViewById(R.id.txvItemProductName);
            productHolder.txvStock = (TextView)view.findViewById(R.id.txvItemProductStock);
            productHolder.txvPrice = (TextView)view.findViewById(R.id.txvItemProductPrice);

            //Aquí Lourdes nos puede poner cualquier tipo de comprobación en el examen
            //Y siempre cae en el examen

            //Si hay que hacer un filtrado de los elementos, se hará con un método propio
            //en el constructor
            view.setTag(productHolder);
        } else {
            productHolder = (ProductHolder)view.getTag();
        }

        productHolder.productImage.setBackgroundResource(getItem(position).getmImage());
        productHolder.txvName.setText(getItem(position).getmName());
        productHolder.txvStock.setText(getItem(position).getFotmattedUnitsInStock());
        productHolder.txvPrice.setText(getItem(position).getFormatedPrice());

        return view;
    }

    public void sortAlphabetically() {
        if(!SORTED_ASC) {
            sort(Product.NAME_COMPARATOR);
            SORTED_ASC = true;
        } else {
            sort(Collections.<Product>reverseOrder());
            SORTED_ASC = false;
        }
        //notifyDataSetChanged();
    }

    public void addProduct(Product product) {
        add(product);
        //dao.add(product);
        //notifyDataSetChanged();
    }

    public void editProduct(Product product) {
        int position = searchProduct(product.getmId());
        if (position != -1) {
            remove(getItem(getPosition(product)));
            add(product);
            sort(Product.NAME_COMPARATOR);
        }
    }
    private int searchProduct(int id) {
        for (int i = 0; i < getCount(); i++)
            if (getItem(i).getmId() == (id))
                return i;
        return -1;
    }

    public void updateProduct (Product product){
        Product productOld = getItem(product.getmId());
        productOld = product;
        notifyDataSetChanged();
    }

    public void deleteProduct(Product product) {
        remove(product);
        notifyDataSetChanged();
    }

    public void deleteProduct() {
        clear();
        notifyDataSetChanged();
    }

    class ProductHolder {

        ImageView productImage;
        TextView txvName;
        TextView txvStock;
        TextView txvPrice;

    }

}
