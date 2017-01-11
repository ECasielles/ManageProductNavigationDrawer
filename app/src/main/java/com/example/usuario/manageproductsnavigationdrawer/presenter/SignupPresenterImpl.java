package com.example.usuario.manageproductsnavigationdrawer.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import com.example.usuario.manageproductsnavigationdrawer.R;
import com.example.usuario.manageproductsnavigationdrawer.interfaces.LoginPresenter;
import com.example.usuario.manageproductsnavigationdrawer.interfaces.SignupPresenter;
import com.example.usuario.manageproductsnavigationdrawer.model.Error;
import com.example.usuario.manageproductsnavigationdrawer.preferences.AccountPreferencesImpl;
import com.example.usuario.manageproductsnavigationdrawer.utils.ErrorMapUtils;

public class SignupPresenterImpl implements LoginPresenter.Presenter, SignupPresenter.PresenterUser {

    private int validateUser;
    private int validatePassword;
    private int validateEmail;
    private LoginPresenter.View view;
    private Context context;

    public SignupPresenterImpl(LoginPresenter.View signupView){
        this.view = signupView;
        this.context = (Context) signupView;
    }

    // Validates user, password and email strings
    public void validateCredentials(String user, String password, String email) {
        validateUser = validateCredentialsUser(user);
        validatePassword = validateCredentialsPassword(password);
        validateEmail = validateCredentialsEmail(email);

        if (validateUser == Error.OK) {
            if (validatePassword == Error.OK) {
                if (validateEmail == Error.OK) {
                    // Guardamos en las preferencias
                    savePreferences(user, password, email);
                    view.startActivity();
                } else {
                    String resourceName = ErrorMapUtils.getErrorMap(context).get(String.valueOf(validateEmail));
                    view.setMessageError(resourceName, R.id.tilSignupEmail);
                }
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

    private void savePreferences(String user, String password, String email) {
        AccountPreferencesImpl accountPreferences = (AccountPreferencesImpl) AccountPreferencesImpl.getInstance(context);
        accountPreferences.putUser(user);
        accountPreferences.putPassword(password);
        accountPreferences.putEmail(email);
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
        } else if (!password.matches(".*[a-z].*") || !password.matches(".*[A-Z].*")) {
            result = Error.PASSWORD_CASE;
        } else if (password.length() < 8) {
            result = Error.PASSWORD_LENGTH;
        }

        return result;
    }
    public int validateCredentialsEmail(String email) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return Error.EMAIL_INVALID;
        return Error.OK;
    }
}
