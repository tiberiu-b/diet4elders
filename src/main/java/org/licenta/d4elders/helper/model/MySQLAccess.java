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

	String url = "jdbc:mysql://localhost:3306/ndb_nal_usda";
	String user = "root";
	String password = "";

	public void readDataBase1() throws Exception {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager.getConnection("jdbc:mysql://localhost/feedback?"
					+ "user=sqluser&password=sqluserpw");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement.executeQuery("select * from feedback.comments");
			writeResultSet(resultSet);

			// PreparedStatements can use variables and are more efficient
			preparedStatement = connect
					.prepareStatement("insert into  feedback.comments values (default, ?, ?, ?, ? , ?, ?)");
			// "myuser, webpage, datum, summery, COMMENTS from feedback.comments");
			// Parameters start with 1
			preparedStatement.setString(1, "Test");
			preparedStatement.setString(2, "TestEmail");
			preparedStatement.setString(3, "TestWebpage");
			preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
			preparedStatement.setString(5, "TestSummary");
			preparedStatement.setString(6, "TestComment");
			preparedStatement.executeUpdate();

			preparedStatement = connect
					.prepareStatement("SELECT myuser, webpage, datum, summery, COMMENTS from feedback.comments");
			resultSet = preparedStatement.executeQuery();
			writeResultSet(resultSet);

			// Remove again the insert comment
			preparedStatement = connect.prepareStatement("delete from feedback.comments where myuser= ? ; ");
			preparedStatement.setString(1, "Test");
			preparedStatement.executeUpdate();

			resultSet = statement.executeQuery("select * from feedback.comments");
			writeMetaData(resultSet);

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	public void readDataBase() throws Exception {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager.getConnection(url, user, password);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement.executeQuery("select * from meal_type");
			writeResultSet(resultSet);

			// PreparedStatements can use variables and are more efficient
			// preparedStatement = connect
			// .prepareStatement("insert into  feedback.comments values (default, ?, ?, ?, ? , ?, ?)");
			// "myuser, webpage, datum, summery, COMMENTS from feedback.comments");
			// Parameters start with 1
			// preparedStatement.setString(1, "Test");
			// preparedStatement.setString(2, "TestEmail");
			// preparedStatement.setString(3, "TestWebpage");
			// preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
			// preparedStatement.setString(5, "TestSummary");
			// preparedStatement.setString(6, "TestComment");
			// preparedStatement.executeUpdate();

			// preparedStatement = connect
			// .prepareStatement("SELECT myuser, webpage, datum, summery, COMMENTS from feedback.comments");
			// resultSet = preparedStatement.executeQuery();
			// writeResultSet(resultSet);

			// Remove again the insert comment
			// preparedStatement =
			// connect.prepareStatement("delete from feedback.comments where myuser= ? ; ");
			// preparedStatement.setString(1, "Test");
			// preparedStatement.executeUpdate();

			// resultSet = statement.executeQuery("select * from feedback.comments");
			// writeMetaData(resultSet);

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	public ArrayList<String> getIdsOfAllNutrients() throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(url, user, password);

			statement = connect.createStatement();
			resultSet = statement.executeQuery("select nutr_no from nutrient_definition");
			while (resultSet.next()) {
				list.add(resultSet.getString("nutr_no"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

		return list;
	}

	public ArrayList<String> getIdsOfAllNutrientsOfIngredient(String ingredientId) throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(url, user, password);

			String query = "SELECT fdescr.ndb_no, ndef.nutr_no FROM food_descr AS fdescr,nutrient_data AS ndata,nutrient_definition AS ndef WHERE fdescr.ndb_no = ndata.ndb_no AND ndata.nutr_no = ndef.nutr_no AND fdescr.ndb_no = '"
					+ ingredientId + "'";

			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				list.add(resultSet.getString("nutr_no"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

		return list;
	}

	public ArrayList<Integer> getIdsOfAllRecipes() throws Exception {
		ArrayList<Integer> list = new ArrayList<Integer>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(url, user, password);

			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from recipe");
			while (resultSet.next()) {
				list.add(resultSet.getInt("recipeId"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

		return list;
	}

	public ArrayList<RecipeFoodRelation> getAllRecipeFoodEntities() {
		ArrayList<RecipeFoodRelation> list = new ArrayList<>();

		return list;
	}

	public ArrayList<RecipeFoodRelation> getARecipeFoodEntitiesByRecipeId(int recipeId) throws Exception {
		ArrayList<RecipeFoodRelation> list = new ArrayList<>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(url, user, password);

			String query = "SELECT rec_fr.ndb_no, w.seq, w.amount, w.gm_wgt, rec_fr.recipeId, rec_fr.foodInRecipeAmount FROM weight AS w,recipe AS rec, recipe_food_relation AS rec_fr WHERE w.ndb_no = rec_fr.ndb_no AND rec_fr.recipeId = "
					+ recipeId + " group by rec_fr.ndb_no";

			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				RecipeFoodRelation ent = new RecipeFoodRelation();
				ent.setAmount(resultSet.getFloat("amount"));
				ent.setFoodInRecipeAmount(resultSet.getFloat("foodInRecipeAmount"));
				ent.setGm_wgt(resultSet.getFloat("gm_wgt"));
				ent.setNdb_no(resultSet.getString("ndb_no"));
				ent.setRecipeId(recipeId);
				ent.setSeq(resultSet.getString("seq"));

				list.add(ent);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

		return list;
	}

	public ArrayList<String> getAllNutrientIdsUsedInARecipe(int recipeId) throws Exception {

		ArrayList<String> nutrientList = new ArrayList<>();

		ArrayList<RecipeFoodRelation> list = getARecipeFoodEntitiesByRecipeId(recipeId);

		for (RecipeFoodRelation ent : list) {
			ArrayList<String> l = getIdsOfAllNutrientsOfIngredient(ent.getNdb_no());
			for (String s : l) {
				if (!nutrientList.contains(s))
					nutrientList.add(s);
				else
					System.out.println("Good");

			}
		}

		return nutrientList;
	}

	public ArrayList<Nutrient> getAllNutrients(int recipeId, String ingredient) throws Exception {
		ArrayList<Nutrient> list = new ArrayList<>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(url, user, password);

			String query = "";

			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				RecipeFoodRelation ent = new RecipeFoodRelation();
				ent.setAmount(resultSet.getFloat("amount"));
				ent.setFoodInRecipeAmount(resultSet.getFloat("foodInRecipeAmount"));
				ent.setGm_wgt(resultSet.getFloat("gm_wgt"));
				ent.setNdb_no(resultSet.getString("ndb_no"));
				ent.setRecipeId(recipeId);
				ent.setSeq(resultSet.getString("seq"));

				// list.add(ent);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

		return list;

	}

	/**
	 * this method return the sum of nutrients of the ingredients with the recipeId and nutrientId
	 * from param
	 * 
	 * @param recipeId
	 * @param nutrientId
	 * @return
	 * @throws Exception
	 */
	public float getTotalNutrientValueOfAllIngredientOfARecipe(int recipeId, String nutrientId) throws Exception {

		float nutrValue = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(url, user, password);

			String query = "SELECT w.amount, w.gm_wgt, rec_fr.foodInRecipeAmount, ndata.nutr_val_100g FROM food_descr AS fdescr, weight AS w, nutrient_data AS ndata, nutrient_definition AS ndef, recipe AS rec, recipe_food_relation AS rec_fr WHERE w.ndb_no = fdescr.ndb_no AND fdescr.ndb_no = ndata.ndb_no AND ndata.nutr_no = ndef.nutr_no and fdescr.ndb_no = rec_fr.ndb_no and rec_fr.recipeId = rec.recipeId and rec_fr.seq = w.seq AND rec.recipeId = "
					+ recipeId + " and ndata.nutr_no = '" + nutrientId + "'";

			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {

				float amount = resultSet.getFloat("amount");
				float gm_wgt = resultSet.getFloat("gm_wgt");
				float foodInRecipeAmount = resultSet.getFloat("foodInRecipeAmount");
				float nutr_value_100g = resultSet.getFloat("nutr_val_100g");
				float weight = gm_wgt * foodInRecipeAmount / amount;
				nutrValue += weight * nutr_value_100g / 100.0;
				// System.out.println("Recipe" + recipeId + "  nutrient" + nutrientId);
				// System.out.println("Amount: " + amount + ",  Gm_wgt: " + gm_wgt +
				// ", FoodInRecipeAmount: "
				// + foodInRecipeAmount + ", Nutr_val_100g: " + nutr_value_100g);
				// System.out.println("  => Weight: " + weight + ", NutrientValue: " + nutrValue);
				// System.out.println("----------------------------------");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

		return nutrValue;
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
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

}
