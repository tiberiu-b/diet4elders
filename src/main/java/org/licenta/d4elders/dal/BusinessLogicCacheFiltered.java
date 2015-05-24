package org.licenta.d4elders.dal;

import java.util.ArrayList;
import java.util.Random;

import org.licenta.d4elders.model.food_package.FoodProviderPackage;

public class BusinessLogicCacheFiltered implements IBusinessLogic {
	private BusinessLogicCache bl;
	private int maxRuns;
	protected ArrayList<String> allergyList;

	public BusinessLogicCacheFiltered(ArrayList<String> allergyList) {
		bl = BusinessLogicCache.getInstance();
		this.allergyList = allergyList;
	}

	@Override
	public FoodProviderPackage generateSingleBreakfastPackages() {

		maxRuns = 100;
		while (maxRuns > 0) {
			maxRuns--;
			boolean packageValid = true;
			FoodProviderPackage p = bl.generateSingleBreakfastPackages();
			for (String ingr : p.getMenu().getIngredientList()) {
				for (String allergy : allergyList)
					if (ingr.contains(allergy))
						packageValid = false;
			}
			if (packageValid == true)
				return p;
		}
		return null;
	}

	@Override
	public FoodProviderPackage generateSingleLunchPackages() {
		maxRuns = 100;
		while (maxRuns > 0) {
			maxRuns--;
			boolean packageValid = true;
			FoodProviderPackage p = bl.generateSingleLunchPackages();
			for (String ingr : p.getMenu().getIngredientList()) {
				for (String allergy : allergyList)
					if (ingr.contains(allergy))
						packageValid = false;
			}
			if (packageValid == true)
				return p;
		}
		return null;
	}

	@Override
	public FoodProviderPackage generateSingleDinnerPackages() {
		maxRuns = 100;
		while (maxRuns > 0) {
			maxRuns--;
			boolean packageValid = true;
			FoodProviderPackage p = bl.generateSingleDinnerPackages();
			for (String ingr : p.getMenu().getIngredientList()) {
				for (String allergy : allergyList)
					if (ingr.contains(allergy))
						packageValid = false;
			}
			if (packageValid == true)
				return p;
		}
		return null;
	}

	@Override
	public FoodProviderPackage generateSingleSnackPackages() {
		maxRuns = 100;
		while (maxRuns > 0) {
			maxRuns--;
			boolean packageValid = true;
			FoodProviderPackage p = bl.generateSingleSnackPackages();
			for (String ingr : p.getMenu().getIngredientList()) {
				for (String allergy : allergyList)
					if (ingr.contains(allergy))
						packageValid = false;
			}
			if (packageValid == true)
				return p;
		}
		return null;
	}

	@Override
	public ArrayList<FoodProviderPackage> generateBreakfastPackages(int numberOfSolutions) {
		ArrayList<FoodProviderPackage> breakfastPackageList = new ArrayList<>();
		for (int i = 0; i < numberOfSolutions; i++) {
			maxRuns = 100;
			boolean packageFound = false;
			while (maxRuns > 0) {
				maxRuns--;
				boolean packageValid = true;
				FoodProviderPackage breakfast = generateSingleBreakfastPackages();
				for (FoodProviderPackage pkg : breakfastPackageList) {
					if (breakfast.getPackageId() == pkg.getPackageId()) {
						packageValid = false;
						break;
					}
				}
				if (packageValid) {
					breakfastPackageList.add(breakfast);
					packageFound = true;
					break;
				}
			}
			if (packageFound == false)
				return breakfastPackageList;
		}
		return breakfastPackageList;
	}

	@Override
	public ArrayList<FoodProviderPackage> generateLunchPackages(int numberOfSolutions) {
		ArrayList<FoodProviderPackage> lunchPackageList = new ArrayList<>();
		for (int i = 0; i < numberOfSolutions; i++) {
			maxRuns = 100;
			boolean packageFound = false;
			while (maxRuns > 0) {
				maxRuns--;
				boolean packageValid = true;
				FoodProviderPackage lunch = generateSingleLunchPackages();
				for (FoodProviderPackage pkg : lunchPackageList) {
					if (lunch.getPackageId() == pkg.getPackageId()) {
						packageValid = false;
						break;
					}
				}
				if (packageValid) {
					lunchPackageList.add(lunch);
					packageFound = true;
					break;
				}
			}
			if (packageFound == false)
				return lunchPackageList;
		}
		return lunchPackageList;
	}

