package gor.oda.eregistre.controllers;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.widget.Toast.LENGTH_LONG;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private static final int CAMERA_PERMISSION = 100;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);

        checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION);


    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {

        Intent intent = new Intent(this,VerificationScan.class);
        intent.putExtra("code",rawResult.toString());
        startActivity(intent);

    }

    private void checkPermission(String permission, int requestCode) {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), permission)==PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(ScanActivity.this,new String[]{permission},requestCode);

        }else {


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION){

            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(ScanActivity.this,"Permissions accordés !", LENGTH_LONG).show();
            }else {
                Toast.makeText(ScanActivity.this, "Vous n'avez pas les permissions requise ", LENGTH_LONG).show();
            }
        }
    }

}