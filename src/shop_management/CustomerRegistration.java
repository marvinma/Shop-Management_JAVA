/*
 * ###$$$*** THIS IS THE CUSTOMER REGISTRATION FRAME.
 */
package shop_management;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class CustomerRegistration extends JFrame implements ActionListener{
	DBConnection dBConnection;
	
	JButton registerB, backB;
	Controller controller;
	
	private JTextField nameTF;
	private JTextField contactTF;

	//DEFAULT CONSTRUCTOR
	public CustomerRegistration(Controller controller, DBConnection dBConnection) {
		this.dBConnection = dBConnection;
		this.controller = controller;
		getContentPane().setBackground(Color.LIGHT_GRAY);
        setResizable(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("New Customer Registration");
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		
		setSize(dim.width, dim.height);
		
		setBackground(Color.LIGHT_GRAY);
		setExtendedState(MAXIMIZED_BOTH );
		getContentPane().setLayout(null);
		
		
		JPanel outerP = new JPanel();
		outerP.setBounds(468, 205, 482, 218);
		getContentPane().add(outerP);
		outerP.setLayout(null);
		
		JPanel innerP = new JPanel();
		innerP.setBounds(52, 67, 381, 89);
		outerP.add(innerP);
		innerP.setLayout(new GridLayout(2, 2, 10, 15));
		
		JLabel lblNewLabel_1 = new JLabel("CUSTOMER NAME");
		innerP.add(lblNewLabel_1);
		
		nameTF = new JTextField();
		innerP.add(nameTF);
		nameTF.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("MOBILE NO.");
		innerP.add(lblNewLabel);
		
		contactTF = new JTextField();
		innerP.add(contactTF);
		contactTF.setColumns(10);
		
		registerB = new JButton("REGISTER");
		registerB.setBounds(655, 473, 130, 35);
		registerB.addActionListener(this);
		getContentPane().add(registerB);
		
		JLabel infoL = new JLabel("NEW CUSTOMER REGISTRATION",SwingConstants.CENTER);
		infoL.setBounds(584, 159, 238, 35);
		getContentPane().add(infoL);
		
		backB = new JButton("<< BACK ");
		backB.setBounds(130, 121, 140, 35);
		backB.addActionListener(this);
		getContentPane().add(backB);

	}
	
	
	//METHOD TO RESET TEXTFIELDS AFTER REGISTRATION
	void reset(){
		nameTF.setText("");
		contactTF.setText("");
	}
	
	   
    public void actionPerformed(ActionEvent e) {
		if(e.getSource() == registerB){
			
			try{
				Long contact = Long.parseLong(contactTF.getText());
				String name = nameTF.getText();
				if(name.trim().equals(""))
					throw new Exception();
				Long id = dBConnection.insertNewCustomer(name, contact);
				JOptionPane.showMessageDialog(this, "Registration Successful !!!\nCustomer ID is " + id, "Success", JOptionPane.INFORMATION_MESSAGE);
				reset();
				controller.nextFrame(this);
			}
			catch(NumberFormatException e1){
				JOptionPane.showMessageDialog(this, "Please enter a numeric contact number !!!", "ERROR", JOptionPane.ERROR_MESSAGE);
			} 
			catch (SQLException e2) {
				JOptionPane.showMessageDialog(this, "Mobile number cannot have more than 10 digits !!!", "DATABASE ERROR", JOptionPane.ERROR_MESSAGE);
			}
			catch (Exception e3) {
				JOptionPane.showMessageDialog(this, "Please enter a name !!!", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
			finally {
				dBConnection.disconnect();
			}
		}
		else if(e.getSource() == backB){
			reset();
			controller.nextFrame(this);
		}
		
	}
}
