package d4elders.dal;

import java.util.ArrayList;
import java.util.Random;

import d4elders.model.food_package.FoodProviderPackage;

public interface IBusinessLogic {

	public FoodProviderPackage generateSingleBreakfastPackages();

	public FoodProviderPackage generateSingleLunchPackages();

	public FoodProviderPackage generateSingleDinnerPackages();

	public FoodProviderPackage generateSingleSnackPackages();

	public ArrayList<FoodProviderPackage> generateBreakfastPackages(int numberOfSolutions);

	public ArrayList<FoodProviderPackage> generateLunchPackages(int numberOfSolutions);

	public ArrayList<FoodProviderPackage> generateDinnerPackages(int numberOfSolutions);

	public ArrayList<FoodProviderPackage> generateSnackPackages(int numberOfSolutions);

	public ArrayList<FoodProviderPackage> getAllBreakfastPackages();

	public ArrayList<FoodProviderPackage> getAllLunchPackages();

	public ArrayList<FoodProviderPackage> getAllDinnerPackages();

	public ArrayList<FoodProviderPackage> getAllSnackPackages();

}
