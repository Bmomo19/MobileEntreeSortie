package gor.oda.eregistre.models;

import com.google.gson.annotations.SerializedName;


public class Academicien {

    @SerializedName("matricule")
    private String matricule;

    @SerializedName("nom")
    private String nom;

    @SerializedName("prenoms")
    private String prenoms;

    public String getMatricule() {
        return matricule;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenoms;
    }
}
