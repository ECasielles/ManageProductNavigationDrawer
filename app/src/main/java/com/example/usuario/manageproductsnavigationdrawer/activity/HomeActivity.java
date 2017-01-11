package com.example.usuario.manageproductsnavigationdrawer.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.usuario.manageproductsnavigationdrawer.R;
import com.example.usuario.manageproductsnavigationdrawer.fragment.ListProductFragment;
import com.example.usuario.manageproductsnavigationdrawer.fragment.ManageProductFragment;
import com.example.usuario.manageproductsnavigationdrawer.fragment.MultiListProductFragment;

public class HomeActivity extends AppCompatActivity
        implements ManageProductFragment.ManageProductListener, ListProductFragment.ListProductListener {

    //Creo una lista o mapa de fragment
    //private ListProductFragment listProductFragment;
    private MultiListProductFragment multiListProductFragment;
    private ManageProductFragment manageProductFragment;
    //Otros autores ponen aquí el presentador por si lo necesitan


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_navigation);
        multiListProductFragment = new MultiListProductFragment();
        getSupportFragmentManager().beginTransaction().
                add(R.id.framehome, multiListProductFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.action_settings_general:
                intent = new Intent(this, GeneralSettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_settings_account:
                intent = new Intent(this, AccountSettingsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showManageProduct(Bundle bundle) {
        manageProductFragment = new ManageProductFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framehome, manageProductFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void showListProduct() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framehome, manageProductFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
