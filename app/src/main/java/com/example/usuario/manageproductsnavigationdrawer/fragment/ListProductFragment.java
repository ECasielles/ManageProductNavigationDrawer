package com.example.usuario.manageproductsnavigationdrawer.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.usuario.manageproductsnavigationdrawer.R;
import com.example.usuario.manageproductsnavigationdrawer.adapter.ProductAdapter;
import com.example.usuario.manageproductsnavigationdrawer.dialog.ConfirmDialog;
import com.example.usuario.manageproductsnavigationdrawer.interfaces.IProduct;
import com.example.usuario.manageproductsnavigationdrawer.interfaces.ProductPresenter;
import com.example.usuario.manageproductsnavigationdrawer.model.Product;
import com.example.usuario.manageproductsnavigationdrawer.presenter.ProductPresenterImpl;

import java.util.List;

//Podríamos heredar de ListFragment
public class ListProductFragment extends Fragment implements ProductPresenter.View {

    public static String PRODUCT_KEY = "product";
    private ProductAdapter adapter;
    private ListProductListener mCallBack;
    private ProductPresenter presenter;


    //No permitimos que un Fragment llame a otro
    //Lo va a gestionar la actividad
    public interface ListProductListener {
        //En vez de devolver un objeto habla con el presentador
        //que habla con el repositorio según se le diga
        void showManageProduct(Bundle bundle);
    }

    private ListView listProducts;
    private TextView emptyProduct;
    private boolean click = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inicializamos el adaptador y el presentador
        adapter = new ProductAdapter(getContext());
        presenter = new ProductPresenterImpl(this);

        //Retiene la instancia del fragment
        setRetainInstance(true);

        //Esta opción le dice a la actividad que el fragment
        //tiene su propio menú y llama al método callback onCreateOptionMenu
        setHasOptionsMenu(true);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
        presenter = null;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallBack = (ListProductListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(getContext().toString() +
                    " ListProductListener must be implemented");
        }
    }
    @Override
    public void onDetach() {
        //Para evitar fugas de memoria desvinculo los objetos persistentes
        super.onDetach();
        //Lo que se instancia, se elimina
        mCallBack = null;
    }
    //Lo que era onCreate ahora es onCreateView
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Cuando cogemos la vista, el findById lo hacemos sobre el objeto crear
        View rootView = inflater.inflate(R.layout.fragment_list_products, container, false);
        listProducts = (ListView) rootView.findViewById(R.id.listActivityProducts);
        listProducts.setAdapter(adapter);
        listProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(IProduct.PRODUCT_KEY, (Product)parent.getItemAtPosition(position));
                mCallBack.showListProduct(bundle);
                    }
        }
        );

        emptyProduct = (TextView) rootView.findViewById(R.id.empty);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click = !click;
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Interpolator interpolator = AnimationUtils.loadInterpolator(getContext(),
                            android.R.interpolator.fast_out_slow_in);

                    view.animate().
                            rotation(click ? 45f : 0).
                            setInterpolator(interpolator).
                            start();
                }
                mCallBack.showListProduct(null);
            }
        });
        registerForContextMenu(listProducts);
        return rootView;
    }
    //Método que infla el menú del fragment y lo empalma al otro menú
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_listproduct, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.action_sort_alphabetically:
                adapter.sortAlphabetically();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //Método que infla el menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Opciones de la lista");
        getActivity().getMenuInflater().inflate(R.menu.menu_contextual, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_delete_product:
                Bundle bundle = new Bundle();
                bundle.putParcelable(IProduct.PRODUCT_KEY, (Product) listProducts.getItemAtPosition(item.getItemId()));
                ConfirmDialog dialog = new ConfirmDialog();

                //Aquí pasamos parámetros al FragmentDialog

                //Aquí cada uno lo hace como quiera
                dialog.setPresenter(presenter);
                dialog.setArguments(bundle);
                dialog.show(getActivity().getSupportFragmentManager(), "SimpleDialog");
                return true;
            default:
                return super.onContextItemSelected(item);

        }
    }

    //Métodos de la interfaz View
    public void showProducts(List<Product> products) {
        adapter.updateProduct(products);
    }
    private void hideList(boolean hidden) {
        listProducts.setVisibility(hidden ? View.GONE : View.VISIBLE);
        emptyProduct.setVisibility(!hidden ? View.GONE : View.VISIBLE);
    }
    public void showEmptyText(boolean show) {
        hideList(show);
    }
    public void showMessage(String message) {

    }

    //Implementado por decidir que el repositorio
    //borre el adaptador
    @Override
    public ProductAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void showMesageDelete(final Product product) {
        Snackbar.make(getView(), "Producto eliminado", Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addProduct(product);
            }
        }).show();
    }
}
