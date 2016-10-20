/*
 * ###$$$*** THIS IS THE FRAME TO VIEW PREVIOUS RECORDS FROM THE DATABASE.
 */
package shop_management;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.border.MatteBorder;;

@SuppressWarnings("serial")
public class PreviousRecords extends JFrame implements ActionListener, ItemListener{
	DBConnection dBConnection;
	Controller controller;
	private JTextField custIdTF;
	ButtonGroup choiceBG = new ButtonGroup();
	JComboBox<String> custTypeCB;
	JSpinner endDateS, startDateS;
	JPanel panel;
	JTextArea outputTA;
	JRadioButton recordsRB, statsRB;
	JButton backB, showRecordsB;
	
	public PreviousRecords(Controller controller, DBConnection dBConnection) {
        this.controller = controller;
        this.dBConnection = dBConnection;
        
		setResizable(true);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Purchase Records");
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		
		setSize(dim.width, dim.height);
		
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setExtendedState(MAXIMIZED_BOTH );
		getContentPane().setLayout(null);
		
		JLabel lbl1 = new JLabel("SELECT PREFERENCES FOR SHOWING RECORDS :");
		lbl1.setBounds(286, 106, 358, 14);
		getContentPane().add(lbl1);
		
		JPanel choiceP = new JPanel();
		choiceP.setBounds(286, 131, 800, 33);
		getContentPane().add(choiceP);
		choiceP.setLayout(new GridLayout(0, 2, 0, 0));
		
		statsRB = new JRadioButton("SALES STATISTICS");
		choiceP.add(statsRB);
		
		recordsRB = new JRadioButton("PURCHASE RECORDS");
		recordsRB.setHorizontalAlignment(SwingConstants.TRAILING);
		choiceP.add(recordsRB);
		
		choiceBG.add(statsRB);
		choiceBG.add(recordsRB);
		
		showRecordsB = new JButton("Show Records");
		showRecordsB.setBounds(876, 336, 150, 40);
		showRecordsB.addActionListener(this);
		getContentPane().add(showRecordsB);
		
		JLabel lbl2 = new JLabel("SHOW RECORDS FOR:");
		lbl2.setBounds(286, 200, 233, 14);
		getContentPane().add(lbl2);
		
		JPanel chooseTimePeriodP = new JPanel();
		chooseTimePeriodP.setBounds(599, 225, 487, 100);
		getContentPane().add(chooseTimePeriodP);
		chooseTimePeriodP.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
		
		startDateS = new JSpinner();
		chooseTimePeriodP.add(startDateS);
		
		JLabel lbl5 = new JLabel("                    TO                    ");
		chooseTimePeriodP.add(lbl5);
		
		endDateS = new JSpinner();
		chooseTimePeriodP.add(endDateS);
		
		JPanel chooseCustTypeP = new JPanel();
		chooseCustTypeP.setBounds(286, 225, 293, 100);
		getContentPane().add(chooseCustTypeP);
		
		custIdTF = new JTextField();
		custIdTF.setEnabled(false);
		custIdTF.setBounds(153, 60, 102, 20);
		custIdTF.setColumns(12);
		chooseCustTypeP.setLayout(null);
		
		String custType[] = {"Registered Customers","Guest Customers","All customers","Search by Cust ID"};
		
		custTypeCB = new JComboBox<String>(custType);
		custTypeCB.setBounds(30, 23, 225, 20);
		chooseCustTypeP.add(custTypeCB);
		custTypeCB.addItemListener(this);
		
		JLabel lbl4 = new JLabel("Enter Cust ID :    ");
		lbl4.setBounds(30, 63, 107, 14);
		chooseCustTypeP.add(lbl4);
		chooseCustTypeP.add(custIdTF);
		
		JLabel lbl3 = new JLabel("SHOW RECORDS FROM :");
		lbl3.setBounds(596, 200, 200, 14);
		getContentPane().add(lbl3);
		
		backB = new JButton("<< BACK");
		backB.setBounds(82, 106, 150, 40);
		backB.addActionListener(this);
		getContentPane().add(backB);
		
		panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GRAY));
		panel.setBounds(286, 387, 800, 280);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		outputTA = new JTextArea();
		outputTA.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(outputTA);
		scrollPane.setBounds(10, 11, 780, 258);
		panel.add(scrollPane);
	}

	//METHOD TO RESET THE FIELDS OF THE FRAME.
	void reset(){
		statsRB.setSelected(true);
		custTypeCB.setSelectedIndex(0);
		custIdTF.setText("");
		outputTA.setText("");
		
		Long currentTime = System.currentTimeMillis();
		
		//THE TIME IN TIME SPINNERS ARE SET TO THE MAX POSSIBLE TIME WINDOW THAT IS 
		//FROM THE TIME THAT THE SHOP STARTED TO THE CURRENT TIME.
		
		//THE STARTING DATE AND TIME OF SHOP OS CONSIDERED AS -- 1 JAN 2010 00:00
		startDateS.setModel(new SpinnerDateModel(new Date(1262284200000L), new Date(1262284200000L),  new Date(currentTime), Calendar.DAY_OF_MONTH));
		//AND END DATE AND TIME WILL THE THE CURRENT SYSTEM DATE AND TIME.
		endDateS.setModel(new SpinnerDateModel(new Date(currentTime), new Date(1262284200000L),  new Date(currentTime), Calendar.DAY_OF_MONTH));
	
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(custTypeCB.getSelectedIndex() == Globals.SINGLE_CUST){
			try {
				int id = Integer.parseInt(custIdTF.getText());
				
				//THERE IS ACTUALLY AN ENTRY FOR ALL THE GUEST CUSTOMERS with cust id 0
				// SO WE HAVE TO CHECK IF THE USER ENTERS 0 AS CUST ID
				// IF ENTERED CUST ID IS 0, AN ERROR DIALOG IS SHOWN AND METHOD IS TERMINATED
				if(id == 0){
					JOptionPane.showMessageDialog(null, "    NOT A VALID CUSTOMER ID !!!", null, JOptionPane.ERROR_MESSAGE);
					return;
				}
				try {
					
					ResultSet resultSet = dBConnection.custLogin(id);
					
					if(!resultSet.isBeforeFirst()){
						JOptionPane.showMessageDialog(null, "    NOT A VALID CUSTOMER ID !!!", null, JOptionPane.ERROR_MESSAGE);
						return;
					}
				} catch (SQLException e1) {
					dBConnection.disconnect();
					JOptionPane.showMessageDialog(this, "Something is wrong with the database !!!", "DATABASE ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				} catch (ClassNotFoundException e2) {
					JOptionPane.showMessageDialog(this, "Something is wrong with the database Driver !!!", "DATABASE ERROR", JOptionPane.ERROR_MESSAGE);
				}
				finally{
					dBConnection.disconnect();
				}
			}
			catch(NumberFormatException e3){
				JOptionPane.showMessageDialog(null, "ENTER NUMERIC CUSTOMER ID !!!", null, JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		
		if(e.getSource() == showRecordsB){
			Globals.START_DATE = (Date)startDateS.getValue();
			Globals.END_DATE = (Date)endDateS.getValue();
			
			try {
				if(statsRB.isSelected())
					callForStats();
				else
					callForRecords();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(this, "Something is wrong with the database !!!", "DATABASE ERROR", JOptionPane.ERROR_MESSAGE);
			} catch (ClassNotFoundException e2) {
				JOptionPane.showMessageDialog(this, "Something is wrong with the database Driver !!!", "DATABASE ERROR", JOptionPane.ERROR_MESSAGE);
			}
			finally{
				dBConnection.disconnect();
			}
		}
		else if(e.getSource() == backB){
			controller.nextFrame(this);
		}
	}
	
	//method to show PURCHASE STATISTICS in the output textarea
	private void callForStats() throws SQLException, ClassNotFoundException{
		String[] stats;
		outputTA.setText("");
		if(custTypeCB.getSelectedIndex() == Globals.SINGLE_CUST)
			stats = dBConnection.getStats(Integer.parseInt(custIdTF.getText()));
		else
			stats = dBConnection.getStats(custTypeCB.getSelectedIndex());
		
		for(String str : stats){
	        if(str == null)
	        	continue;
	        outputTA.append(str+"\n");
		}
	}
	
	//method to show PURCHASE RECORDS in the output textarea
	private void callForRecords() throws SQLException, NumberFormatException, ClassNotFoundException{
		ResultSet rs;
		outputTA.setText("PURCHASE ID"
				+"\t"+"| CUSTOMER"
				+"\t\t"+"| NO. OF ITEMS"
				+"\t"+"| AMOUNT"
				+"\t"+"| MODE OF PAYMENT"
				+"\t"+"| TIME OF PURCHASE"
				+"\n");
		if(custTypeCB.getSelectedIndex() == 3)
			rs = dBConnection.getRecords(Integer.parseInt(custIdTF.getText()));
		else
			rs = dBConnection.getRecords(custTypeCB.getSelectedIndex());
		
		while(rs.next()){
			String name = rs.getString(2);
			
			//trick to make all name length same so that they line up correctly in output textarea.
			if(name.length()<20){
				int spacesRequired = 20 - name.length();
				for(int i = 0; i< spacesRequired; i++)
					name.concat(" ");
			}
			
			outputTA.append("_________________________________________________________________"
					+ "___________________________________________\n");
			outputTA.append(rs.getString(1)
					+"\t "+name
					+"\t\t "+rs.getString(3)
					+"\t "+rs.getString(4)
					+"\t "+rs.getString(5)
					+"\t\t "+rs.getString(6)+"\n");
		}
		dBConnection.disconnect();
	}

	//event handler to enable cust id textbox if 'search by cust id' option is selected in the combo box.
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(custTypeCB.getSelectedIndex() == 3)
			custIdTF.setEnabled(true);
		else
			custIdTF.setEnabled(false);
	}
}
