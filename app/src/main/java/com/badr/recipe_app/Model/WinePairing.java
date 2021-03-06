
package com.badr.recipe_app.Model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WinePairing implements Serializable {
    @Override
    public String toString() {
        return "WinePairing{" +
                "pairedWines=" + pairedWines +
                ", pairingText='" + pairingText + '\'' +
                ", productMatches=" + productMatches +
                '}';
    }

    @SerializedName("pairedWines")
    @Expose
    private List<String> pairedWines = null;
    @SerializedName("pairingText")
    @Expose
    private String pairingText;
    @SerializedName("productMatches")
    @Expose
    private List<ProductMatch> productMatches = null;

    public List<String> getPairedWines() {
        return pairedWines;
    }

    public void setPairedWines(List<String> pairedWines) {
        this.pairedWines = pairedWines;
    }

    public String getPairingText() {
        return pairingText;
    }

    public void setPairingText(String pairingText) {
        this.pairingText = pairingText;
    }

    public List<ProductMatch> getProductMatches() {
        return productMatches;
    }

    public void setProductMatches(List<ProductMatch> productMatches) {
        this.productMatches = productMatches;
    }

}
