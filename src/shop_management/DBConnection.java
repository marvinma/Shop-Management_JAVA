/*
 * THIS CLASS CONTAINS DOES ALL THE OPERATIONS ON THE DATABASE.
 * 
 * An object of this class is provided to the Frames that need to do some database related operation.
 * 
 */

package shop_management;

import java.sql.*;
import java.text.SimpleDateFormat;


public class DBConnection {
	Connection con = null;
	Statement statement = null;
	ResultSet resultSet = null;
	
	public void connect() throws ClassNotFoundException, SQLException {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop_management","root","root");
	}
	
	//called from SHOPPING Frame to get the items from database to display in the Frame.
	public ResultSet getItems(int tableCat) throws SQLException, ClassNotFoundException{
		connect();
		statement = con.createStatement();
		resultSet = statement.executeQuery("SELECT item_id,	item_name, item_details, item_price FROM items"
				+ " WHERE item_category="+
				tableCat);
		
		return resultSet;
	}
	
	//called from CUST LOGIN Dialog to check the if the customer ID is legitimate.
	public ResultSet custLogin(int custID) throws SQLException, ClassNotFoundException{
		connect();
		statement = con.createStatement();
		resultSet = statement.executeQuery("SELECT * FROM customers WHERE cust_id = "+custID);
		return resultSet;
	}

	//called from PAYMENT Frame to insert the purchase record.
	public void insertPurchaseRecord(int paymentMethod) throws SQLException, ClassNotFoundException {
		connect();
		String sql = "INSERT INTO purchase (purchase_id, cust_id, items_sold, total_cost, payment_type, time_of_purchase) " +
				"Values (NULL" + 
				"," +
				Globals.CUSTOMER_ID+
				"," +
				Globals.NUMBER_OF_ITEMS+
				"," +
				Globals.BILL_AMOUNT+
				"," +
				paymentMethod+
				",CURRENT_TIMESTAMP)";

			statement = con.createStatement();
			statement.executeUpdate(sql);
			
	}
	
	//called from CustomerRegistration Frame to insert new customer.
		public Long insertNewCustomer(String name, Long contact) throws SQLException, ClassNotFoundException {
			connect();
			String sql = "INSERT INTO customers (cust_id, cust_name, cust_mobile) " +
					"Values (NULL" + 
					",'" +
					name +
					"'," +
					contact +
					")";
			String sqlForId = "SELECT LAST_INSERT_ID()";
				statement = con.createStatement();
				statement.executeUpdate(sql);
				resultSet = statement.executeQuery(sqlForId);
				resultSet.next();
				long newCustId =resultSet.getLong(1);
				
				return newCustId;
		}
		
	//called from PAYMENT Frame to update the loyalty points of customer after purchase.
	public void updatePoints(int points) throws SQLException, ClassNotFoundException {
		connect();
		String sql = "Update customers " +
		        "SET cust_points = "+
				points +
				" WHERE cust_id = "+
				Globals.CUSTOMER_ID
				;

			statement = con.createStatement();
			statement.executeUpdate(sql);
			
	}
	
