package com.example.usuario.manageproductsnavigationdrawer.utils;


import android.util.SparseBooleanArray;
import android.widget.ListView;

public class MultiChoicePresenter {

    public SparseBooleanArray sparseBooleanArray;

    public MultiChoicePresenter(ListView listProducts) {
        sparseBooleanArray = listProducts.getCheckedItemPositions();
    }

    public boolean isPositionChecked(int position) {
        Boolean result = sparseBooleanArray.get(position);
        return result == null ? false : result;
    }

    public void setNewSelection(int position, boolean checked) {

    }

    public void removeSelection(int position) {

    }

    public void clearSelection() {

    }

    public void deleteSelectedProduct() {

    }
}
