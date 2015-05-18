package org.licenta.d4elders.model.meal;

import org.licenta.d4elders.obsolete.MainCourse;

public abstract class Meal {

	protected MainCourse mainCourse;

	public Meal() {

	}

	public Meal(MainCourse mainCourse) {
		this.mainCourse = mainCourse;
	}

	public MainCourse getMainCourse() {
		return mainCourse;
	}

	public void setMainCourse(MainCourse mainCourse) {
		this.mainCourse = mainCourse;
	}
}