	//called from PREV RECORDS Frame to get the purchase statistics.
	public String[] getStats(int custType)throws SQLException, ClassNotFoundException{
		connect();
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String timePeriod = " AND time_of_purchase >= '"
				+ sdf.format(Globals.START_DATE)
				+"' AND time_of_purchase <= '"
				+ sdf.format(Globals.END_DATE) 
				+"'";
		String timePeriodForAll = " WHERE time_of_purchase >= '"
				+ sdf.format(Globals.START_DATE)
				+"' AND time_of_purchase <= '"
				+ sdf.format(Globals.END_DATE) 
				+"'";
		
		String[] data = new String[10];
		
		//following is a if - else  block to check that the user is asking for whose Statistics
		//of registered customers
		//OR guest customers
		//OR all customers
		//OR a single registered customer.
		
		//To show the purchasing statistics of a single registered customer.
		if(custType == Globals.REGISTERED_CUST){
			statement = con.createStatement();
			resultSet = statement.executeQuery("SELECT count(*)  FROM purchase WHERE cust_id != 0" + timePeriod);
			resultSet.next();
			data[3] = "Total number of Purchases : \t"+ resultSet.getString(1);
			
			statement = con.createStatement();
			resultSet = statement.executeQuery("SELECT sum(items_sold)  FROM purchase WHERE cust_id != 0" + timePeriod);
			resultSet.next();
			data[4] = "Total number Items Sold : \t"+ resultSet.getString(1);
			
			statement = con.createStatement();
			resultSet = statement.executeQuery("SELECT sum(total_cost)  FROM purchase WHERE cust_id != 0" + timePeriod);
			resultSet.next();
			data[5] = "Total Amount Spent : \tRs "+ resultSet.getString(1);
		}
		
		//To show the purchasing statistics of a guest customer.
		else if (custType == Globals.GUEST_CUST){
			statement = con.createStatement();
			resultSet = statement.executeQuery("SELECT count(*)  FROM purchase WHERE cust_id = 0" + timePeriod);
			resultSet.next();
			data[3] = "Total number of Purchases : \t"+ resultSet.getString(1);
			
			statement = con.createStatement();
			resultSet = statement.executeQuery("SELECT sum(items_sold)  FROM purchase WHERE cust_id = 0" + timePeriod);
			resultSet.next();
			data[4] = "Total number Items Sold : \t"+ resultSet.getString(1);
			
			statement = con.createStatement();
			resultSet = statement.executeQuery("SELECT sum(total_cost) FROM purchase WHERE cust_id = 0" + timePeriod);
			resultSet.next();
			data[5] = "Total Amount Spent : \tRs "+ resultSet.getString(1);
		}
		
		//To show the purchasing statistics of all customers -- registered and guests.
		else if (custType == Globals.ALL_CUST){
			statement = con.createStatement();
			resultSet = statement.executeQuery("SELECT count(*)  FROM purchase" + timePeriodForAll);
			resultSet.next();
			data[3] = "Total number of Purchases : \t\t"+ resultSet.getString(1);
		
			statement = con.createStatement();
			resultSet = statement.executeQuery("SELECT sum(items_sold)  FROM purchase"  + timePeriodForAll);
			resultSet.next();
			data[4] = "Total number Items Sold : \t\t"+ resultSet.getString(1);
			
			statement = con.createStatement();
			resultSet = statement.executeQuery("SELECT count(*)  FROM purchase WHERE cust_id = 0" + timePeriod);
			resultSet.next();
			data[5] = "Purchases done by Guest Customers : \t"+ resultSet.getString(1);
			
			statement = con.createStatement();
			resultSet = statement.executeQuery("SELECT count(*)  FROM purchase WHERE cust_id != 0" + timePeriod);
			resultSet.next();
			data[6] = "Purchases done by Registered Customers : \t"+ resultSet.getString(1);
			
			statement = con.createStatement();
			resultSet = statement.executeQuery("SELECT sum(total_cost) FROM purchase"  + timePeriodForAll);
			resultSet.next();
			data[7] = "Total Amount Spent : \t\tRs "+ resultSet.getString(1);		
		}
		
		//To show the purchasing statistics of a single registered customer.
		//In this case the argument 'custType' will be containing the customer id of the registered customers
		else {
			statement = con.createStatement();
			resultSet = statement.executeQuery("SELECT cust_name, cust_mobile, cust_points FROM customers"
					+ " WHERE cust_id = "+
					custType);
			resultSet.next();
			data[0] = "Customer Name :    \t" + resultSet.getString(1);
			data[1] = "Customer Contact : \t" + resultSet.getString(2);
			data[2] = "Loyalty Points :   \t" + resultSet.getString(3);
			
			statement = con.createStatement();
			resultSet = statement.executeQuery("SELECT count(*)  FROM purchase WHERE cust_id = "+
					custType + timePeriod );
			resultSet.next();
			data[3] = "Total number of Purchases : \t"+ resultSet.getString(1);
			
			statement = con.createStatement();
			resultSet = statement.executeQuery("SELECT sum(items_sold)  FROM purchase WHERE cust_id = "+
					custType + timePeriod );
			resultSet.next();
			data[4] = "Total number Items Sold : \t"+ resultSet.getString(1);
			
			statement = con.createStatement();
			resultSet = statement.executeQuery("SELECT sum(total_cost) FROM purchase WHERE cust_id = "+
					custType + timePeriod );
			resultSet.next();
			data[5] = "Total Amount Spent : \tRs "+ resultSet.getString(1);
			
		}
		
		return data;
	}
	
	
	//called from PREV RECORDS Frame to get the previous purchase records.
	public ResultSet getRecords(int custType) throws SQLException, ClassNotFoundException{
		connect();
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timePeriod = " AND time_of_purchase >= '"
				+ sdf.format(Globals.START_DATE)
				+"' AND time_of_purchase <= '"
				+ sdf.format(Globals.END_DATE) 
				+"' ORDER BY time_of_purchase DESC";
		ResultSet resultSet;
		String query = new String();
		
		if(custType == Globals.REGISTERED_CUST)
			query = " AND purchase.cust_id != 0";
		else if (custType == Globals.GUEST_CUST)
			query = " AND purchase.cust_id = 0";
		else if (custType == Globals.ALL_CUST)
			query = "";
		else
			query = " AND purchase.cust_id =" + custType;
		
		statement = con.createStatement();
		resultSet = statement.executeQuery("SELECT purchase_id, cust_name, items_sold, total_cost, payment_type_name, time_of_purchase"
				+ "  FROM customers, purchase, payment_type"
				+ " WHERE customers.cust_id = purchase.cust_id"
				+ " AND purchase.payment_type = payment_type.payment_type_id"
				+ query + timePeriod);
		
		return resultSet;
	}
	
	//method to safely disconnect the connection with the database.
	public void disconnect() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			System.out.println("SUPER EXCEPTION");
			e.printStackTrace();
		}
	}
}