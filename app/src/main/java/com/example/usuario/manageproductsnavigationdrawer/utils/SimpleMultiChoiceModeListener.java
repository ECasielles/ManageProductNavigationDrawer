package com.example.usuario.manageproductsnavigationdrawer.utils;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;

import com.example.usuario.manageproductsnavigationdrawer.R;

import java.util.ArrayList;

/**
 * Maneja los elementos de la selección múltiple y el ActionMode
 * No la hemos hecho anónima por la cantidad de código que se debe sobreescribir
 */
public class SimpleMultiChoiceModeListener implements AbsListView.MultiChoiceModeListener {

    private MultiChoicePresenter presenter;
    private Context context;
    private int statusBarColor;
    private int count;
    private ArrayList<View> listView;

    public SimpleMultiChoiceModeListener(Context context, ArrayList<View> listView, MultiChoicePresenter presenter) {
        this.context = context;
        this.count = 0;
        this.listView = listView;
    }

    //Se va a ejecutar siempre que un método sea seleccionado o deseleccionado en la lista
    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked) {
        //El objeto ActionMode es la barra de menú contextual que se superpone sobre la barra superior
        //position es posición del elemento en la lista
        //checked: true = seleccionado, false = deseleccionado
        //Requiere una estructura hashmap para ver qué elementos están seleccionados

        if(checked) {
            count++;
            presenter.setNewSelection(position, checked);
        } else {
            count--;
            presenter.removeSelection(position);
        }
        actionMode.setTitle(count);

    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        //Se crea el menu de acción y hay que realizar operaciones de inicialización
        //y otros cambios de estilo que vienen obligados por Material Design

        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        //Se lanza cuando se actualiza en menú de acción y
        //hay que realizar operaciones como las que cambian el color de la barra de estado, etc.
        //Cuando una barra se oculta y se vuelve a mostrar, debe usarse este método.

        //Hay que comprobar que esté permitido en esta SDK
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            statusBarColor = ((AppCompatActivity) context).getWindow().getStatusBarColor();
            ((AppCompatActivity)context).getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.background_status_bar));
        }
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        //Cuando le doy a un elemento de la barra, tendré un menú
        //El menú contextual en vez de aparecer en el RegisterContextMenu,
        //aparece con los iconos.
        //Según el botón seleccionado se hará una operación u otra.

        switch (menuItem.getItemId()) {
            case R.id.action_delete_product:
                presenter.deleteSelectedProduct();
                break;
        }
        actionMode.finish();
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        //Devolvemos la barra de estado al estado previo

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((AppCompatActivity)context).getWindow().setStatusBarColor(statusBarColor);
        }
        presenter.clearSelection();
        for(View v: listView) {
            v.setVisibility(View.INVISIBLE);
        }
        count = 0;
    }
}