	@Override
	public ArrayList<FoodProviderPackage> generateDinnerPackages(int numberOfSolutions) {
		ArrayList<FoodProviderPackage> dinnerPackageList = new ArrayList<>();
		for (int i = 0; i < numberOfSolutions; i++) {
			maxRuns = 100;
			boolean packageFound = false;
			while (maxRuns > 0) {
				maxRuns--;
				boolean packageValid = true;
				FoodProviderPackage dinner = generateSingleDinnerPackages();
				for (FoodProviderPackage pkg : dinnerPackageList) {
					if (dinner.getPackageId() == pkg.getPackageId()) {
						packageValid = false;
						break;
					}
				}
				if (packageValid) {
					dinnerPackageList.add(dinner);
					packageFound = true;
					break;
				}
			}
			if (packageFound == false)
				return dinnerPackageList;
		}
		return dinnerPackageList;
	}

	@Override
	public ArrayList<FoodProviderPackage> generateSnackPackages(int numberOfSolutions) {
		ArrayList<FoodProviderPackage> snackPackageList = new ArrayList<>();
		for (int i = 0; i < numberOfSolutions; i++) {
			maxRuns = 100;
			boolean packageFound = false;
			while (maxRuns > 0) {
				maxRuns--;
				boolean packageValid = true;
				FoodProviderPackage snack = generateSingleSnackPackages();
				for (FoodProviderPackage pkg : snackPackageList) {
					if (snack.getPackageId() == pkg.getPackageId()) {
						packageValid = false;
						break;
					}
				}
				if (packageValid) {
					snackPackageList.add(snack);
					packageFound = true;
					break;
				}
			}
			if (packageFound == false)
				return snackPackageList;
		}
		return snackPackageList;
	}

	@Override
	public ArrayList<FoodProviderPackage> getAllBreakfastPackages() {
		ArrayList<FoodProviderPackage> breakfastList = bl.getAllBreakfastPackages();
		ArrayList<FoodProviderPackage> breakfastListFiltered = new ArrayList<>();
		for (FoodProviderPackage p : breakfastList) {
			boolean packageValid = true;
			for (String ingr : p.getMenu().getIngredientList()) {
				for (String allergy : allergyList)
					if (ingr.contains(allergy))
						packageValid = false;
			}
			if (packageValid == true)
				breakfastListFiltered.add(p);

		}
		return breakfastListFiltered;
	}

	@Override
	public ArrayList<FoodProviderPackage> getAllLunchPackages() {
		ArrayList<FoodProviderPackage> lunchList = bl.getAllLunchPackages();
		ArrayList<FoodProviderPackage> lunchListFiltered = new ArrayList<>();
		for (FoodProviderPackage p : lunchList) {
			boolean packageValid = true;
			for (String ingr : p.getMenu().getIngredientList()) {
				for (String allergy : allergyList)
					if (ingr.contains(allergy))
						packageValid = false;
			}
			if (packageValid == true)
				lunchListFiltered.add(p);

		}
		return lunchListFiltered;
	}

	@Override
	public ArrayList<FoodProviderPackage> getAllDinnerPackages() {
		ArrayList<FoodProviderPackage> dinnerList = bl.getAllDinnerPackages();
		ArrayList<FoodProviderPackage> dinnerListFiltered = new ArrayList<>();
		for (FoodProviderPackage p : dinnerList) {
			boolean packageValid = true;
			for (String ingr : p.getMenu().getIngredientList()) {
				for (String allergy : allergyList)
					if (ingr.contains(allergy))
						packageValid = false;
			}
			if (packageValid == true)
				dinnerListFiltered.add(p);

		}
		return dinnerListFiltered;
	}

	@Override
	public ArrayList<FoodProviderPackage> getAllSnackPackages() {
		ArrayList<FoodProviderPackage> snackList = bl.getAllSnackPackages();
		ArrayList<FoodProviderPackage> snackListFiltered = new ArrayList<>();
		for (FoodProviderPackage p : snackList) {
			boolean packageValid = true;
			for (String ingr : p.getMenu().getIngredientList()) {
				for (String allergy : allergyList)
					if (ingr.contains(allergy))
						packageValid = false;
			}
			if (packageValid == true)
				snackListFiltered.add(p);

		}
		return snackListFiltered;
	}

}
