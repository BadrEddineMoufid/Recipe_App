package com.badr.recipe_app.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class searchRecipes implements Serializable {


    @SerializedName("results")
    @Expose
    private ArrayList<Result> results = null;
    @SerializedName("baseUri")
    @Expose
    private String baseUri;
    @SerializedName("offset")
    @Expose
    private int offset;
    @SerializedName("number")
    @Expose
    private int number;
    @SerializedName("totalResults")
    @Expose
    private int totalResults;
    @SerializedName("processingTimeMs")
    @Expose
    private int processingTimeMs;
    @SerializedName("expires")
    @Expose
    private long expires;



    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getProcessingTimeMs() {
        return processingTimeMs;
    }

    public void setProcessingTimeMs(int processingTimeMs) {
        this.processingTimeMs = processingTimeMs;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }

    @Override
    public String toString() {
        return "searchRecipes{" +
                "results=" + results +
                ", baseUri='" + baseUri + '\'' +
                ", offset=" + offset +
                ", number=" + number +
                ", totalResults=" + totalResults +
                ", processingTimeMs=" + processingTimeMs +
                ", expires=" + expires +
                '}';
    }


    public class Result {
        @Override
        public String toString() {
            return "Result{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", readyInMinutes=" + readyInMinutes +
                    ", servings=" + servings +
                    ", sourceUrl='" + sourceUrl + '\'' +
                    ", openLicense=" + openLicense +
                    ", image='" + image + '\'' +
                    '}';
        }

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("readyInMinutes")
        @Expose
        private int readyInMinutes;
        @SerializedName("servings")
        @Expose
        private int servings;
        @SerializedName("sourceUrl")
        @Expose
        private String sourceUrl;
        @SerializedName("openLicense")
        @Expose
        private int openLicense;
        @SerializedName("image")
        @Expose
        private String image;



        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getReadyInMinutes() {
            return readyInMinutes;
        }

        public void setReadyInMinutes(int readyInMinutes) {
            this.readyInMinutes = readyInMinutes;
        }

        public int getServings() {
            return servings;
        }

        public void setServings(int servings) {
            this.servings = servings;
        }

        public String getSourceUrl() {
            return sourceUrl;
        }

        public void setSourceUrl(String sourceUrl) {
            this.sourceUrl = sourceUrl;
        }

        public int getOpenLicense() {
            return openLicense;
        }

        public void setOpenLicense(int openLicense) {
            this.openLicense = openLicense;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }

}