package gor.oda.eregistre.models;

import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("userId")
    int userId;

    @SerializedName("id")
    int id;

    @SerializedName("title")
    String title;

    @SerializedName("body")
    String body;

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
