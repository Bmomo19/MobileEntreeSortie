package gor.oda.eregistre.models;

import com.google.gson.annotations.SerializedName;

public class Visitor {

    @SerializedName("matricule")
    private String matricule;

    @SerializedName("nom")
    private String nom;

    @SerializedName("prenoms")
    private String prenom;

    @SerializedName("type")
    private  String type;

    @SerializedName("motif")
    private String motif;

    @SerializedName("tel")
    private String contact;


    public Visitor(String matricule, String nom, String prenom, String type, String motif, String contact) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.type = type;
        this.motif = motif;
        this.contact = contact;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
