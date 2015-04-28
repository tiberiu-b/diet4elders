package org.licenta.d4elders.model.user_profile;

import java.util.*;

/**
 * 
 */
public class UserProfileStub {

	private int weight;
	private int height;
	private int age;
	private double PAF;
	private boolean weight_gain = false;
	private boolean weight_loss = false;
	private GenderType gender;

	private Set<String> likeSet = new TreeSet<String>();
	private Set<String> dislikeSet = new TreeSet<String>();

	public UserProfileStub() {
		generateMockupValues();

	}

	private void generateMockupValues() {
		// Default value for PAF is 1.2
		setAge(27);
		setHeight(180);
		setWeight(79);
		setGender(GenderType.Male);
		setPAF(1.2);

		likeSet.add("Chocolate biscuits, full coated");
		likeSet.add("Chocolate biscuits, cream filled, full coated");

		dislikeSet.add("Gingernut biscuits");
		dislikeSet.add("Oat based biscuits");

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

	public Set<String> getDislikeSet() {
		return dislikeSet;
	}

	public void setDislikeSet(Set<String> dislikeSet) {
		this.dislikeSet = dislikeSet;
	}

	public Set<String> getLikeSet() {
		return likeSet;
	}

	public void setLikeSet(Set<String> likeSet) {
		this.likeSet = likeSet;
	}

	@Override
	public String toString() {
		return "UserProfileStub{" + "weight=" + weight + ", height=" + height + ", age=" + age + ", PAF=" + PAF
				+ ", weight_gain=" + weight_gain + ", weight_loss=" + weight_loss + ", gender=" + gender + '}';
	}
}