/*
 * ###$$$*** THIS DIALOG BOX IS FOR CHOICE OF TYPE OF USER-- REGISTERED OR GUEST
 */

package shop_management;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.*;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

@SuppressWarnings("serial")
public class CustomerLogin extends JDialog implements ActionListener{
	private JTextField custIdTF;
	JButton guestCustB, okB;
	Controller controller;
	DBConnection dBConnection;
	JLabel warningL ;
	
	ResultSet resultSet;

	public CustomerLogin(Controller controller, DBConnection dBConnection) {
		this.controller = controller;
		this.dBConnection = dBConnection;
		
		setBounds(500, 200, 450, 250);
		setResizable(false);
		setTitle("Customer Login");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		getContentPane().setLayout(new GridLayout(3, 1));
		
		JPanel custIDP = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 45));
		getContentPane().add(custIDP);
		
		JLabel custIdL = new JLabel("Enter Customer ID");
		custIDP.add(custIdL);
		
		custIdTF = new JTextField(18);
		custIDP.add(custIdTF);
		
		//clicks the OK button on ENTER key press in customer ID textfield.
		custIdTF.addActionListener(new ActionListener(){

									@Override
									public void actionPerformed(ActionEvent e) {
										okB.doClick();						
									}
									
								  });
		
		okB = new JButton("OK");
		okB.addActionListener(this);
		custIDP.add(okB);
		
		warningL = new JLabel("", SwingConstants.CENTER);
		warningL.setForeground(Color.RED);
		getContentPane().add(warningL);
	
	
		JPanel buttonP = new JPanel(new FlowLayout());
		getContentPane().add(buttonP);
		
		guestCustB = new JButton("Guest Customer");
		guestCustB.addActionListener(this);
		guestCustB.setSize(130, 35);
		buttonP.add(guestCustB);
			
	}
		
	//method to reset dialog box components.
	void reset(){
		custIdTF.setText("");
		warningL.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == okB){
			
			//try catch block to check if entered ID is numeric.
			try {
				int id = Integer.parseInt(custIdTF.getText());
				//try catch for SQL Exception
				try {
					
					resultSet = dBConnection.custLogin(id);
					
					if(resultSet.isBeforeFirst()){
						Globals.CUSTOMER_ID = id;
						controller.nextFrame(this, Globals.CUST_LOGIN);
					}
					else{
						warningL.setText("NOT A VALID CUSTOMER ID !!!");
					}
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(this, "Something is wrong with the database !!!", "DATABASE ERROR", JOptionPane.ERROR_MESSAGE);
				} catch (ClassNotFoundException e2) {
					JOptionPane.showMessageDialog(this, "Something is wrong with the database Driver !!!", "DATABASE ERROR", JOptionPane.ERROR_MESSAGE);
				}
				finally{
					dBConnection.disconnect();
				}
			dBConnection.disconnect();
			}
			catch(NumberFormatException e3){
				warningL.setText("ENTER NUMERIC CUSTOMER ID !!!");
			}
			
			
		}
		else if(e.getSource() == guestCustB){
			try {
				controller.nextFrame(this, Globals.GUEST_LOGIN);
			} catch (SQLException e4) {
				JOptionPane.showMessageDialog(this, "Something is wrong with the database !!!", "DATABASE ERROR", JOptionPane.ERROR_MESSAGE);
			} catch (ClassNotFoundException e5) {
				JOptionPane.showMessageDialog(this, "Something is wrong with the database Driver !!!", "DATABASE ERROR", JOptionPane.ERROR_MESSAGE);
			}
			finally{
				dBConnection.disconnect();
			}
		}
	}
}
