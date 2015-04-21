package Meals.Model;

import javax.persistence.*;
import java.util.*;

/**
 * 
 */
@Entity
@Table(name = "food_properties", schema = "", catalog = "nutritioncareprocess")
public class FoodProperties {

    private String code;
    private String name;
    private String description;
    private String type;
    private String subtype;
    private Double energyKcal;
    private Double fatG;
    private Double carbG;
    private Double satFatAcidG;
    private Double transFatAcidG;
    private Double proteinG;
    private Double potassiumMg;
    private Double calciumMg;
    private Double sodiumMg;
    private Double vitaminDUg;
    private Double alcoholG;
    private Double waterG;

    @Transient
    private Map<String, Double> nutrientsIdealValuesMap = null;

    // TODO: MAAARE TODO .... trebuie o idee a standardiza numele nutrientilor
    public void populateMap()
    {
        /*
        nutrientsIdealValuesMap = new HashMap<String, Double>();
        nutrientsIdealValuesMap.put("energy_kcal", energyKcal);
        nutrientsIdealValuesMap.put("fatG", fatG);
        nutrientsIdealValuesMap.put("carbG", carbG);
        nutrientsIdealValuesMap.put("satFatAcidG", satFatAcidG);
        nutrientsIdealValuesMap.put("transFatAcidG", transFatAcidG);
        nutrientsIdealValuesMap.put("proteinG", proteinG);
        nutrientsIdealValuesMap.put("potassiumG", potassiumMg);
        nutrientsIdealValuesMap.put("calciumG", calciumMg);
        nutrientsIdealValuesMap.put("sodiumG", sodiumMg);
        nutrientsIdealValuesMap.put("vitaminDUg", vitaminDUg);
        nutrientsIdealValuesMap.put("alcoholG", alcoholG);
        nutrientsIdealValuesMap.put("waterG", waterG);
        */

        nutrientsIdealValuesMap = new HashMap<String, Double>();
        nutrientsIdealValuesMap.put("calories", energyKcal);
        nutrientsIdealValuesMap.put("fats", fatG);
        nutrientsIdealValuesMap.put("carbo", carbG);
        //nutrientsIdealValuesMap.put("satFatAcidG", satFatAcidG);
        //nutrientsIdealValuesMap.put("transFatAcidG", transFatAcidG);
        nutrientsIdealValuesMap.put("proteins", proteinG);
        //nutrientsIdealValuesMap.put("potassiumG", potassiumMg);
        nutrientsIdealValuesMap.put("calcium", calciumMg);
        nutrientsIdealValuesMap.put("sodium", sodiumMg);
        nutrientsIdealValuesMap.put("vitaminD", vitaminDUg);
        //nutrientsIdealValuesMap.put("alcoholG", alcoholG);
        //nutrientsIdealValuesMap.put("waterG", waterG);
    }

    @Transient
    public Map<String, Double> getNutrientsIdealValuesMap() {
        return nutrientsIdealValuesMap;
    }

    /**
     *
     */
    public FoodProperties() {
    }

