package com.example.usuario.manageproductsnavigationdrawer.interfaces;

public interface LoginPresenter {

    interface View {
        void setMessageError(String messageError, int viewId);
        void startActivity();
    }

    interface Presenter {

        int validateCredentialsUser(String user);
        int validateCredentialsPassword(String password);

        // Lo implementamos en la interfaz gracias a Java 8
        // aunque mezclamos código con interfaces y no parece buena idea
        // pero nos ahorramos clases estáticas

        /*
        static int validateCredentialsUser(String user) {
            if (TextUtils.isEmpty(user))
                return Error.DATA_EMPTY;
            return Error.OK;
        }

        static int validateCredentialsPassword(String password) {
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
        */

    }
}
