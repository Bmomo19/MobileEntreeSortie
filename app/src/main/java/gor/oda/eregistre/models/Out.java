package gor.oda.eregistre.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Out {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("motif")
    @Expose
    private String motif;
    @SerializedName("date_arrive")
    @Expose
    private String dateArrive;
    @SerializedName("date_depart")
    @Expose
    private String dateDepart;
    @SerializedName("visiteur_id")
    @Expose
    private Integer visiteurId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("visiteur")
    @Expose
    private Visiteur visiteur;
    @SerializedName("user")
    @Expose
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getDateArrive() {
        return dateArrive;
    }

    public void setDateArrive(String dateArrive) {
        this.dateArrive = dateArrive;
    }

    public String getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(String dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Integer getVisiteurId() {
        return visiteurId;
    }

    public void setVisiteurId(Integer visiteurId) {
        this.visiteurId = visiteurId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Visiteur getVisiteur() {
        return visiteur;
    }

    public void setVisiteur(Visiteur visiteur) {
        this.visiteur = visiteur;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}