package org.licenta.d4elders.helper.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySQLAccess {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	// private String query1 =
	private String query1 = "select * from meal_recipe_relation as mrr join recipe as rec on mrr.recipeId=rec.recipeId join meal_type as mt on mt.mealId=mrr.mealId ORDER BY `rec`.`recipeId` ASC";
	private String query2 = "select * from dish";
	private String query3 = "select * from meal_variant";
	String url = "jdbc:mysql://localhost:3306/toateretetele";
	String user = "root";
	String password = "";

	public ResultSet getQueryResults(String query) {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager.getConnection(url, user, password);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement.executeQuery(query);
			return resultSet;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	public ArrayList<Dish> getAllDishes() {
		try {
			ArrayList<Dish> dishList = new ArrayList<Dish>();
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager.getConnection(url, user, password);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement.executeQuery(query1);
			int nrIt = 0;
			while (resultSet.next()) {
				nrIt++;
				Dish d = new Dish();
				d.dishTypeId = resultSet.getInt("dishType");
				d.mealTypeId = resultSet.getInt("mealId");
				d.recipeId = resultSet.getInt("recipeId");
				dishList.add(d);
			}
			System.out.println(nrIt);
			return dishList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	public ArrayList<Dish> getAllDishesFromDB() {
		try {
			ArrayList<Dish> dishList = new ArrayList<Dish>();
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager.getConnection(url, user, password);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement.executeQuery(query2);
			int nrIt = 0;
			while (resultSet.next()) {
				nrIt++;
				Dish d = new Dish();
				d.dishId = resultSet.getInt("dishId");
				d.dishTypeId = resultSet.getInt("dishTypeId");
				d.mealTypeId = resultSet.getInt("mealTypeId");
				d.recipeId = resultSet.getInt("recipeId");
				dishList.add(d);
			}
			System.out.println(nrIt);
			return dishList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	public ArrayList<MealVariant> getAllMealVariants() {
		try {
			ArrayList<MealVariant> mealVarList = new ArrayList<MealVariant>();
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager.getConnection(url, user, password);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement.executeQuery(query3);
			int nrIt = 0;
			while (resultSet.next()) {
				nrIt++;
				MealVariant mealVar = new MealVariant();
				mealVar.mealVariantId = resultSet.getInt("mealVariantId");
				mealVar.mealTypeId = resultSet.getInt("mealVariantId");
				mealVarList.add(mealVar);
			}
			System.out.println(nrIt);
			return mealVarList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	public ArrayList<FoodServiceProvider> getAllFoodServiceProviders() {
		try {
			ArrayList<FoodServiceProvider> foodProviderList = new ArrayList<FoodServiceProvider>();
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager.getConnection(url, user, password);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement.executeQuery(query3);
			int nrIt = 0;
			while (resultSet.next()) {
				nrIt++;
				FoodServiceProvider foodProvider = new FoodServiceProvider();
				foodProvider.foodProviderId = resultSet.getInt("foodProviderId");
				foodProvider.geogrAreaId = resultSet.getInt("geographicalAreaId");
				foodProviderList.add(foodProvider);
			}
			System.out.println(nrIt);
			return foodProviderList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	private void writeMetaData(ResultSet resultSet) throws SQLException {
		// Now get some metadata from the database
		// Result set get the result of the SQL query

		System.out.println("The columns in the table are: ");

		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i));
		}
	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			String user = resultSet.getString("mealId");
			String website = resultSet.getString("mealName");
			System.out.println("User: " + user);
			System.out.println("Website: " + website);
		}
	}

	private void writeResultSet1(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			String user = resultSet.getString("myuser");
			String website = resultSet.getString("webpage");
			String summery = resultSet.getString("summery");
			Date date = resultSet.getDate("datum");
			String comment = resultSet.getString("comments");
			System.out.println("User: " + user);
			System.out.println("Website: " + website);
			System.out.println("Summery: " + summery);
			System.out.println("Date: " + date);
			System.out.println("Comment: " + comment);
		}
	}

	// You need to close the resultSet
	private void close() {
		try {
			if (resultSet != null) {
				// resultSet.close();
			}

			if (statement != null) {
				// statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

}
