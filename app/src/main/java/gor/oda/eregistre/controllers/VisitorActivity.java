package gor.oda.eregistre.controllers;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import gor.oda.eregistre.Constant;
import gor.oda.eregistre.EregistreApi;
import gor.oda.eregistre.R;
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

public class VisitorActivity extends AppCompatActivity {

    EditText search;
    TextView info;
    Button valider,depart,arrive;
    CardView result;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);


        valider=(Button)findViewById(R.id.valider);
        search=(EditText)findViewById(R.id.recherche);
        info=(TextView)findViewById(R.id.info);

        arrive=(Button)findViewById(R.id.arrivee);
        depart=(Button)findViewById(R.id.depart);

        result=(CardView)findViewById(R.id.cardview);

        progressDialog = new ProgressDialog(VisitorActivity.this);


        result.setVisibility(View.INVISIBLE);
        info.setVisibility(View.INVISIBLE);

        arrive.setVisibility(View.INVISIBLE);
        depart.setVisibility(View.INVISIBLE);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                verifVisitor();

            }
        });

    }

    public void verifVisitor() {

        progressDialog.setMessage("Recherche en cours.. ");
        progressDialog.show();

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        EregistreApi eregistreApi = retrofit.create(EregistreApi.class);
//
//        Call<Visitor> call = eregistreApi.getVisitor(search.toString().trim());
        //Toast.makeText(getApplicationContext(),search.getText().toString(), Toast.LENGTH_LONG).show();
//
//        Call<Visiteur> call = RetrofitClient
//                .getInstance()
//                .eregistreApi()
//                .getVisitor(search.toString().trim());

        Retrofit myRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EregistreApi eregistreApi = myRetrofit.create(EregistreApi.class);

        Call<Visiteur> recherche = eregistreApi.getVisitor(search.getText().toString().trim());

        recherche.enqueue(new Callback<Visiteur>() {
            @Override
            public void onResponse(Call<Visiteur> call, Response<Visiteur> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();

                    result.setVisibility(View.VISIBLE);
                    info.setVisibility(View.VISIBLE);

                    info.setText(response.body().getNom()+" "+response.body().getPrenoms());


                    arrive.setVisibility(View.VISIBLE);
                    depart.setVisibility(View.VISIBLE);

                    arrive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            heureArrivee(response.body().getNumPiece().toString());

                        }
                    });

                    depart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            heureDepart(response.body().getNumPiece().toString());

                        }
                    });

                }

            }

            @Override
            public void onFailure(Call<Visiteur> call, Throwable t) {


                AlertDialog.Builder builder = new AlertDialog.Builder(
                        VisitorActivity.this);
                builder.setTitle("Visiteur non enregistré");
                builder.setMessage(" Souhaitez vous l'enregistrer ?");
                builder.setNegativeButton("NON",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                //Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
                            }
                        });
                builder.setPositiveButton("OUI",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent intent= new Intent(VisitorActivity.this,FormulaireVisiteurs.class);
                                startActivity(intent);
                            }
                        });
                builder.show();
                //Toast.makeText(VisitorActivity.this,"",Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void heureDepart(String piece){

        //Toast.makeText(getApplicationContext(),piece, Toast.LENGTH_LONG).show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EregistreApi eregistreApi = retrofit.create(EregistreApi.class);

        Call<Out> depart = eregistreApi.Sortie(piece);
        depart.enqueue(new Callback<Out>() {
            @Override
            public void onResponse(Call<Out> call, Response<Out> response) {


                Toast.makeText(getApplicationContext(),"Heure de départ enregistré",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),MainMenu.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<Out> call, Throwable t) {

            }
        });
    }


    public void heureArrivee(String numPiece){


        //Toast.makeText(getApplicationContext(),numPiece, Toast.LENGTH_LONG).show();
        EditText editTextField;
        editTextField = new EditText(getApplicationContext());
        android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(getApplicationContext())
                .setTitle("Motif")
                .setMessage("Saisissez le motif")
                .setView(editTextField)
                .setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String editTextInput = editTextField.getText().toString().trim();

                        RequestBody objet = RequestBody.create(MultipartBody.FORM,editTextInput);

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Constant.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        EregistreApi eregistreApi = retrofit.create(EregistreApi.class);

                        Call<ResponseBody> call = eregistreApi.motif(numPiece.toString().trim(), Constant.id_user,objet);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                Toast.makeText(getApplicationContext(),"Visiteur enregistré avec succès",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(),MainMenu.class);
                                startActivity(intent);

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });


                    }
                })
                .create();
        dialog.show();
    }

}
