package gor.oda.eregistre.controllers;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import gor.oda.eregistre.adapter.AdapterHist;
import gor.oda.eregistre.R;
import gor.oda.eregistre.RetrofitClient;
import gor.oda.eregistre.models.Historique;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HistActivity extends AppCompatActivity {

   RecyclerView recyclerView;
   AdapterHist adapterHist;

   ArrayList<Historique> historiques = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        recyclerView = (RecyclerView)findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AfficherHist();

    }

    public void AfficherHist() {

        Call<List<Historique>> call = RetrofitClient
                .getInstance()
                .eregistreApi()
                .getHistorique();

        call.enqueue(new Callback<List<Historique>>() {
            @Override
            public void onResponse(Call<List<Historique>> call, Response<List<Historique>> response) {
                if (response.isSuccessful()){
                    response.body();
                    historiques = new ArrayList<>(response.body());
                    adapterHist = new AdapterHist(HistActivity.this,historiques);
                    recyclerView.setAdapter(adapterHist);
                }


            }

            @Override
            public void onFailure(Call<List<Historique>> call, Throwable t) {
                //Toast.makeText(HistActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                Log.e("Erreur Affichage",t.getMessage());

            }
        });

    }

}

