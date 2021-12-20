package gor.oda.eregistre.controllers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import gor.oda.eregistre.Constant;
import gor.oda.eregistre.R;
import gor.oda.eregistre.RetrofitClient;
import gor.oda.eregistre.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    EditText login, motdepasse;
    Button LoginButton;
    String LoginHolder, PasswordHolder;
    ProgressDialog progressDialog;

    Boolean CheckEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        login = (EditText) findViewById(R.id.login);

        motdepasse = (EditText) findViewById(R.id.password);

        LoginButton = (Button) findViewById(R.id.connexion);


        progressDialog = new ProgressDialog(HomeActivity.this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

                if (CheckEditText) {

                    //UserLogin();
                    Login();

                } else {

                    Toast.makeText(HomeActivity.this, "Veuillez remplir tous les champs du formulaire.", Toast.LENGTH_LONG).show();

                }


            }
        });

    }

    public void Login() {

        progressDialog.setMessage("Patientez svp.. ");
        progressDialog.show();

        Call<User> call = RetrofitClient
                .getInstance()
                .eregistreApi()
                .getUser(LoginHolder,PasswordHolder);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    response.body();
                    progressDialog.dismiss();
                    Toast.makeText(HomeActivity.this, "Bienvenue "+ response.body().getNom().toString(),Toast.LENGTH_LONG).show();

                    Constant.id_user = response.body().getIdentifiant();
                    Intent intent = new Intent(HomeActivity.this, MainMenu.class);
                    //intent.putExtra("identifiant_user",response.body().getIdentifiant());
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                //Toast.makeText(HomeActivity.this,"t.toString()",Toast.LENGTH_LONG).show();
                Toast.makeText(HomeActivity.this,"Login ou Mot de passe incorrect",Toast.LENGTH_LONG).show();
                Log.e("eRegistreError",t.getMessage());
                progressDialog.dismiss();

            }
        });

    }


    public void CheckEditTextIsEmptyOrNot() {

        LoginHolder = login.getText().toString().trim();
        PasswordHolder = motdepasse.getText().toString().trim();

        if (TextUtils.isEmpty(LoginHolder) || TextUtils.isEmpty(PasswordHolder)) {

            CheckEditText = false;

        } else {

            CheckEditText = true;
        }
    }
}
