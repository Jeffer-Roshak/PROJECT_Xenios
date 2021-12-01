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
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Color;

public class Customer extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JButton btnNewButton;
	private JButton logoutButton;
	private JLabel lblUsername;
	Statement myStatement = null;
	String query;
	ResultSet myResult;
	private JLabel lblTitle;
	private JLabel lblXenios;

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
		setTitle("Customer | Home Page");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 110, 480, 292);
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
					//Check if the selected room is already reserved
					query = "SELECT booking_id FROM Bookings_v1 WHERE room_number="+value+" AND status='RESERVED'";
					myResult = myStatement.executeQuery(query);
					if(myResult.isBeforeFirst()) {
						//If the room is already reserved for a booking
						JOptionPane.showMessageDialog(null, "This room currently unavailable for booking. Please try again later.", "Room Unavailable", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						//Create a booking and reserve the room if the room is not already reserved
						query = "INSERT INTO Bookings_v1 Values ('"+username+"',"+value+", null , null, 'RESERVED', null)";
						myResult = myStatement.executeQuery(query);
						query = "SELECT booking_id FROM Bookings_v1 WHERE customer_name='"+username+"' AND room_number="+value+" AND status='RESERVED'";
						myResult = myStatement.executeQuery(query);
						String booking_id;
						myResult.next();
						booking_id = myResult.getString(1);
						//System.out.println("Retrieve Booking: "+booking_id); //Debug statement
						close();
						//Proceed to room booking
						RoomBooking frame = new RoomBooking(conn, username, value, booking_id);
						frame.setVisible(true);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
		ButtonColumn buttonColumn = new ButtonColumn(table, book, 5);
		buttonColumn.setMnemonic(KeyEvent.VK_D);
		
		scrollPane.setViewportView(table);
		
		btnNewButton = new JButton("My Bookings");
		btnNewButton.setBounds(230, 420, 135, 21);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
		        BookingsManagement frame = new BookingsManagement(conn, username);
		        frame.setVisible(true);
			}
		});
		contentPane.add(btnNewButton);
		
		logoutButton = new JButton("Log Out");
		logoutButton.setBounds(509, 10, 67, 21);
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				Login frame;
				try {
					frame = new Login();
					frame.setVisible(true);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(logoutButton);
		
		lblUsername = new JLabel("Logged in as "+username);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUsername.setBounds(21, 70, 250, 20);
		contentPane.add(lblUsername);
		
		lblTitle = new JLabel("Hotel Xenios");
		lblTitle.setBounds(20, 5, 300, 60);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 40));
		contentPane.add(lblTitle);
		
		lblXenios = new JLabel("Powered by XeniOS");
		lblXenios.setBounds(22, 50, 110, 15);
		lblXenios.setFont(new Font("Tahoma", Font.PLAIN, 10));
		contentPane.add(lblXenios);
	}
	
	public void close() {
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
	}
}
