package org.licenta.d4elders.model.outdated;

import java.util.Map;

/**
 * Created by cristiprg on 02.03.2015.
 */
public abstract class BasicMealOld {
	protected Map<String, Double> nutrientsValuesMap = null;

	public Map<String, Double> getNutrientsValuesMap() {
		return nutrientsValuesMap;
	}

	public abstract Map<String, Double> computeNutrientsValues();
}
