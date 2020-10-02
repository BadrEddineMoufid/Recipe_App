package com.badr.recipe_app.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class authResponse {
    @SerializedName("tokens")
    @Expose
    private Tokens tokens;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("error")
    @Expose
    private String error;

    public Tokens getTokens() {
        return tokens;
    }

    public void setTokens(Tokens tokens) {
        this.tokens = tokens;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
