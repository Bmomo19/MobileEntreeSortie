package gor.oda.eregistre.models;

import com.google.gson.annotations.SerializedName;

public class Utilisateur {

    @SerializedName("id_user")
    private String id_user;
    @SerializedName("nom")
    private String nom;
    @SerializedName("prenoms")
    private String prenoms;
    @SerializedName("role")
    private String role;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    public String getNom() {
        return nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
