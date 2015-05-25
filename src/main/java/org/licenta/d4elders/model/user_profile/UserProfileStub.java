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

	private ArrayList<String> likeList = new ArrayList<String>();
	private ArrayList<String> dislikeList = new ArrayList<String>();
	private ArrayList<String> allergyList = new ArrayList<String>();

	public UserProfileStub() {
		generateMockupValues();
	}

	private void generateMockupValues() {
		// Default value for PAF is 1.2
		setAge(25);
		setHeight(180);
		setWeight(78);
		setGender(GenderType.Male);
		setPAF(1.7);

		getLikeList().add("Chocolate biscuits, full coated");
		getLikeList().add("Chocolate biscuits, cream filled, full coated");

		getDislikeList().add("Gingernut biscuits");
		getDislikeList().add("Oat based biscuits");

		// getAllergyList().add("butter");
		// getAllergyList().add("milk");
		// getAllergyList().add("apricot");

	}

	private void generateMockupValues2() {
		// Default value for PAF is 1.2
		setAge(27);
		setHeight(180);
		setWeight(79);
		setGender(GenderType.Male);
		setPAF(1.2);

		getLikeList().add("Chocolate biscuits, full coated");
		getLikeList().add("Chocolate biscuits, cream filled, full coated");

		getDislikeList().add("Gingernut biscuits");
		getDislikeList().add("Oat based biscuits");

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

	@Override
	public String toString() {
		return "UserProfileStub{" + "weight=" + weight + ", height=" + height + ", age=" + age + ", PAF=" + PAF
				+ ", weight_gain=" + weight_gain + ", weight_loss=" + weight_loss + ", gender=" + gender + '}';
	}

	public ArrayList<String> getLikeList() {
		return likeList;
	}

	public void setLikeList(ArrayList<String> likeList) {
		this.likeList = likeList;
	}

	public ArrayList<String> getDislikeList() {
		return dislikeList;
	}

	public void setDislikeList(ArrayList<String> dislikeList) {
		this.dislikeList = dislikeList;
	}

	public ArrayList<String> getAllergyList() {
		return allergyList;
	}

	public void setAllergyList(ArrayList<String> allergyList) {
		this.allergyList = allergyList;
	}

}