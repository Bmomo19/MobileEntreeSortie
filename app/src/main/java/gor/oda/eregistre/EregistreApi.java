package gor.oda.eregistre;

import java.util.List;

import gor.oda.eregistre.models.Historique;
import gor.oda.eregistre.models.Out;
import gor.oda.eregistre.models.User;
import gor.oda.eregistre.models.Visiteur;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface EregistreApi {

     @GET("in_out/")
     Call<List<Historique>> getHistorique();

//     @GET("visiteurs/{numero_piece}")
//     Call<Academicien>getAcademicien(@Path("numero_piece") String matricule);

     @GET("visiteurs/{num_piece}")
     Call<Visiteur>getVisitor(@Path("num_piece") String num_piece);

     @GET("auth/{login}/{password}")
     Call<User>getUser(@Path("login") String login,
                       @Path("password") String password);

    @Multipart
    @POST("visiteurs/store")
    Call<Visiteur>createVisitor(
            @Part("nom") RequestBody  nom,
            @Part("prenoms") RequestBody  prenoms,
            @Part("num_piece") RequestBody num_piece,
            @Part("type_piece") RequestBody type_piece,
            @Part("type_visiteur") RequestBody type_visiteur,
            @Part MultipartBody.Part piece);

    @Multipart
    @POST("in_out/in/{num_piece_visiteur}/{identifiant_user}")
    Call<ResponseBody>motif(
            @Path("num_piece_visiteur")  String num_piece_visiteur,
            @Path("identifiant_user")   String identifiant_user,
            @Part("motif") RequestBody motif);


    @GET("in_out/out/{num_piece_visiteur}")
    Call<Out>Sortie(@Path("num_piece_visiteur")  String num_piece_visiteur);

    @Multipart
    @POST("users/")
    Call<ResponseBody>createUser(
            @Part("nom") RequestBody  nom,
            @Part("prenoms") RequestBody  prenoms,
            @Part("tel") RequestBody telephone,
            @Part("role") RequestBody  role,
            @Part("login") RequestBody login,
            @Part("password") RequestBody password,
            @Part MultipartBody.Part photo);
}