    @Id
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "subtype")
    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    @Basic
    @Column(name = "energy_kcal")
    public Double getEnergyKcal() {
        return energyKcal;
    }

    public void setEnergyKcal(Double energyKcal) {
        this.energyKcal = energyKcal;
    }

    @Basic
    @Column(name = "fat_g")
    public Double getFatG() {
        return fatG;
    }

    public void setFatG(Double fatG) {
        this.fatG = fatG;
    }

    @Basic
    @Column(name = "carb_g")
    public Double getCarbG() {
        return carbG;
    }

    public void setCarbG(Double carbG) {
        this.carbG = carbG;
    }

    @Basic
    @Column(name = "satFatAcid_g")
    public Double getSatFatAcidG() {
        return satFatAcidG;
    }

    public void setSatFatAcidG(Double satFatAcidG) {
        this.satFatAcidG = satFatAcidG;
    }

    @Basic
    @Column(name = "transFatAcid_g")
    public Double getTransFatAcidG() {
        return transFatAcidG;
    }

    public void setTransFatAcidG(Double transFatAcidG) {
        this.transFatAcidG = transFatAcidG;
    }

    @Basic
    @Column(name = "protein_g")
    public Double getProteinG() {
        return proteinG;
    }

    public void setProteinG(Double proteinG) {
        this.proteinG = proteinG;
    }

    @Basic
    @Column(name = "potassium_mg")
    public Double getPotassiumMg() {
        return potassiumMg;
    }

    public void setPotassiumMg(Double potassiumMg) {
        this.potassiumMg = potassiumMg;
    }

    @Basic
    @Column(name = "calcium_mg")
    public Double getCalciumMg() {
        return calciumMg;
    }

    public void setCalciumMg(Double calciumMg) {
        this.calciumMg = calciumMg;
    }

    @Basic
    @Column(name = "sodium_mg")
    public Double getSodiumMg() {
        return sodiumMg;
    }

    public void setSodiumMg(Double sodiumMg) {
        this.sodiumMg = sodiumMg;
    }

    @Basic
    @Column(name = "vitaminD_ug")
    public Double getVitaminDUg() {
        return vitaminDUg;
    }

    public void setVitaminDUg(Double vitaminDUg) {
        this.vitaminDUg = vitaminDUg;
    }

    @Basic
    @Column(name = "alcohol_g")
    public Double getAlcoholG() {
        return alcoholG;
    }

    public void setAlcoholG(Double alcoholG) {
        this.alcoholG = alcoholG;
    }

    @Basic
    @Column(name = "water_g")
    public Double getWaterG() {
        return waterG;
    }

    public void setWaterG(Double waterG) {
        this.waterG = waterG;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FoodProperties that = (FoodProperties) o;

        if (alcoholG != null ? !alcoholG.equals(that.alcoholG) : that.alcoholG != null) return false;
        if (calciumMg != null ? !calciumMg.equals(that.calciumMg) : that.calciumMg != null) return false;
        if (carbG != null ? !carbG.equals(that.carbG) : that.carbG != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (energyKcal != null ? !energyKcal.equals(that.energyKcal) : that.energyKcal != null) return false;
        if (fatG != null ? !fatG.equals(that.fatG) : that.fatG != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (potassiumMg != null ? !potassiumMg.equals(that.potassiumMg) : that.potassiumMg != null) return false;
        if (proteinG != null ? !proteinG.equals(that.proteinG) : that.proteinG != null) return false;
        if (satFatAcidG != null ? !satFatAcidG.equals(that.satFatAcidG) : that.satFatAcidG != null) return false;
        if (sodiumMg != null ? !sodiumMg.equals(that.sodiumMg) : that.sodiumMg != null) return false;
        if (subtype != null ? !subtype.equals(that.subtype) : that.subtype != null) return false;
        if (transFatAcidG != null ? !transFatAcidG.equals(that.transFatAcidG) : that.transFatAcidG != null)
            return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (vitaminDUg != null ? !vitaminDUg.equals(that.vitaminDUg) : that.vitaminDUg != null) return false;
        if (waterG != null ? !waterG.equals(that.waterG) : that.waterG != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (subtype != null ? subtype.hashCode() : 0);
        result = 31 * result + (energyKcal != null ? energyKcal.hashCode() : 0);
        result = 31 * result + (fatG != null ? fatG.hashCode() : 0);
        result = 31 * result + (carbG != null ? carbG.hashCode() : 0);
        result = 31 * result + (satFatAcidG != null ? satFatAcidG.hashCode() : 0);
        result = 31 * result + (transFatAcidG != null ? transFatAcidG.hashCode() : 0);
        result = 31 * result + (proteinG != null ? proteinG.hashCode() : 0);
        result = 31 * result + (potassiumMg != null ? potassiumMg.hashCode() : 0);
        result = 31 * result + (calciumMg != null ? calciumMg.hashCode() : 0);
        result = 31 * result + (sodiumMg != null ? sodiumMg.hashCode() : 0);
        result = 31 * result + (vitaminDUg != null ? vitaminDUg.hashCode() : 0);
        result = 31 * result + (alcoholG != null ? alcoholG.hashCode() : 0);
        result = 31 * result + (waterG != null ? waterG.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return code + " " + name + " " + description + " " + type + " " + subtype + " " + energyKcal + " " +
                fatG + " " + carbG + " " + satFatAcidG + " " + transFatAcidG + " " + proteinG + " " +
                potassiumMg + " " + calciumMg + " " + sodiumMg + " " + vitaminDUg + " " + alcoholG + " " + waterG;
    }
}