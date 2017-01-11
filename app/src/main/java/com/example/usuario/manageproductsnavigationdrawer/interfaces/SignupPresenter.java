package com.example.usuario.manageproductsnavigationdrawer.interfaces;

public interface SignupPresenter extends LoginPresenter {

    // interface Presenter Adds code to superclass' presenter interface
    // So we need it to have a different name to make a clear distinction
    interface PresenterUser {
        int validateCredentialsEmail(String email);

        /*
        static int validateCredentialsEmail(String email) {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                return Error.EMAIL_INVALID;
            return Error.OK;
        }
        */
    }

}
