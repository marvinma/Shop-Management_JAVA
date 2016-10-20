/*
 * ###$$$*** THIS IS THE PAYMENT FRAME.
 * IT HAS TWO LAYOUT -- one for REGISTERED CUSTOMERS and one for GUEST CUSTOMERS
 * THE WINDOW LAYOUT IS SET ACCORDING TO THE CUSTOMER TYPE BEFORE DISPLAYING IT.
 */
package shop_management;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("serial")
public class Payment extends JFrame implements ActionListener{
	DBConnection dBConnection;
	
	private JTextField custNameTF;
	private JTextField custIdTF;
	private JTextField custContactTF;
	private JTextField pointsTF;
	private JTextField totalBillTF;
	private JTextField pointsUsedTF;
	private JTextField payAmountTF;
	
	JRadioButton payCashRB, payDebitRB, payCreditRB;
	
	JLabel pointsUsedL;
	JLabel totalBillL;
	JLabel warningL;
	
	Controller controller;
	
	ButtonGroup payMethodBG;
	
	ResultSet resultSet;
	
	JPanel custInfoP, guestInfoP, billP, custInfoP1, custInfoP2, payMethodP;
	JButton payB;
	

	public Payment(Controller controller, DBConnection dBConnection) {
		this.controller = controller;
		this.dBConnection = dBConnection;
		
		getContentPane().setBackground(Color.LIGHT_GRAY);
        setResizable(true);
        setTitle("Payment");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		
		setSize(dim.width, dim.height);
		
		
		setExtendedState(MAXIMIZED_BOTH );
		getContentPane().setLayout(null);
		
		custInfoP = new JPanel();
		custInfoP.setBounds(270, 67, 810, 87);
		custInfoP.setLayout(new GridLayout(0, 2, 0, 0));

		guestInfoP = new JPanel();
		FlowLayout flowLayout = (FlowLayout) guestInfoP.getLayout();
		flowLayout.setVgap(35);
		guestInfoP.setBounds(270, 67, 810, 87);
		
		getContentPane().add(guestInfoP);
		
		JLabel lblNewLabel = new JLabel("GUEST CUSTOMER");
		guestInfoP.add(lblNewLabel);
		
		custInfoP1 = new JPanel();
		custInfoP.add(custInfoP1);
		custInfoP1.setLayout(new GridLayout(2, 2, 0, 10));
		
		JLabel custNameL = new JLabel("Customer Name");
		custNameL.setHorizontalAlignment(SwingConstants.CENTER);
		custInfoP1.add(custNameL);
		
		custNameTF = new JTextField();
		custNameTF.setEditable(false);
		custInfoP1.add(custNameTF);
		custNameTF.setColumns(10);
		
		JLabel custContactL = new JLabel("Customer Contact");
		custContactL.setHorizontalAlignment(SwingConstants.CENTER);
		custInfoP1.add(custContactL);
		
		custContactTF = new JTextField();
		custContactTF.setEditable(false);
		custInfoP1.add(custContactTF);
		custContactTF.setColumns(10);
		
		custInfoP2 = new JPanel();
		custInfoP.add(custInfoP2);
		custInfoP2.setLayout(new GridLayout(2, 2, 0, 10));
		
		JLabel custIdL = new JLabel("Customer ID");
		custIdL.setHorizontalAlignment(SwingConstants.CENTER);
		custInfoP2.add(custIdL);
		
		custIdTF = new JTextField();
		custIdTF.setEditable(false);
		custInfoP2.add(custIdTF);
		custIdTF.setColumns(10);
		
		JLabel pointsL = new JLabel("Loyalty Points");
		pointsL.setHorizontalAlignment(SwingConstants.CENTER);
		custInfoP2.add(pointsL);
		
		pointsTF = new JTextField();
		pointsTF.setEditable(false);
		custInfoP2.add(pointsTF);
		pointsTF.setColumns(10);
		
		getContentPane().add(custInfoP);
		
		billP = new JPanel();
		billP.setBounds(680, 229, 400, 130);
		getContentPane().add(billP);
		billP.setLayout(new GridLayout(0, 2, 0, 10));
		
		totalBillL = new JLabel("Total Bill                            Rs.");
		totalBillL.setHorizontalAlignment(SwingConstants.CENTER);
		billP.add(totalBillL);
		
		totalBillTF = new JTextField();
		totalBillTF.setEditable(false);
		billP.add(totalBillTF);
		totalBillTF.setColumns(10);
		
		pointsUsedL = new JLabel("         Loyalty Points Used");
		pointsUsedL.setHorizontalAlignment(SwingConstants.LEFT);
		billP.add(pointsUsedL);
		
		pointsUsedTF = new JTextField();
		billP.add(pointsUsedTF);
		pointsUsedTF.setColumns(10);
		
		//adds a document listener to the textfield that generates event at any change insert or delete operation in the textfield.
		//and those function can be overridden to perform any operation
		//in this case that is to call updateFields() METHOS.
		pointsUsedTF.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateFields();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateFields();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateFields();
			}
		}
		);
		
		JLabel payAmountL = new JLabel("Payable Amount               Rs.");
		payAmountL.setHorizontalAlignment(SwingConstants.CENTER);
		billP.add(payAmountL);
		
		payAmountTF = new JTextField();
		payAmountTF.setEditable(false);
		payAmountTF.setColumns(10);
		billP.add(payAmountTF);
		
		payMethodP = new JPanel();
		payMethodP.setBounds(270, 229, 400, 130);
		getContentPane().add(payMethodP);
		payMethodP.setLayout(new GridLayout(3, 1, 0, 0));
		
		payMethodBG = new ButtonGroup();
		
		payCashRB = new JRadioButton("Payment By Cash");
		payCashRB.setHorizontalAlignment(SwingConstants.LEFT);
		payMethodP.add(payCashRB);
		
		payDebitRB = new JRadioButton("Payment By Debit Card");
		payDebitRB.setHorizontalAlignment(SwingConstants.LEFT);
		payMethodP.add(payDebitRB);
		
		payCreditRB = new JRadioButton("Payment By Credit Card");
		payCreditRB.setHorizontalAlignment(SwingConstants.LEFT);
		payMethodP.add(payCreditRB);
		
		payMethodBG.add(payCashRB);
		payMethodBG.add(payDebitRB);
		payMethodBG.add(payCreditRB);
				
		payB = new JButton("Pay");
		payB.setBounds(930, 427, 150, 40);
		payB.addActionListener(this);
		getContentPane().add(payB);
		
		warningL = new JLabel("");
		warningL.setForeground(Color.RED);
		warningL.setBounds(270, 427, 400, 14);
		getContentPane().add(warningL);
	}
	
	// METHOD TO SET THE LAYOUT OF THE PAGE ACCORDING TO THE CUSTOMER TYPE WHICH IS SUPPLIED IN THE ARGUMENT.
	void setLoginType(boolean loginType) throws SQLException, ClassNotFoundException {
		totalBillTF.setText(""+ Globals.BILL_AMOUNT);
		payAmountTF.setText(""+ Globals.BILL_AMOUNT);
		pointsUsedTF.setText("");
		if(loginType == Globals.CUST_LOGIN){
			
			resultSet = dBConnection.custLogin(Globals.CUSTOMER_ID);
			setCustInfo();
			guestInfoP.setVisible(false);
			custInfoP.setVisible(true);
			billP.add(totalBillL,0);
			billP.add(totalBillTF,1);
			billP.add(pointsUsedL, 2);
			billP.add(pointsUsedTF, 3);
			payCashRB.setSelected(true);
			
			dBConnection.disconnect();
		}
		else if(loginType == Globals.GUEST_LOGIN){
			billP.remove(totalBillL);
			billP.remove(totalBillTF);
			billP.remove(pointsUsedTF);
			billP.remove(pointsUsedL);
			custInfoP.setVisible(false);
			guestInfoP.setVisible(true);
			payCashRB.setSelected(true);
		}
	}
	
	//METHOD TO RESET THE COMPONENTS OF THE PAGE FOR NEW PURCHASE.
	void reset(){
		custNameTF.setText("");
		custIdTF.setText("");
		custContactTF.setText("");
		pointsTF.setText("");
		pointsUsedTF.setText("");
		totalBillTF.setText("");
		payAmountTF.setText("");
		payCashRB.setSelected(true);
		warningL.setText("");
	}

	//METHOD FOR DISPLAYING THE CUSTOMER INFO IF THE CUSTOMER TYPE IS REGISTERED.
	public void setCustInfo() throws SQLException{
		resultSet.next();
		custNameTF.setText(resultSet.getString("cust_name"));
		custIdTF.setText(resultSet.getString("cust_id"));
		custContactTF.setText(resultSet.getString("cust_mobile"));
		pointsTF.setText(resultSet.getString("cust_points"));
		Globals.POINTS = resultSet.getInt("cust_points");
		
	}
	
	//method to show that how much POINTS of user will be left and BILL AMOUNT on the basis amount of loyalty points
	//that customer choose to utilize in the current purchase.
	public void updateFields(){
		
		int pointsUsed = 0;
		
		try{
			pointsUsed = Integer.parseInt(pointsUsedTF.getText());
		
			if(pointsUsed > Globals.POINTS){
				warningL.setText("NOT ENOUGH POINTS !!!");
				pointsTF.setText("" + Globals.POINTS);
				payAmountTF.setText("" + Globals.BILL_AMOUNT);
				payB.setEnabled(false);
			}
			else if(Globals.BILL_AMOUNT < pointsUsed){
					warningL.setText("CAN NOT USE POINTS MORE THAN BILL AMOUNT !!!");
					pointsTF.setText("" + Globals.POINTS);
					payAmountTF.setText("" + Globals.BILL_AMOUNT);
					payB.setEnabled(false);
			}
			else{
				pointsTF.setText("" + (Globals.POINTS - pointsUsed));
				payAmountTF.setText("" + (Globals.BILL_AMOUNT - pointsUsed));
				warningL.setText("");
				payB.setEnabled(true);
			}
		}
		catch(NumberFormatException e2){
			warningL.setText("ENTER INTEGER VALUE ONLY !!!");
			pointsTF.setText("" + Globals.POINTS);
			payAmountTF.setText("" + Globals.BILL_AMOUNT);
		}	
		
			
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == payB){
			int pointsUsed = 0;
			try {
				try {
					pointsUsed = Integer.parseInt(pointsUsedTF.getText());
				} catch (NumberFormatException e1) {
					pointsUsed = 0;
				}
				if(pointsUsed != 0){
					dBConnection.updatePoints(Globals.POINTS - pointsUsed); 
				}
				
				if(payCashRB.isSelected())
					dBConnection.insertPurchaseRecord(Globals.CASH);
				else if(payCreditRB.isSelected())
					dBConnection.insertPurchaseRecord(Globals.CREDIT);
				else
					dBConnection.insertPurchaseRecord(Globals.DEBIT);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(this, "Something is wrong with the database !!!", "DATABASE ERROR", JOptionPane.ERROR_MESSAGE);
			}
			catch (ClassNotFoundException e2) {
				JOptionPane.showMessageDialog(this, "Something is wrong with the database Driver !!!", "DATABASE ERROR", JOptionPane.ERROR_MESSAGE);
			}
			finally{
				dBConnection.disconnect();
			}
			JOptionPane.showMessageDialog(this, "Purchase successful", "DONE", JOptionPane.INFORMATION_MESSAGE);
			controller.nextFrame(this);
		}
	}
	

}
