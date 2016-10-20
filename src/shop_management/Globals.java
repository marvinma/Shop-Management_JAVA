/*
 *###$$$**** this class contains the Global constants and Global variables.
 */
package shop_management;

import java.util.Date;

public class Globals {
	
	
	//####$$$$****  CONSTANTS
	
	//constants for customer type
	public static final boolean CUST_LOGIN = true;
	public static final boolean GUEST_LOGIN = false;
	
	//constants for choice of operation -- new purchase or previous records
	public static final int SHOPPING_PAGE = 1;
	public static final int PREV_RECORDS_PAGE = 2;
	public static final int CUST_REG_PAGE = 3;
	
	//constants for item category
	public static final int CLOTHING = 1;
	public static final int ELECTRONICS = 2;
	public static final int HEALTH_AND_FITNESS = 3;
	public static final int HOME_AND_DECOR = 4;
	
	//constants used for showing Previous Records
	public static final int REGISTERED_CUST = 0;
	public static final int GUEST_CUST = 1;
	public static final int ALL_CUST = 2;
	public static final int SINGLE_CUST = 3;
	
	//constants for type of payment
	public static final int CREDIT = 1;
	public static final int DEBIT = 2;
	public static final int CASH = 3;
	
	
	
	//####$$$$****  VARIABLES
	
	//variables for details of current purchase
	public static int CUSTOMER_ID;
	public static float BILL_AMOUNT;
	public static int POINTS;
	public static int NUMBER_OF_ITEMS;
	
	public static Date START_DATE;
	public static Date END_DATE;
	
	
	
	
	//reset function to reset the variables after the purchase is done
	public static void reset(){
		CUSTOMER_ID = 0;
		BILL_AMOUNT = (float) 0.0;
		POINTS = 0;
		NUMBER_OF_ITEMS = 0;
	}
}
