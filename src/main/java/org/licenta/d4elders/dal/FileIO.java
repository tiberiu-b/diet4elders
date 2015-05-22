package org.licenta.d4elders.dal;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.licenta.d4elders.model.FoodProviderPackage;
import org.licenta.d4elders.model.FoodServiceProvider;
import org.licenta.d4elders.model.GeographicalArea;
import org.licenta.d4elders.model.MealVariant;
import org.licenta.d4elders.model.Menu;
import org.licenta.d4elders.model.Recipe;
import org.licenta.d4elders.model.dish.Dish;

public class FileIO {
	private String recipeCacheFile = "recipe.ser";
	private String dishCacheFile = "dish.ser";
	private String mealVariantCacheFile = "mealVariant.ser";
	private String foodProviderCacheFile = "foodProvider.ser";
	private String geoAreaCacheFile = "geoArea.ser";
	private String menuCacheFile = "menu.ser";
	private String packageCacheFile = "package.ser";

	public void serializeRecipe(ArrayList<Recipe> objList) {
		try {
			OutputStream file = new FileOutputStream(recipeCacheFile);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			try {
				output.writeObject(objList);
			} finally {
				output.close();
			}
		} catch (IOException ex) {
			fLogger.log(Level.SEVERE, "Cannot perform output.", ex);
		}
	}

	public void serializeDish(ArrayList<Dish> objList) {
		try {
			OutputStream file = new FileOutputStream(dishCacheFile);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			try {
				output.writeObject(objList);
			} finally {
				output.close();
			}
		} catch (IOException ex) {
			fLogger.log(Level.SEVERE, "Cannot perform output.", ex);
		}
	}

	public void serializeMealVariant(ArrayList<MealVariant> objList) {
		try {
			OutputStream file = new FileOutputStream(mealVariantCacheFile);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			try {
				output.writeObject(objList);
			} finally {
				output.close();
			}
		} catch (IOException ex) {
			fLogger.log(Level.SEVERE, "Cannot perform output.", ex);
		}
	}

	public void serializeFoodServiceProvider(ArrayList<FoodServiceProvider> objList) {
		try {
			OutputStream file = new FileOutputStream(foodProviderCacheFile);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			try {
				output.writeObject(objList);
			} finally {
				output.close();
			}
		} catch (IOException ex) {
			fLogger.log(Level.SEVERE, "Cannot perform output.", ex);
		}
	}

	public void serializeGeographicalArea(ArrayList<GeographicalArea> objList) {
		try {
			OutputStream file = new FileOutputStream(geoAreaCacheFile);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			try {
				output.writeObject(objList);
			} finally {
				output.close();
			}
		} catch (IOException ex) {
			fLogger.log(Level.SEVERE, "Cannot perform output.", ex);
		}
	}

	public void serializeMenu(ArrayList<Menu> objList) {
		try {
			OutputStream file = new FileOutputStream(menuCacheFile);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			try {
				output.writeObject(objList);
			} finally {
				output.close();
			}
		} catch (IOException ex) {
			fLogger.log(Level.SEVERE, "Cannot perform output.", ex);
		}
	}

	public void serializeFoodProviderPackage(ArrayList<FoodProviderPackage> objList) {
		try {
			OutputStream file = new FileOutputStream(packageCacheFile);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			try {
				output.writeObject(objList);
			} finally {
				output.close();
			}
		} catch (IOException ex) {
			fLogger.log(Level.SEVERE, "Cannot perform output.", ex);
		}
	}

	public ArrayList<Recipe> deserializeRecipe() {
		try {
			InputStream file = new FileInputStream(recipeCacheFile);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			try {
				ArrayList<Recipe> list = (ArrayList<Recipe>) input.readObject();
				return list;
			} finally {
				input.close();
			}
		} catch (ClassNotFoundException ex) {
			fLogger.log(Level.SEVERE, "Cannot perform input. Class not found.", ex);
		} catch (IOException ex) {
			fLogger.log(Level.SEVERE, "Cannot perform input.", ex);
		}
		return null;
	}

	public ArrayList<Dish> deserializeDish() {
		try {
			InputStream file = new FileInputStream(dishCacheFile);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			try {
				ArrayList<Dish> list = (ArrayList<Dish>) input.readObject();
				return list;
			} finally {
				input.close();
			}
		} catch (ClassNotFoundException ex) {
			fLogger.log(Level.SEVERE, "Cannot perform input. Class not found.", ex);
		} catch (IOException ex) {
			fLogger.log(Level.SEVERE, "Cannot perform input.", ex);
		}
		return null;
	}

	public ArrayList<MealVariant> deserializeMealVariant() {
		try {
			InputStream file = new FileInputStream(mealVariantCacheFile);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			try {
				ArrayList<MealVariant> list = (ArrayList<MealVariant>) input.readObject();
				return list;
			} finally {
				input.close();
			}
		} catch (ClassNotFoundException ex) {
			fLogger.log(Level.SEVERE, "Cannot perform input. Class not found.", ex);
		} catch (IOException ex) {
			fLogger.log(Level.SEVERE, "Cannot perform input.", ex);
		}
		return null;
	}

	public ArrayList<FoodServiceProvider> deserializeFoodServiceProvider() {
		try {
			InputStream file = new FileInputStream(foodProviderCacheFile);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			try {
				ArrayList<FoodServiceProvider> list = (ArrayList<FoodServiceProvider>) input.readObject();
				return list;
			} finally {
				input.close();
			}
		} catch (ClassNotFoundException ex) {
			fLogger.log(Level.SEVERE, "Cannot perform input. Class not found.", ex);
		} catch (IOException ex) {
			fLogger.log(Level.SEVERE, "Cannot perform input.", ex);
		}
		return null;
	}

	public ArrayList<GeographicalArea> deserializeGeographicalArea() {
		try {
			InputStream file = new FileInputStream(geoAreaCacheFile);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			try {
				ArrayList<GeographicalArea> list = (ArrayList<GeographicalArea>) input.readObject();
				return list;
			} finally {
				input.close();
			}
		} catch (ClassNotFoundException ex) {
			fLogger.log(Level.SEVERE, "Cannot perform input. Class not found.", ex);
		} catch (IOException ex) {
			fLogger.log(Level.SEVERE, "Cannot perform input.", ex);
		}
		return null;
	}

	public ArrayList<Menu> deserializeMenu() {
		try {
			InputStream file = new FileInputStream(menuCacheFile);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			try {
				ArrayList<Menu> list = (ArrayList<Menu>) input.readObject();
				return list;
			} finally {
				input.close();
			}
		} catch (ClassNotFoundException ex) {
			fLogger.log(Level.SEVERE, "Cannot perform input. Class not found.", ex);
		} catch (IOException ex) {
			fLogger.log(Level.SEVERE, "Cannot perform input.", ex);
		}
		return null;
	}

	public ArrayList<FoodProviderPackage> deserializeFoodProviderPackage() {
		try {
			InputStream file = new FileInputStream(packageCacheFile);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);
			try {
				ArrayList<FoodProviderPackage> list = (ArrayList<FoodProviderPackage>) input.readObject();
				return list;
			} finally {
				input.close();
			}
		} catch (ClassNotFoundException ex) {
			fLogger.log(Level.SEVERE, "Cannot perform input. Class not found.", ex);
		} catch (IOException ex) {
			fLogger.log(Level.SEVERE, "Cannot perform input.", ex);
		}
		return null;
	}

	private final Logger fLogger = Logger.getLogger(FileIO.class.getPackage().getName());

}
