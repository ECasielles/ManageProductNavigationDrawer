package com.example.usuario.manageproductsnavigationdrawer.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.example.usuario.manageproductsnavigationdrawer.R;
import com.example.usuario.manageproductsnavigationdrawer.interfaces.LoginPresenter;
import com.example.usuario.manageproductsnavigationdrawer.utils.ErrorMapUtils;
import com.example.usuario.manageproductsnavigationdrawer.model.Error;

public class LoginPresenterImpl implements LoginPresenter.Presenter {

    private int validateUser;
    private int validatePassword;
    private LoginPresenter.View view;
    private Context context;

    public LoginPresenterImpl(LoginPresenter.View loginView){
        this.view = loginView;
        this.context = (Context) loginView;
    }

    public void validateCredentialsLogin(String user, String password) {

        validateUser = validateCredentialsUser(user);
        validatePassword = validateCredentialsPassword(password);

        if (validateUser == Error.OK) {
            if (validatePassword == Error.OK) {
                // Se puede utilizar la llamada al método startActivity con un Intent
                // como parámetro y no tener que implementar el método startActivity() en la vista
                // porque llama al método super.startActivity() dentro de la Actividad
                view.startActivity();
            }else {
                String resourceName = ErrorMapUtils.getErrorMap(context).get(String.valueOf(validatePassword));
                view.setMessageError(resourceName, R.id.tilSignupUsername);
            }
        }else {
            // Extracts error name from a given error code
            String resourceName = ErrorMapUtils.getErrorMap(context).get(String.valueOf(validateUser));
            view.setMessageError(resourceName, R.id.tilSignupUsername);
        }
    }

    public int validateCredentialsUser(String user) {
        if (TextUtils.isEmpty(user))
            return Error.DATA_EMPTY;
        return Error.OK;
    }

    public int validateCredentialsPassword(String password) {
        int result = Error.OK;

        if (TextUtils.isEmpty(password)) {
            result = Error.DATA_EMPTY;
        } else if (!password.matches(".*[0-9].*")) {
            result = Error.PASSWORD_DIGIT;
        } else if (!password.matches(".*[a-zA-Z].*")) {
            result = Error.PASSWORD_CASE;
        } else if (password.length() < 8) {
            result = Error.PASSWORD_LENGTH;
        }

        return result;
    }

}
