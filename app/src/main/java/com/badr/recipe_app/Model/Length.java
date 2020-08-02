
package com.badr.recipe_app.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Length {
    @Override
    public String toString() {
        return "Length{" +
                "number=" + number +
                ", unit='" + unit + '\'' +
                '}';
    }

    @SerializedName("number")
    @Expose
    private int number;
    @SerializedName("unit")
    @Expose
    private String unit;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
