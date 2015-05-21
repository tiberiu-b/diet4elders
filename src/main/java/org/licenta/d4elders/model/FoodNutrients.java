package org.licenta.d4elders.model;

import java.io.Serializable;

public class FoodNutrients implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Float proteins;
	protected Float lipids;
	protected Float carbohydrates;
	protected Float energy;
	protected Float calcium;
	protected Float iron;
	protected Float sodium;
	protected Float vitA;
	protected Float vitB;
	protected Float vitC;
	protected Float vitD;

	public Float getProteins() {
		return proteins;
	}

	public void setProteins(Float proteins) {
		this.proteins = proteins;
	}

	public Float getLipids() {
		return lipids;
	}

	public void setLipids(Float lipids) {
		this.lipids = lipids;
	}

	public Float getCarbohydrates() {
		return carbohydrates;
	}

	public void setCarbohydrates(Float carbohydrates) {
		this.carbohydrates = carbohydrates;
	}

	public Float getEnergy() {
		return energy;
	}

	public void setEnergy(Float energy) {
		this.energy = energy;
	}

	public Float getCalcium() {
		return calcium;
	}

	public void setCalcium(Float calcium) {
		this.calcium = calcium;
	}

	public Float getIron() {
		return iron;
	}

	public void setIron(Float iron) {
		this.iron = iron;
	}

	public Float getSodium() {
		return sodium;
	}

	public void setSodium(Float sodium) {
		this.sodium = sodium;
	}

	public Float getVitA() {
		return vitA;
	}

	public void setVitA(Float vitA) {
		this.vitA = vitA;
	}

	public Float getVitB() {
		return vitB;
	}

	public void setVitB(Float vitB) {
		this.vitB = vitB;
	}

	public Float getVitC() {
		return vitC;
	}

	public void setVitC(Float vitC) {
		this.vitC = vitC;
	}

	public Float getVitD() {
		return vitD;
	}

	public void setVitD(Float vitD) {
		this.vitD = vitD;
	}

}
