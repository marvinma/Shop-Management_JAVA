/*
 * ###$$$*** IN THIS WINDOW USER SELECTS PRODUCTS TO BUY.
 */
package shop_management;

import java.awt.Color;
import java.sql.*;
import java.util.Arrays;
import java.util.Vector;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Shopping extends JFrame implements ActionListener{
	DBConnection dBConnection;
	
	//FOUR TABLES FOR FOR CATEGORIES OF PRODUCTS.
	private JTable clothingTable = new JTable();
	private JTable electronicsTable = new JTable();
	private JTable healthTable = new JTable();
	private JTable decorTable = new JTable();
	
	//TABLE FOR SHOPPING CART OF CUSTOMER.
	private JTable shopListTable = new JTable();
	
	//TABBED PANE FOR SHOWING FOUR CATAGORIES OF PRODUCTS.
	JTabbedPane catagoryTP;
	
	JButton finishPurchaseB, addItemB, removeItemB;
	JTextField totalTF;
	Controller controller;
	JPanel shopListP ;
	JLabel totalL, shopCartL;
	
	//RESULTSET OBJECT FOR GETTING THE RESULT BACK AFTER QUERY.
	ResultSet resultSet;

	//DEFAULT CONSTRUCTOR
	public Shopping(Controller controller, DBConnection dBConnection) {
		this.dBConnection = dBConnection;
		this.controller = controller;
		getContentPane().setBackground(Color.LIGHT_GRAY);
        setResizable(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Shopping Page");
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		
		setSize(dim.width, dim.height);
		
		setBackground(Color.LIGHT_GRAY);
		setExtendedState(MAXIMIZED_BOTH );
		getContentPane().setLayout(null);
		
		catagoryTP = new JTabbedPane(JTabbedPane.TOP);
		catagoryTP.setBounds(80, 79, 600, 543);
		
		//ADDING ITEMS TABLES TO THE TABBED PANE.
		catagoryTP.addTab("CLOTHING", null, new JScrollPane(clothingTable), null);
		catagoryTP.addTab("ELECTRONICS", null, new JScrollPane(electronicsTable), null);
		catagoryTP.addTab("HEALTH AND FITNESS", null, new JScrollPane(healthTable), null);
		catagoryTP.addTab("HOME AND DECOR", null, new JScrollPane(decorTable), null);		

		getContentPane().add(catagoryTP);
		
		//PANEL FOR CONATAINING THE CUSTOMERS SHOPPING LIST TABLE
		shopListP = new JPanel();
		shopListP.setBounds(780, 100, 482, 439);
		shopListP.add(new JScrollPane(shopListTable));
		getContentPane().add(shopListP);
		
		addItemB = new JButton("Add Items");
		addItemB.addActionListener(this);
		addItemB.setBounds(550, 633, 130, 35);
		getContentPane().add(addItemB);
		
		removeItemB = new JButton("Remove Item");
		removeItemB.setBounds(1132, 550, 130, 35);
		removeItemB.addActionListener(this);
		getContentPane().add(removeItemB);
		
		totalL = new JLabel("TOTAL   Rs.");
		totalL.setBounds(780, 633, 130, 35);
		getContentPane().add(totalL);
		
		totalTF = new JTextField("0.00",10);
		totalTF.setEditable(false);
		totalTF.setBounds(859, 633, 130, 35);
		getContentPane().add(totalTF);
		
		finishPurchaseB = new JButton("Finish Purchase");
		finishPurchaseB.setBounds(1132, 633, 130, 35);
		finishPurchaseB.addActionListener(this);
		getContentPane().add(finishPurchaseB);
		
		shopCartL = new JLabel("SHOPPING CART",SwingConstants.CENTER);
		shopCartL.setBounds(762, 75, 130, 35);
		getContentPane().add(shopCartL);

	}
	
	//METHOD TO GET ITEMS FROM DATABASE AND POPULATE THE ITEM TABLES.
	void setTables(){
		
			try {
				resultSet = dBConnection.getItems(Globals.CLOTHING);
				DefaultTableModel clothingModel = (DefaultTableModel)buildTableModel(resultSet);
				clothingTable.setModel(clothingModel);
				TableColumn idCol = clothingTable.getColumnModel().getColumn(0);  
				idCol.setMaxWidth(60);
				idCol = clothingTable.getColumnModel().getColumn(3);  
				idCol.setMaxWidth(120);
				
				resultSet = dBConnection.getItems(Globals.ELECTRONICS);
				DefaultTableModel electronicsModel = (DefaultTableModel)buildTableModel(resultSet);
				electronicsTable.setModel(electronicsModel);
				idCol = electronicsTable.getColumnModel().getColumn(0);  
				idCol.setMaxWidth(60);
				idCol = electronicsTable.getColumnModel().getColumn(3);  
				idCol.setMaxWidth(120);
				
				resultSet = dBConnection.getItems(Globals.HEALTH_AND_FITNESS);			
				DefaultTableModel healthModel = (DefaultTableModel)buildTableModel(resultSet);
				healthTable.setModel(healthModel);
				idCol = healthTable.getColumnModel().getColumn(0);  
				idCol.setMaxWidth(60);
				idCol = healthTable.getColumnModel().getColumn(3);  
				idCol.setMaxWidth(120);
				
				resultSet = dBConnection.getItems(Globals.HOME_AND_DECOR);
				DefaultTableModel decorModel = (DefaultTableModel)buildTableModel(resultSet);
				decorTable.setModel(decorModel);		
				idCol = decorTable.getColumnModel().getColumn(0);  
				idCol.setMaxWidth(60);
				idCol = decorTable.getColumnModel().getColumn(3);  
				idCol.setMaxWidth(120);
				
				DefaultTableModel shopListModel = (DefaultTableModel)buildTableModel();
				shopListTable.setModel(shopListModel);
				idCol = shopListTable.getColumnModel().getColumn(0);  
				idCol.setMaxWidth(60);
				idCol = shopListTable.getColumnModel().getColumn(3);  
				idCol.setMaxWidth(120);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, "Something is wrong with the database !!!", "DATABASE ERROR", JOptionPane.ERROR_MESSAGE);
			} catch (ClassNotFoundException e1) {
				JOptionPane.showMessageDialog(this, "Something is wrong with the database Driver !!!", "DATABASE ERROR", JOptionPane.ERROR_MESSAGE);
			}			
			finally{
				dBConnection.disconnect();
			}
	     
	}
	
	//METHOD TO RESET EVERYTHING FOR A NEW PURCHASE
	void reset(){
		totalTF.setText("0.00");
		catagoryTP.setSelectedIndex(0);
		DefaultTableModel shopListModel = (DefaultTableModel)shopListTable.getModel();
		shopListModel.setRowCount(0);
	}
	
	//METHOD TO PERFORM OPERATIONS WHEN THE USER ADDS ITEMS FROM ITEMS LIST TO SHOPPING CART
	//TOTAL AMOUNT TEXTFIELD IS ALSO UPDATED ON THE BASIS OF ITEMS ADDED
	public void moveItemsToCart(JTable table){
		int selectedItems[] = table.getSelectedRows();	
		DefaultTableModel itemsModel = (DefaultTableModel)table.getModel();
		DefaultTableModel shopListModel = (DefaultTableModel)shopListTable.getModel();
		
		//following loop runs for each selected item and copies its fields to shopping cart and updates the TOTAL AMOUNT textfield.
		for (int i : selectedItems) {
			//Vector to copy fields of one item.
			Vector<Object> rowData = new Vector<Object>();
			for(int j=0 ;j <=3; j++){
				rowData.add(itemsModel.getValueAt(i, j));
			}	
			
			//updates TOTAL AMOUNT textfield.
			float prevTotal = Float.parseFloat(totalTF.getText());
			String itemPrice = itemsModel.getValueAt(i, 3).toString();
			float newTotal = prevTotal + Float.parseFloat(itemPrice);
			totalTF.setText(""+newTotal);
			
			//adds item details to shopping table.
			shopListModel.addRow(rowData);
		}
		
	}
	
	//METHOD TO PERFORM OPERATIONS WHEN THE USER REMOVES ITEMS FROM SHOPPING CART
	//TOTAL AMOUNT TEXTFIELD IS ALSO UPDATED ON THE BASIS OF ITEMS REMOVED
	public void removeItemsFromCart(){
		
		DefaultTableModel shopListModel = (DefaultTableModel)shopListTable.getModel();
		
		int []selectedItems = shopListTable.getSelectedRows();
		/*
		 * if multiple items are selected then the items need to be removed in the descending order of position in the table,
		 * so that the index of upper selected items do not change when they are removed.
		 * That's why the array of selected items need to be sorted in descending order.
		 */
		
		Arrays.sort(selectedItems);
		for (int left = 0, right = selectedItems.length - 1; left < right; left++, right--) {
	        int temp = selectedItems[left];
	        selectedItems[left]  = selectedItems[right];
	        selectedItems[right] = temp;
	    }
		
		for (int i : selectedItems) {
			float prevTotal = Float.parseFloat(totalTF.getText());
			String itemPrice = shopListModel.getValueAt(i, 3).toString();
			float newTotal = prevTotal - Float.parseFloat(itemPrice);
			totalTF.setText(""+newTotal);
			shopListModel.removeRow(i);
		}
		
	}
	
	//builds table model for item table and populates it from the RESULTSET object supplied.
	public DefaultTableModel buildTableModel(ResultSet resultSet)
	        throws SQLException {

	    ResultSetMetaData metaData = resultSet.getMetaData();

	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    columnNames.add("Item ID");
	    columnNames.add("Item Name");
	    columnNames.add("Item Details");
	    columnNames.add("Item Price");
	    

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (resultSet.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(resultSet.getObject(columnIndex));
	        }
	        data.add(vector);
	    }
	    return new DefaultTableModel(data, columnNames){
	    @Override
	    	public boolean isCellEditable(int row, int column){ 
	        return false;
	    	}
	    };
	    
	}
	
	// builds table model for shopping cart table.
    public static DefaultTableModel buildTableModel()
	        throws SQLException {

	    Vector<String> columnNames = new Vector<String>();
	    
	    columnNames.add("Item ID");
	    columnNames.add("Item Name");
	    columnNames.add("Item Details");
	    columnNames.add("Item Price");
	    
	    return new DefaultTableModel(columnNames, 0){
	    @Override
	    	public boolean isCellEditable(int row, int column){ 
	        return false;
	    	}
	    };

	}
    
   
    public void actionPerformed(ActionEvent e) {
		if(e.getSource() == finishPurchaseB){
			Globals.NUMBER_OF_ITEMS = shopListTable.getRowCount();
			Globals.BILL_AMOUNT = Float.parseFloat(totalTF.getText());
			if(Float.parseFloat(totalTF.getText()) == 0.0)
				JOptionPane.showMessageDialog(this, "Please add atleast one item to cart !!!", "ERROR", JOptionPane.ERROR_MESSAGE);
			else
				controller.nextFrame(this);
		}
		else if(e.getSource() == addItemB){
			int selectedTab = catagoryTP.getSelectedIndex();
			if(selectedTab == 0)
				moveItemsToCart(clothingTable);
			else if(selectedTab == 1)
				moveItemsToCart(electronicsTable);
			else if(selectedTab == 2)
				moveItemsToCart(healthTable);
			else 
				moveItemsToCart(decorTable);
		}
		else if(e.getSource() == removeItemB){
			removeItemsFromCart();
		
		}
	}
	
}
