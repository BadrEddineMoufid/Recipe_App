
package com.badr.recipe_app.Model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipe implements Serializable {
    @Override
    public String toString() {
        return "Recipe{" +
                "vegetarian=" + vegetarian +
                ", vegan=" + vegan +
                ", glutenFree=" + glutenFree +
                ", dairyFree=" + dairyFree +
                ", veryHealthy=" + veryHealthy +
                ", cheap=" + cheap +
                ", veryPopular=" + veryPopular +
                ", sustainable=" + sustainable +
                ", weightWatcherSmartPoints=" + weightWatcherSmartPoints +
                ", gaps='" + gaps + '\'' +
                ", lowFodmap=" + lowFodmap +
                ", aggregateLikes=" + aggregateLikes +
                ", spoonacularScore=" + spoonacularScore +
                ", healthScore=" + healthScore +
                ", creditsText='" + creditsText + '\'' +
                ", license='" + license + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", pricePerServing=" + pricePerServing +
                ", extendedIngredients=" + extendedIngredients +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", readyInMinutes=" + readyInMinutes +
                ", servings=" + servings +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", image='" + image + '\'' +
                ", imageType='" + imageType + '\'' +
                ", summary='" + summary + '\'' +
                ", cuisines=" + cuisines +
                ", dishTypes=" + dishTypes +
                ", diets=" + diets +
                ", occasions=" + occasions +
                ", winePairing=" + winePairing +
                ", instructions='" + instructions + '\'' +
                ", analyzedInstructions=" + analyzedInstructions +
                ", originalId=" + originalId +
                ", spoonacularSourceUrl='" + spoonacularSourceUrl + '\'' +
                '}';
    }

    @SerializedName("vegetarian")
    @Expose
    private boolean vegetarian;
    @SerializedName("vegan")
    @Expose
    private boolean vegan;
    @SerializedName("glutenFree")
    @Expose
    private boolean glutenFree;
    @SerializedName("dairyFree")
    @Expose
    private boolean dairyFree;
    @SerializedName("veryHealthy")
    @Expose
    private boolean veryHealthy;
    @SerializedName("cheap")
    @Expose
    private boolean cheap;
    @SerializedName("veryPopular")
    @Expose
    private boolean veryPopular;
    @SerializedName("sustainable")
    @Expose
    private boolean sustainable;
    @SerializedName("weightWatcherSmartPoints")
    @Expose
    private int weightWatcherSmartPoints;
    @SerializedName("gaps")
    @Expose
    private String gaps;
    @SerializedName("lowFodmap")
    @Expose
    private boolean lowFodmap;
    @SerializedName("aggregateLikes")
    @Expose
    private int aggregateLikes;
    @SerializedName("spoonacularScore")
    @Expose
    private double spoonacularScore;
    @SerializedName("healthScore")
    @Expose
    private double healthScore;
    @SerializedName("creditsText")
    @Expose
    private String creditsText;
    @SerializedName("license")
    @Expose
    private String license;
    @SerializedName("sourceName")
    @Expose
    private String sourceName;
    @SerializedName("pricePerServing")
    @Expose
    private double pricePerServing;
    @SerializedName("extendedIngredients")
    @Expose
    private List<ExtendedIngredient> extendedIngredients = null;
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
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("imageType")
    @Expose
    private String imageType;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("cuisines")
    @Expose
    private List<Object> cuisines = null;
    @SerializedName("dishTypes")
    @Expose
    private List<Object> dishTypes = null;
    @SerializedName("diets")
    @Expose
    private List<String> diets = null;
    @SerializedName("occasions")
    @Expose
    private List<String> occasions = null;
    @SerializedName("winePairing")
    @Expose
    private WinePairing winePairing;
    @SerializedName("instructions")
    @Expose
    private String instructions;
    @SerializedName("analyzedInstructions")
    @Expose
    private List<AnalyzedInstruction> analyzedInstructions = null;
    @SerializedName("originalId")
    @Expose
    private Object originalId;
    @SerializedName("spoonacularSourceUrl")
    @Expose
    private String spoonacularSourceUrl;

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(boolean glutenFree) {
        this.glutenFree = glutenFree;
    }

    public boolean isDairyFree() {
        return dairyFree;
    }

    public void setDairyFree(boolean dairyFree) {
        this.dairyFree = dairyFree;
    }

    public boolean isVeryHealthy() {
        return veryHealthy;
    }

    public void setVeryHealthy(boolean veryHealthy) {
        this.veryHealthy = veryHealthy;
    }

    public boolean isCheap() {
        return cheap;
    }

    public void setCheap(boolean cheap) {
        this.cheap = cheap;
    }

    public boolean isVeryPopular() {
        return veryPopular;
    }

    public void setVeryPopular(boolean veryPopular) {
        this.veryPopular = veryPopular;
    }

    public boolean isSustainable() {
        return sustainable;
    }

    public void setSustainable(boolean sustainable) {
        this.sustainable = sustainable;
    }

    public int getWeightWatcherSmartPoints() {
        return weightWatcherSmartPoints;
    }

    public void setWeightWatcherSmartPoints(int weightWatcherSmartPoints) {
        this.weightWatcherSmartPoints = weightWatcherSmartPoints;
    }

    public String getGaps() {
        return gaps;
    }

    public void setGaps(String gaps) {
        this.gaps = gaps;
    }

    public boolean isLowFodmap() {
        return lowFodmap;
    }

    public void setLowFodmap(boolean lowFodmap) {
        this.lowFodmap = lowFodmap;
    }

    public int getAggregateLikes() {
        return aggregateLikes;
    }

    public void setAggregateLikes(int aggregateLikes) {
        this.aggregateLikes = aggregateLikes;
    }

    public double getSpoonacularScore() {
        return spoonacularScore;
    }

    public void setSpoonacularScore(double spoonacularScore) {
        this.spoonacularScore = spoonacularScore;
    }

    public double getHealthScore() {
        return healthScore;
    }

    public void setHealthScore(double healthScore) {
        this.healthScore = healthScore;
    }

    public String getCreditsText() {
        return creditsText;
    }

    public void setCreditsText(String creditsText) {
        this.creditsText = creditsText;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public double getPricePerServing() {
        return pricePerServing;
    }

    public void setPricePerServing(double pricePerServing) {
        this.pricePerServing = pricePerServing;
    }

    public List<ExtendedIngredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    public void setExtendedIngredients(List<ExtendedIngredient> extendedIngredients) {
        this.extendedIngredients = extendedIngredients;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Object> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<Object> cuisines) {
        this.cuisines = cuisines;
    }

    public List<Object> getDishTypes() {
        return dishTypes;
    }

    public void setDishTypes(List<Object> dishTypes) {
        this.dishTypes = dishTypes;
    }

    public List<String> getDiets() {
        return diets;
    }

    public void setDiets(List<String> diets) {
        this.diets = diets;
    }

    public List<String> getOccasions() {
        return occasions;
    }

    public void setOccasions(List<String> occasions) {
        this.occasions = occasions;
    }

    public WinePairing getWinePairing() {
        return winePairing;
    }

    public void setWinePairing(WinePairing winePairing) {
        this.winePairing = winePairing;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public List<AnalyzedInstruction> getAnalyzedInstructions() {
        return analyzedInstructions;
    }

    public void setAnalyzedInstructions(List<AnalyzedInstruction> analyzedInstructions) {
        this.analyzedInstructions = analyzedInstructions;
    }

    public Object getOriginalId() {
        return originalId;
    }

    public void setOriginalId(Object originalId) {
        this.originalId = originalId;
    }

    public String getSpoonacularSourceUrl() {
        return spoonacularSourceUrl;
    }

    public void setSpoonacularSourceUrl(String spoonacularSourceUrl) {
        this.spoonacularSourceUrl = spoonacularSourceUrl;
    }

}
