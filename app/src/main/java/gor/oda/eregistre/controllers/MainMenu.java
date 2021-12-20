package gor.oda.eregistre.controllers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import gor.oda.eregistre.R;

import static android.widget.Toast.LENGTH_LONG;

public class MainMenu extends AppCompatActivity {

    Button btnAca, btnVisit,history;
    private static final int STORAGE_PERMISSION = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION);

        btnAca = (Button)findViewById(R.id.academie);
        btnVisit = (Button)findViewById(R.id.visitor);
        history = (Button)findViewById(R.id.historique);


        btnAca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ScanActivity.class);
                startActivity(intent);
            }
        });

        btnVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),VisitorActivity.class);
                startActivity(intent);

            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),HistActivity.class);
                startActivity(intent);

            }
        });


    }

    private void checkPermission(String permission, int requestCode) {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), permission)==PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(MainMenu.this,new String[]{permission},requestCode);

        }else {


        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION){

            if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainMenu.this,"Permissions accordés !", LENGTH_LONG).show();
            }else {
                Toast.makeText(MainMenu.this, "Vous n'avez pas les permissions requise ", LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
