
package com.badr.recipe_app.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metric {
    @Override
    public String toString() {
        return "Metric{" +
                "amount=" + amount +
                ", unitShort='" + unitShort + '\'' +
                ", unitLong='" + unitLong + '\'' +
                '}';
    }

    @SerializedName("amount")
    @Expose
    private double amount;
    @SerializedName("unitShort")
    @Expose
    private String unitShort;
    @SerializedName("unitLong")
    @Expose
    private String unitLong;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnitShort() {
        return unitShort;
    }

    public void setUnitShort(String unitShort) {
        this.unitShort = unitShort;
    }

    public String getUnitLong() {
        return unitLong;
    }

    public void setUnitLong(String unitLong) {
        this.unitLong = unitLong;
    }

}
