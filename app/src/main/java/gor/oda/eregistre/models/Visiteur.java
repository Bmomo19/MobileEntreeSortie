package gor.oda.eregistre.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Visiteur {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nom")
    @Expose
    private String nom;
    @SerializedName("prenoms")
    @Expose
    private String prenoms;
    @SerializedName("num_piece")
    @Expose
    private String numPiece;
    @SerializedName("type_piece")
    @Expose
    private String typePiece;
    @SerializedName("type_visiteur_id")
    @Expose
    private Integer typeVisiteurId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("type_visiteur")
    @Expose
    private TypeVisiteur typeVisiteur;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public String getNumPiece() {
        return numPiece;
    }

    public void setNumPiece(String numPiece) {
        this.numPiece = numPiece;
    }

    public String getTypePiece() {
        return typePiece;
    }

    public void setTypePiece(String typePiece) {
        this.typePiece = typePiece;
    }

    public Integer getTypeVisiteurId() {
        return typeVisiteurId;
    }

    public void setTypeVisiteurId(Integer typeVisiteurId) {
        this.typeVisiteurId = typeVisiteurId;
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

    public TypeVisiteur getTypeVisiteur() {
        return typeVisiteur;
    }

    public void setTypeVisiteur(TypeVisiteur typeVisiteur) {
        this.typeVisiteur = typeVisiteur;
    }

}
