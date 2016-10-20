/*
 * $$$$#### ENTRY POINT OF THE PROJECT.
 * 
 * Manages all other classes -- visual container classes and the Database Connection Class.
 * Controls the transition b/w all the visual components.
 * Provides the DataBase connection capability by passing the DBconnection object.
 * There is always one object of all the visual components that are maintained during a purchase and reseted for new purchase.
 * The method nextFrame() is overloaded to transition from one window to the next.
*/
package shop_management;

import java.sql.SQLException;

public class Controller {
	
	//Object of class DBConnection which provides capability to perform database operations.
	DBConnection dBConnection = new DBConnection();
	
	//Objects of Graphical Interface Classes.
	Welcome welcomeF = new Welcome(this);
	Shopping shoppingF = new Shopping(this, dBConnection);
	PreviousRecords prevRecordsF = new PreviousRecords(this, dBConnection);
	CustomerLogin custLoginF = new CustomerLogin(this, dBConnection);
	Payment paymentF = new Payment(this, dBConnection);
	CustomerRegistration custRegF = new CustomerRegistration(this, dBConnection);
	
	Controller(){
		//Makes visible the first Frame that is the WELCOME Frame.
		welcomeF.setVisible(true);
	}
	
	
	//transitions from WELCOME Frame to next Frame based on the Button Clicked -- SHOPPING or PREVIOUS RECORDS
	void nextFrame(Welcome welcomeF, int pageChoice){
		welcomeF.setVisible(false);
		if(pageChoice == Globals.SHOPPING_PAGE){
			shoppingF.setVisible(true);
			
			//to fill the shopping tables in the SHOPPING Frame before displaying the frame.
			shoppingF.setTables();
			
		}
		else if(pageChoice == Globals.PREV_RECORDS_PAGE){
			prevRecordsF.reset();
			prevRecordsF.setVisible(true);
		}
		else if(pageChoice == Globals.CUST_REG_PAGE){
			custRegF.setVisible(true);
		}
	}
	
	//makes the CUSTOMER LOGIN Dialog visible on top of the SHOPPING Frame.
	void nextFrame(Shopping shoppingF){
		custLoginF.reset();
		custLoginF.setVisible(true);
	}
	
	//transitions from CUSTOMER LOGIN Dialog to PAYMENT Frame.
	//prepares the layout of PAYMENT Frame based on the customer type using the member methods of  PAYMENT class.
	//and the makes the PAYMENT Frame visible.
	void nextFrame(CustomerLogin custLoginF, boolean loginType) throws SQLException, ClassNotFoundException {
		shoppingF.setVisible(false);
		custLoginF.setVisible(false);
		paymentF.reset();
		
			if(loginType == Globals.CUST_LOGIN){
				//prepares the layout of PAYMENT Frame for a registered customer
				paymentF.setLoginType(Globals.CUST_LOGIN);
			}
			else if(loginType == Globals.GUEST_LOGIN){
				//prepares the layout of PAYMENT Frame for guest customer
				paymentF.setLoginType(Globals.GUEST_LOGIN);
			}
		
		paymentF.setVisible(true);
	}
	
	//RESETS ALL THE GRAPHICAL OBJECTS AND GLOBAL VARIABLES AND TRANSITIONS TO WELCOME PAGE.
	void nextFrame(Payment paymentF){
		shoppingF.reset();
		custLoginF.reset();
		paymentF.reset();
		prevRecordsF.reset();
		Globals.reset();
		
		paymentF.setVisible(false);
		welcomeF.setVisible(true);		
	}

	
	//makes transition from PREVIOUS RECORDS Frame to WELCOME Frame
	void nextFrame(PreviousRecords prevRecordsF){
		prevRecordsF.setVisible(false);
		welcomeF.setVisible(true);
		
	}
	
	//makes transition from CUSTOMER REGISTRATION Frame to WELCOME Frame
		void nextFrame(CustomerRegistration custRegF){
			custRegF.setVisible(false);
			welcomeF.setVisible(true);
			
		}
	
	public static void main(String args[]){
		new Controller();
	}	
}
