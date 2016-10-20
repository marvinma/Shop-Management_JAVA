package shop_management;

import java.sql.*;


public class TestingDatabaseConnectivity {
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	public void readDataBase() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop_management","root","root");

			statement = connect.createStatement();

			resultSet = statement.executeQuery("select * from items");
			writeResultSet(resultSet);
			System.out.println();
			System.out.println();
			writeMetaData(resultSet);

		} 
		catch (Exception e) {
			throw e;
		} 
		finally {
			close();
		}
	}

	private void writeMetaData(ResultSet resultSet) throws SQLException {
		System.out.println("The columns in the table are: ");

		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
			System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
		}
	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {

			int itemId = resultSet.getInt("item_id");
			String itemName = resultSet.getString("item_name");
			String itemCatagory = resultSet.getString("item_category");
			String itemAttribute = resultSet.getString("item_details");
			float itemPrice = resultSet.getFloat("item_Price");
			System.out.println("name: " + itemName);
			System.out.println("catagory: " + itemCatagory);
			System.out.println("price: " + itemPrice);
			System.out.println("id: " + itemId);
			System.out.println("attribute: " + itemAttribute);
		}
	}

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
	
	public static void main(String args[]) throws Exception{
		new TestingDatabaseConnectivity().readDataBase();;
	}

}