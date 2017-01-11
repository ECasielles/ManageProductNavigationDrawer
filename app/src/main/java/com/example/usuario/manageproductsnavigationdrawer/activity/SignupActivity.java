package com.example.usuario.manageproductsnavigationdrawer.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.usuario.manageproductsnavigationdrawer.R;
import com.example.usuario.manageproductsnavigationdrawer.fragment.ListProductFragment;
import com.example.usuario.manageproductsnavigationdrawer.interfaces.SignupPresenter;
import com.example.usuario.manageproductsnavigationdrawer.presenter.SignupPresenterImpl;

public class SignupActivity extends AppCompatActivity implements SignupPresenter.View {

    private Spinner spnCounty;
    private Spinner spnCity;
    private RadioGroup typeClient;
    private TextInputLayout tilNameCompany;
    private EditText edtUser;
    private EditText edtPwd;
    private EditText edtEmail;
    private ViewGroup parentLayout;

    private AdapterView.OnItemSelectedListener spinnerListener; //Atributo delegado

    private SignupPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        parentLayout = (RelativeLayout) findViewById(R.id.activity_signup);

        spnCounty = (Spinner) findViewById(R.id.spnSignupProvincia);
        spnCity = (Spinner) findViewById(R.id.spnSignupLocalidad);
        tilNameCompany = (TextInputLayout) findViewById(R.id.tilSignupCompany);
        typeClient = (RadioGroup) findViewById(R.id.rgpSignupIsCompany);

        edtUser = (EditText) findViewById(R.id.edtSignupUsername);
        edtPwd = (EditText) findViewById(R.id.edtSignupUserPassword);
        edtEmail = (EditText) findViewById(R.id.edtSignupEmail);

        presenter = new SignupPresenterImpl(this);

        initRadioClient();
        loadSpinnerCounty();
    }

    public void signup(View view) {
        // Recoge los datos de la vista (EN CASA)
        // Llama al método del presentador
        presenter.validateCredentials(
                edtUser.getText().toString(),
                edtPwd.getText().toString(),
                edtEmail.getText().toString()
        );
    }
    private void showCompany(boolean isVisible) {
        tilNameCompany.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
    private void initRadioClient() {
        typeClient.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtSignupClient:
                        showCompany(false);
                        break;
                    case R.id.rbtSignupCompany:
                        showCompany(true);
                        break;
                }
            }
        });
    }
    // Loads both spinners, starting with the counties/provinces drop down list
    private void loadSpinnerCounty() {
        // Le pasamos CharSequence para poder manejar StringBuilder, etc.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.provincias, android.R.layout.simple_spinner_dropdown_item);
        spnCounty.setAdapter(adapter);

        spinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                switch (adapterView.getId()) {
                    case R.id.spnSignupProvincia:
                        loadSpinnerCity(position);
                        break;
                    case R.id.spnSignupLocalidad:
                        showSelectedCity();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        spnCounty.setOnItemSelectedListener(spinnerListener);
        spnCity.setOnItemSelectedListener(spinnerListener);
    }
    private void loadSpinnerCity(int position) {
        // Inicializa el Spinner Localidades
        TypedArray typedCity = getResources().obtainTypedArray(R.array.array_provincia_a_localidades);
        CharSequence[] arrayCities = typedCity.getTextArray(position);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, arrayCities);

        spnCity.setAdapter(adapter);
    }

    /**
     * Shows selected city
     */
    public void showSelectedCity() {
        // Mensaje concatenado con la provincia y la localidad elegidas
        Toast.makeText(
                getApplicationContext(),
                getString(
                        R.string.messageCityCounty,
                        spnCounty.getSelectedItem().toString(),
                        spnCity.getSelectedItem().toString()
                ),
                Toast.LENGTH_LONG
        ).show();
    }

    /**
     * Method that shows a custom message in a {@link Snackbar} component
     * depending on error occurred during data validation.
     * @param nameResource Error message String name showed
     * Use {@link android.content.res.Resources#getIdentifier(String, String, String)} to get class id in R class.
     * @param viewId view id in which error will show
     */
    @Override
    public void setMessageError(String nameResource, int viewId) {
        //Toast.makeText(this, messageError, Toast.LENGTH_SHORT).show();
        // We have to pick the resource whose name is that given as a parameter
        String errorMessage = getResources().getString(getResources().
                getIdentifier(nameResource, "string", getPackageName()));

        switch (viewId){
            case R.id.tilSignupUsername:
                //tilUser.setError(errorMessage);
                Snackbar.make(parentLayout, errorMessage, Snackbar.LENGTH_LONG).show();
                break;
            case R.id.tilSignupUserPassword:
                //tilPassword.setError(errorMessage);
                Snackbar.make(parentLayout, errorMessage, Snackbar.LENGTH_LONG).show();
                break;
            case R.id.tilSignupEmail:
                Snackbar.make(parentLayout, errorMessage, Snackbar.LENGTH_LONG).show();
                break;
        }
    }

    // Incompleto
    public boolean validate(){
        //Cambia las preferencias
        // El método validar guarda también las preferencias
        // User
        // Password
        // AccountPreferencesImpl

        boolean isValid = true;

        EditText edtEmail = (EditText) findViewById(R.id.edtSignupEmail);
        EditText edtUsername = (EditText) findViewById(R.id.edtSignupUsername);
        EditText edtPwd = (EditText) findViewById(R.id.edtSignupUserPassword);

        String email = edtEmail.getText().toString();
        String username = edtUsername.getText().toString();
        String pwd = edtPwd.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = false;
        }

        //Switch con Snackbar

        return isValid;
    }

    public void startActivity() {
        Intent intent = new Intent(SignupActivity.this, ListProductFragment.class);
        startActivity(intent);
        // Closes this activity after validation
        finish();
    }

}
