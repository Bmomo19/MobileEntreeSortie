package gor.oda.eregistre.controllers;


import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.widget.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import gor.oda.eregistre.Constant;
import gor.oda.eregistre.EregistreApi;
import gor.oda.eregistre.R;
import gor.oda.eregistre.models.Visiteur;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.Toast.*;

public class FormulaireVisiteurs extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText nom, prenoms, numPiece;
    private Button valider,addphoto;
    private ImageView photo;
    private static final int REQUEST_CODE_CAMERA=1;
    private String photoPath=null;
    Boolean CheckEditText;
    Spinner spinner;
    String type_v;
    String[] type_visiteurs={"Visiteurs","OCI","Academcien","Personnel"};
    private static final int CAMERA_PERMISSION = 100;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R. layout.activity_addvisitor);

        //checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION);

        valider = (Button)findViewById(R.id.valider);
        addphoto =(Button)findViewById(R.id.btnPhoto);

        photo=(ImageView)findViewById(R.id.photo) ;

        numPiece = (EditText)findViewById(R.id.numeroCarte);
        nom = (EditText)findViewById(R.id.nom);
        prenoms = (EditText)findViewById(R.id.prenom);
        photo = (ImageView)findViewById(R.id.photo);

        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        progressDialog = new ProgressDialog(FormulaireVisiteurs.this);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,type_visiteurs);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //takePhoto();
                checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION);

            }
        });


        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextIsEmptyOrNot();

                if (CheckEditText) {

                    creerVisitor();


                } else {

                    Toast.makeText(FormulaireVisiteurs.this, "Veuillez remplir tous les champs du formulaire.", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    private void checkPermission(String permission, int requestCode) {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), permission)==PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(FormulaireVisiteurs.this,new String[]{permission},requestCode);

        }else {

            takePhoto();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION){

            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(FormulaireVisiteurs.this,"Permissions accordés !", LENGTH_LONG).show();
            }else {
                Toast.makeText(FormulaireVisiteurs.this, "Vous n'avez pas les permissions requise ", LENGTH_LONG).show();
            }
        }
    }

    private void takePhoto(){
        Intent takePic= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePic.resolveActivity(getPackageManager()) !=null){

            File photoDir= getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                File photoFile=File.createTempFile("CNI",".jpg",photoDir);
                photoPath= photoFile.getAbsolutePath();

                Uri photoUri= FileProvider.getUriForFile(FormulaireVisiteurs.this,FormulaireVisiteurs.this.getApplicationContext().getPackageName()+".provider",photoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(takePic,REQUEST_CODE_CAMERA);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==REQUEST_CODE_CAMERA && resultCode==RESULT_OK){
            Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
            photo.setImageBitmap(bitmap);
        }

    }

    public void creerVisitor () {

        progressDialog.setMessage("Enregistrement en cours.. ");
        progressDialog.show();


        RequestBody name = RequestBody.create(MultipartBody.FORM,nom.getText().toString().trim());
        RequestBody prenom = RequestBody.create(MultipartBody.FORM,prenoms.getText().toString().trim());
        RequestBody numPiec = RequestBody.create(MultipartBody.FORM,numPiece.getText().toString().trim());
        RequestBody typeVisiteur = RequestBody.create(MultipartBody.FORM,"Visiteur");
        RequestBody t_piece = RequestBody.create(MultipartBody.FORM,"CNI");

        File file = new File(photoPath);
        RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"),file);
        MultipartBody.Part imageCarte=MultipartBody.Part.createFormData("piece",file.getName(),imageBody);

        //Toast.makeText(FormulaireVisiteurs.this,photoPath, LENGTH_LONG).show();

        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                      .baseUrl(Constant.BASE_URL)
                      .addConverterFactory(GsonConverterFactory.create())
                      .client(okHttpClient)
                      .build();

        EregistreApi eregistreApi = retrofit.create(EregistreApi.class);

       Call<Visiteur> call = eregistreApi.createVisitor(name,prenom,numPiec,t_piece,typeVisiteur,imageCarte);

        call.enqueue(new Callback<Visiteur>() {
            @Override
            public void onResponse(Call<Visiteur> call, Response<Visiteur> response) {
                if (response.body()!=null){

                    progressDialog.dismiss();

                    //makeText(FormulaireVisiteurs.this,response.body().toString(), LENGTH_LONG).show();

                    EditText editTextField;
                    editTextField = new EditText(getApplicationContext());
                    AlertDialog dialog = new AlertDialog.Builder(FormulaireVisiteurs.this)
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

                                    Call<ResponseBody> call = eregistreApi.motif(numPiece.getText().toString().trim(), Constant.id_user,objet);
                                    call.enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                                Toast.makeText(getApplicationContext(),"Visiteur enregistré avec succès",Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(FormulaireVisiteurs.this,MainMenu.class);
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

            @Override
            public void onFailure(Call<Visiteur> call, Throwable t) {
                progressDialog.dismiss();
                makeText(FormulaireVisiteurs.this,"Erreur pendant l'enregistrement ", LENGTH_LONG).show();
               Log.e("Erreur d'envoi", t.getMessage());

               // Intent intent = new Intent(FormulaireVisiteurs.this,MainMenu.class);
                //startActivity(intent);

            }
        });


    }



    public void CheckEditTextIsEmptyOrNot() {


        if (TextUtils.isEmpty(nom.getText().toString().trim()) || TextUtils.isEmpty(prenoms.getText().toString().trim())
                || TextUtils.isEmpty(numPiece.getText().toString().trim())) {

            CheckEditText = false;

        } else {

            CheckEditText = true;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
