/*
 * ###$$$*** FIRST WINDOW OF THE APPLICATION.
 * 
 * LETS THE USER CHOOSE B/W NEW PURCHARE OR PREVIOUS RECORDS.
 */

package shop_management;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class Welcome extends JFrame implements ActionListener {
	
	JPanel mainP, subP;
	JButton newPurchaseB, prevRecordsB, custRegB ;
	JLabel shopNameL;
	Controller controller;
	private JPanel sub2P;
	

	public Welcome(Controller controller) {
		this.controller = controller;
		
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setResizable(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Shopping Bazaar - Welcome");
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		
		setSize(dim.width, dim.height);
		
		setBackground(Color.LIGHT_GRAY);
		setExtendedState(MAXIMIZED_BOTH );
		getContentPane().setLayout(null);
		
		mainP = new JPanel();
		mainP.setSize(450, 289);
		mainP.setLocation(458, 203);
		mainP.setLayout(new GridLayout(3, 1, 0, 0));
		getContentPane().add(mainP);
		
		shopNameL = new JLabel("SHOP & SHOP");
		shopNameL.setHorizontalAlignment(SwingConstants.CENTER);
		mainP.add(shopNameL);
		
		sub2P = new JPanel();
		mainP.add(sub2P);
		sub2P.setLayout(null);
		
		newPurchaseB = new JButton("New Purchase");
		newPurchaseB.setBounds(135, 29, 170, 40);
		sub2P.add(newPurchaseB);
		newPurchaseB.addActionListener(this);
		
		subP = new JPanel();
		subP.setLayout(null);
		mainP.add(subP);
		
		prevRecordsB = new JButton("Previous Records");
		prevRecordsB.setBounds(256, 28, 170, 40);
		prevRecordsB.addActionListener(this);
		subP.add(prevRecordsB);
		
		custRegB = new JButton("Customer Registration");
		custRegB.setBounds(47, 28, 170, 40);
		custRegB.addActionListener(this);
		subP.add(custRegB);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == newPurchaseB)
			controller.nextFrame(this, Globals.SHOPPING_PAGE);
		else if(e.getSource() == prevRecordsB)
			controller.nextFrame(this, Globals.PREV_RECORDS_PAGE);
		else if(e.getSource() == custRegB)
			controller.nextFrame(this, Globals.CUST_REG_PAGE);
	}
}
