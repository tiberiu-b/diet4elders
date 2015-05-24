package org.licenta.d4elders.model.food_package;

import java.io.Serializable;

public class FoodProviderPackage  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int packageId;
	private Menu menu;
	private FoodServiceProvider foodProv;
	private double cost;
	private int deliveryTime;

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public FoodServiceProvider getFoodProv() {
		return foodProv;
	}

	public void setFoodProv(FoodServiceProvider foodProv) {
		this.foodProv = foodProv;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(int deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	
	public String toString() {
		return menu.toString()+ " Cost: "+cost+" DeliveryTime(min): "+deliveryTime;
	}

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}
}
