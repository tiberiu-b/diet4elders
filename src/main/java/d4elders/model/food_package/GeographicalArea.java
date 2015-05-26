package d4elders.model.food_package;

import java.io.Serializable;

public class GeographicalArea  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int geoAreaId;
	private String city;
	private String country;
	private String region;

	public GeographicalArea() {

	}

	public GeographicalArea(int geoAreaId, String country, String region, String city) {
		this.geoAreaId = geoAreaId;
		this.city = city;
		this.country = country;
		this.region = region;

	}

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
