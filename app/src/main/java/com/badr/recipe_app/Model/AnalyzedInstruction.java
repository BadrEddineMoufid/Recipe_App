
package com.badr.recipe_app.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnalyzedInstruction {

    @SerializedName("name")
    @Expose
    private String name;

    @Override
    public String toString() {
        return "AnalyzedInstruction{" +
                "name='" + name + '\'' +
                ", steps=" + steps +
                '}';
    }

    @SerializedName("steps")
    @Expose
    private List<Step> steps = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

}
