package org.licenta.d4elders.model;

public class FoodServiceProvider {
	private int foodProviderId;
	private String name;
	private GeographicalArea geoArea;

	public FoodServiceProvider() {

	}

	public FoodServiceProvider(int foodProviderId, String name, GeographicalArea geoArea) {
		this.foodProviderId = foodProviderId;
		this.name = name;
		this.geoArea = geoArea;
	}

	public int getFoodProviderId() {
		return foodProviderId;
	}

	public void setFoodProviderId(int foodProviderId) {
		this.foodProviderId = foodProviderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GeographicalArea getGeoArea() {
		return geoArea;
	}

	public void setGeoArea(GeographicalArea geoArea) {
		this.geoArea = geoArea;
	}
}
