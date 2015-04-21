package Meals.Model;

import java.util.*;

/**
 * 
 */
public class UserProfileStub {

    public UserProfileStub() {
        // Default value for PAF is 1.2
        setAge(27);
        setHeight(180);
        setWeight(79);
        setGender(GenderType.Male);
        setPAF(1.2);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getPAF() {
        return PAF;
    }

    public void setPAF(double PAF) {
        this.PAF = PAF;
    }

    public boolean isWeight_gain() {
        return weight_gain;
    }

    public void setWeight_gain(boolean weight_gain) {
        this.weight_gain = weight_gain;
    }

    public boolean isWeight_loss() {
        return weight_loss;
    }

    public void setWeight_loss(boolean weight_loss) {
        this.weight_loss = weight_loss;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    private int weight;

    private int height;

    private int age;

    private double PAF;

    private boolean weight_gain = false;

    private boolean weight_loss = false;

    private GenderType gender;

    @Override
    public String toString() {
        return "UserProfileStub{" +
                "weight=" + weight +
                ", height=" + height +
                ", age=" + age +
                ", PAF=" + PAF +
                ", weight_gain=" + weight_gain +
                ", weight_loss=" + weight_loss +
                ", gender=" + gender +
                '}';
    }
}