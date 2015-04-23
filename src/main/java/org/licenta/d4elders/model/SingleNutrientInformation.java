package org.licenta.d4elders.model;
import java.util.*;

/**
 * 
 */
public class SingleNutrientInformation {

    /**
     * 
     */
    public SingleNutrientInformation() {
    }

    public SingleNutrientInformation(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(Double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public Double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(Double upperLimit) {
        this.upperLimit = upperLimit;
    }

    public Double getFixedValue() {
        return fixedValue;
    }

    public void setFixedValue(Double fixedValue) {
        this.fixedValue = fixedValue;
    }

    public Double getTolerableUpperIntakeLevel() {
        return tolerableUpperIntakeLevel;
    }

    public void setTolerableUpperIntakeLevel(Double tolerableUpperIntakeLevel) {
        this.tolerableUpperIntakeLevel = tolerableUpperIntakeLevel;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * 
     */
    private String name = null;

    private Double lowerLimit = null;
    private Double upperLimit = null;
    private Double fixedValue = null;
    private Double tolerableUpperIntakeLevel = null;
    private Integer weight = 1;
}