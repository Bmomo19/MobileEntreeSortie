package gor.oda.eregistre.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import gor.oda.eregistre.Constant;
import gor.oda.eregistre.EregistreApi;
import gor.oda.eregistre.R;
import gor.oda.eregistre.RetrofitClient;
import gor.oda.eregistre.models.Out;
import gor.oda.eregistre.models.Visiteur;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VerificationScan extends AppCompatActivity {

    String matricule;
    TextView name,surname,idMat,t1,t2,t3,msg_erreur;
    Button bt_depart,bt_arrivee;
    String num_piece;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificationscan);

        name=(TextView)findViewById(R.id.nom);
        surname=(TextView)findViewById(R.id.prenom);
        idMat=(TextView)findViewById(R.id.matricule);

        msg_erreur=(TextView)findViewById(R.id.erreur);

        t1=(TextView)findViewById(R.id.textView3);
        t2=(TextView)findViewById(R.id.textView4);
        t3=(TextView)findViewById(R.id.textView5);




        t1.setVisibility(View.INVISIBLE);
        t2.setVisibility(View.INVISIBLE);
        t3.setVisibility(View.INVISIBLE);

        bt_arrivee = (Button)findViewById(R.id.arrivee);
        bt_depart = (Button)findViewById(R.id.depart);

        bt_depart.setVisibility(View.INVISIBLE);
        bt_arrivee.setVisibility(View.INVISIBLE);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            matricule = extras.getString("code");
        }

       verifScanByMatricule(matricule);

        bt_arrivee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestBody objet = RequestBody.create(MultipartBody.FORM,"");
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constant.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                EregistreApi eregistreApi = retrofit.create(EregistreApi.class);

                Call<ResponseBody> call = eregistreApi.motif(num_piece, Constant.id_user,objet);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        Toast.makeText(getApplicationContext(),"Heure d'entrée enregistré",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(VerificationScan.this,MainMenu.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            }
        });


        bt_depart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constant.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                EregistreApi eregistreApi = retrofit.create(EregistreApi.class);

                Call<Out> call = eregistreApi.Sortie(num_piece);
                call.enqueue(new Callback<Out>() {
                    @Override
                    public void onResponse(Call<Out> call, Response<Out> response) {

                        Toast.makeText(getApplicationContext(),"Heure de départ enregistré",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(VerificationScan.this,MainMenu.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Out> call, Throwable t) {

                    }
                });

            }
        });


    }


    public void verifScanByMatricule(String matricule) {

        Call<Visiteur> call = RetrofitClient
                .getInstance()
                .eregistreApi()
                .getVisitor(matricule);

        call.enqueue(new Callback<Visiteur>() {
            @Override
            public void onResponse(Call<Visiteur> call, Response<Visiteur> response) {
                    if (response.isSuccessful()){

                        t1.setVisibility(View.VISIBLE);
                        t2.setVisibility(View.VISIBLE);
                        t3.setVisibility(View.VISIBLE);
                        response.body();
                        name.setText(response.body().getNom());
                        surname.setText(response.body().getPrenoms());
                        idMat.setText(response.body().getNumPiece());

                        bt_depart.setVisibility(View.VISIBLE);
                        bt_arrivee.setVisibility(View.VISIBLE);
                   }

                //Log.e("TAG",response.body().getPrenoms());

            }

            @Override
            public void onFailure(Call<Visiteur> call, Throwable t) {

                msg_erreur.setText("Code QR invalide");
                //Toast.makeText(VerificationScan.this,"Echec",Toast.LENGTH_LONG).show();

            }
        });

    }



}
