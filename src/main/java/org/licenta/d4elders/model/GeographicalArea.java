package org.licenta.d4elders.model;

public class GeographicalArea {
	private int geoAreaId;
	private String city;
	private String country;
	private String region;

	public int getGeoAreaId() {
		return geoAreaId;
	}

	public void setGeoAreaId(int geoAreaId) {
		this.geoAreaId = geoAreaId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
}
