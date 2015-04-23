package org.licenta.d4elders.model.outdated;
import javax.persistence.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cristiprg on 02.03.2015.
 */
@Entity
@Table(name = "nutrients_ideal_values", schema = "", catalog = "nutritioncareprocess")
@Deprecated
public class NutrientsIdealValues {
    private int id;
    private Double energyKcal;
    private Double fatG;
    private Double carbG;
    private Double satFatAcidG;
    private Double transFatAcidG;
    private Double proteinG;
    private Double potassiumG;
    private Double calciumG;
    private Double sodiumG;
    private Double vitaminDUg;
    private Double alcoholG;
    private Double waterG;

    @Transient
    private Map<String, Double> nutrientsIdealValuesMap = null;

    @Transient
    public Map<String, Double> getNutrientsIdealValuesMap() {
        return nutrientsIdealValuesMap;
    }

    public NutrientsIdealValues()
    {
    }

    public void populateMap()
    {
        nutrientsIdealValuesMap = new HashMap<String, Double>();
        nutrientsIdealValuesMap.put("energy_kcal", energyKcal);
        nutrientsIdealValuesMap.put("fatG", fatG);
        nutrientsIdealValuesMap.put("carbG", carbG);
        nutrientsIdealValuesMap.put("satFatAcidG", satFatAcidG);
        nutrientsIdealValuesMap.put("transFatAcidG", transFatAcidG);
        nutrientsIdealValuesMap.put("proteinG", proteinG);
        nutrientsIdealValuesMap.put("potassiumG", potassiumG);
        nutrientsIdealValuesMap.put("calciumG", calciumG);
        nutrientsIdealValuesMap.put("sodiumG", sodiumG);
        nutrientsIdealValuesMap.put("vitaminDUg", vitaminDUg);
        nutrientsIdealValuesMap.put("alcoholG", alcoholG);
        nutrientsIdealValuesMap.put("waterG", waterG);
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "potassium_g")
    public Double getPotassiumG() {
        return potassiumG;
    }

    public void setPotassiumG(Double potassiumG) {
        this.potassiumG = potassiumG;
    }

    @Basic
    @Column(name = "calcium_g")
    public Double getCalciumG() {
        return calciumG;
    }

    public void setCalciumG(Double calciumG) {
        this.calciumG = calciumG;
    }

    @Basic
    @Column(name = "sodium_g")
    public Double getSodiumG() {
        return sodiumG;
    }

    public void setSodiumG(Double sodiumG) {
        this.sodiumG = sodiumG;
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

        NutrientsIdealValues that = (NutrientsIdealValues) o;

        if (id != that.id) return false;
        if (alcoholG != null ? !alcoholG.equals(that.alcoholG) : that.alcoholG != null) return false;
        if (calciumG != null ? !calciumG.equals(that.calciumG) : that.calciumG != null) return false;
        if (carbG != null ? !carbG.equals(that.carbG) : that.carbG != null) return false;
        if (energyKcal != null ? !energyKcal.equals(that.energyKcal) : that.energyKcal != null) return false;
        if (fatG != null ? !fatG.equals(that.fatG) : that.fatG != null) return false;
        if (potassiumG != null ? !potassiumG.equals(that.potassiumG) : that.potassiumG != null) return false;
        if (proteinG != null ? !proteinG.equals(that.proteinG) : that.proteinG != null) return false;
        if (satFatAcidG != null ? !satFatAcidG.equals(that.satFatAcidG) : that.satFatAcidG != null) return false;
        if (sodiumG != null ? !sodiumG.equals(that.sodiumG) : that.sodiumG != null) return false;
        if (transFatAcidG != null ? !transFatAcidG.equals(that.transFatAcidG) : that.transFatAcidG != null)
            return false;
        if (vitaminDUg != null ? !vitaminDUg.equals(that.vitaminDUg) : that.vitaminDUg != null) return false;
        if (waterG != null ? !waterG.equals(that.waterG) : that.waterG != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (energyKcal != null ? energyKcal.hashCode() : 0);
        result = 31 * result + (fatG != null ? fatG.hashCode() : 0);
        result = 31 * result + (carbG != null ? carbG.hashCode() : 0);
        result = 31 * result + (satFatAcidG != null ? satFatAcidG.hashCode() : 0);
        result = 31 * result + (transFatAcidG != null ? transFatAcidG.hashCode() : 0);
        result = 31 * result + (proteinG != null ? proteinG.hashCode() : 0);
        result = 31 * result + (potassiumG != null ? potassiumG.hashCode() : 0);
        result = 31 * result + (calciumG != null ? calciumG.hashCode() : 0);
        result = 31 * result + (sodiumG != null ? sodiumG.hashCode() : 0);
        result = 31 * result + (vitaminDUg != null ? vitaminDUg.hashCode() : 0);
        result = 31 * result + (alcoholG != null ? alcoholG.hashCode() : 0);
        result = 31 * result + (waterG != null ? waterG.hashCode() : 0);
        return result;
    }


    @Override
    public String toString()
    {
        /*return "energyKcal = " + energyKcal + "\n" +
                "fatG = " + fatG + "\n" +
                "carbG = " + carbG + "\n" +
                "satFatAcidG = " + satFatAcidG + "\n" +
                "transFatAcidG = " + transFatAcidG + "\n" +
                "proteinG = " + proteinG + "\n" +
                "potassiumG = " + potassiumG + "\n" +
                "calciumG = " + calciumG + "\n" +
                "sodiumG = " + sodiumG + "\n" +
                "vitaminDug = " + vitaminDUg + "\n" +
                "alcoholG = " + alcoholG + "\n" +
                "waterG = " + waterG + "\n";*/

        /*String s = "";
        for(Map.Entry<String, Double> nutritent : this.nutrientsIdealValuesMap.entrySet())
        {
            s += nutritent.getKey() + " = " + nutritent.getValue() + "\n";
        }
        return s;
        */
        return MapHelper.mapToString(this.nutrientsIdealValuesMap);
    }
}
