package my_Package;

import java.sql.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;

public class Customer extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JButton btnNewButton;
	private JButton logoutButton;
	private JLabel lblUsername;
	Statement myStatement = null;
	String query;
	ResultSet myResult;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Customer frame = new Customer(null, "TestCustomer");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Customer(Connection conn, String username) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 635, 405);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(64, 53, 469, 207);
		contentPane.add(scrollPane);
		
		String[] columns={
				"Number", "Price", "Luxury Level", "Balcony", "Outlook", "Book"
			};
		DefaultTableModel model = new DefaultTableModel();
	    model.setColumnIdentifiers(columns);
	    table = new JTable(model){
	        public boolean isCellEditable(int row, int column) {
	        	/*if (column!=5)
	        		return false; 
	        	else 
	        		return true;*/
	        	return column == 5; //Need to return both edit and delete columns
        };
		};
		table.setModel(new DefaultTableModel(
				new Object[][] {
					
				},
				new String[] {
					"Number", "Price", "Luxury Level", "Balcony", "Outlook", "Book"
				}
		));
		model = (DefaultTableModel) table.getModel();
		//Load the table
		try {
			myStatement= conn.createStatement();
			query = "SELECT * FROM Rooms_v1";
			myResult = myStatement.executeQuery(query);
			while(myResult.next()) {
				model.addRow(new Object[]{myResult.getInt(1), myResult.getFloat(2), myResult.getString(3), myResult.getString(4), myResult.getString(5), "Book"});
			}
		} catch (Exception ex){
			ex.printStackTrace();
		}
		
		table.setModel(model);
		    
		//Book Button
		Action book = new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				try {
					JTable table = (JTable)e.getSource();
					int modelRow = Integer.valueOf( e.getActionCommand() );
					String value = table.getModel().getValueAt(modelRow, 0).toString();
					System.out.println("Room Number: "+value);
					myStatement = conn.createStatement();
					//Run query to check if the roomnumber already exist in the DB
					query = "INSERT INTO Bookings_v1 Values (XXX,”XXX”,XXX,XXX,XXX,”RESERVED”)";
					myResult = myStatement.executeQuery(query);
					close();
					RoomBooking frame = new RoomBooking(conn, username, value);
					frame.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
		ButtonColumn buttonColumn = new ButtonColumn(table, book, 5);
		buttonColumn.setMnemonic(KeyEvent.VK_D);
		
		scrollPane.setViewportView(table);
		
		btnNewButton = new JButton("My Bookings");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
		        BookingsManagement frame = new BookingsManagement(conn, username);
		        frame.setVisible(true);
			}
		});
		btnNewButton.setBounds(229, 270, 135, 21);
		contentPane.add(btnNewButton);
		
		logoutButton = new JButton("Log Out");
		logoutButton.setBounds(10, 10, 85, 21);
		contentPane.add(logoutButton);
		
		lblUsername = new JLabel("Logged in as "+username);
		lblUsername.setBounds(469, 334, 113, 13);
		contentPane.add(lblUsername);
	}
	
	public void close() {
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
	}
}
