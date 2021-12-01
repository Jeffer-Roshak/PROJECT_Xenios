package my_Package;

import java.sql.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class RoomEdit extends JFrame {

	private JPanel contentPane;
	private JTextField roomNumber_1;
	private JTextField price;
	
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
					RoomEdit frame = new RoomEdit(null, "TestAdmin", "123");
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
	public RoomEdit(Connection conn, String username, String roomNumber) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Number");
		lblNewLabel.setBounds(10, 36, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(10, 80, 46, 14);
		contentPane.add(lblPrice);
		
		JLabel lblLuxuryLevel = new JLabel("Luxury Level");
		lblLuxuryLevel.setBounds(10, 122, 75, 14);
		contentPane.add(lblLuxuryLevel);
		
		JLabel lblBalcony = new JLabel("Balcony");
		lblBalcony.setBounds(10, 168, 46, 14);
		contentPane.add(lblBalcony);
		
		JLabel lblOutlook = new JLabel("Outlook");
		lblOutlook.setBounds(10, 212, 46, 14);
		contentPane.add(lblOutlook);
		
		roomNumber_1 = new JTextField();
		roomNumber_1.setEditable(false);
		roomNumber_1.setBounds(220, 33, 86, 20);
		contentPane.add(roomNumber_1);
		roomNumber_1.setColumns(10);
		
		roomNumber_1.setText(roomNumber);
		
		price = new JTextField();
		price.setColumns(10);
		price.setBounds(220, 77, 86, 20);
		contentPane.add(price);
		
		JComboBox luxury_cb = new JComboBox();
		luxury_cb.setModel(new DefaultComboBoxModel(new String[] {"Single", "Double", "Suite"}));
		luxury_cb.setEditable(true);
		luxury_cb.setBounds(220, 119, 86, 21);
		contentPane.add(luxury_cb);
		
		JComboBox balcony_cb = new JComboBox();
		balcony_cb.setModel(new DefaultComboBoxModel(new String[] {"Y", "N"}));
		balcony_cb.setBounds(220, 165, 86, 21);
		contentPane.add(balcony_cb);
		
		JComboBox outlook_cb = new JComboBox();
		outlook_cb.setModel(new DefaultComboBoxModel(new String[] {"Beach", "Garden", "Pool"}));
		outlook_cb.setEditable(true);
		outlook_cb.setBounds(220, 209, 86, 21);
		contentPane.add(outlook_cb);
		
		try {
			myStatement = conn.createStatement();
			query = "SELECT * FROM Rooms_v1 WHERE Room_number="+roomNumber;
			myResult = myStatement.executeQuery(query);
			myResult.next();
			price.setText(Float.toString(myResult.getFloat(2)));
			luxury_cb.setSelectedItem(myResult.getString(3));
			balcony_cb.setSelectedItem(myResult.getString(4));
			outlook_cb.setSelectedItem(myResult.getString(5));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		JButton add = new JButton("Submit");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(price.getText().equals("")||luxury_cb.getSelectedItem().equals("")||outlook_cb.getSelectedItem().equals("")) {
					JOptionPane.showMessageDialog(null, "Ensure all fields are filled", "Edit Room: Fields Empty", JOptionPane.INFORMATION_MESSAGE);
					System.out.println("Ensure all fields are filled!");
				}
				else {
					//Perform field checks here
					boolean incorrect=false;
					String message="Please fix the following issue(s):\n";
					//Checking Price
					try{ 
						Float.parseFloat(price.getText()); 
					}catch(NumberFormatException e1){ 
						message+="Room number must be a float value\n";
						incorrect=true;
					}
					//Check Luxury Text
					if(!luxury_cb.getSelectedItem().toString().matches("[a-zA-Z]+")) {
						message+="Luxury level must only contain letters\n";
						incorrect=true;
					}	
					//Check Outlook Text
					if(!outlook_cb.getSelectedItem().toString().matches("[a-zA-Z]+")) {
						message+="Outlook must only contain letters\n";
						incorrect=true;
					}	
					if(incorrect) {
						JOptionPane.showMessageDialog(null, message,"Invalid format", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					//Check if room number already exists in the DB
					try {
						query = "UPDATE rooms_v1 SET price="+price.getText()+", Luxury_label='"+luxury_cb.getSelectedItem()+"', balcony='"+balcony_cb.getSelectedItem()+"', outlook='"+outlook_cb.getSelectedItem()+"' WHERE Room_Number="+roomNumber+"";
			            myStatement.executeQuery(query);
			            JOptionPane.showMessageDialog(null, "Room edited successfully", "Edit Room: Successful", JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					
				}
			}
		});
		add.setBounds(335, 238, 89, 23);
		contentPane.add(add);
		
		JButton logOut = new JButton("Log Out");
		logOut.addActionListener(new ActionListener() {
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
		logOut.setBounds(217, 240, 89, 23);
		contentPane.add(logOut);
		
		JLabel lblNewLabel_1 = new JLabel("Edit Room Page");
		lblNewLabel_1.setBounds(10, 10, 90, 13);
		contentPane.add(lblNewLabel_1);
		
		//Back to Main Button
		JButton btnNewButton = new JButton("Back To Main");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
		        Admin frame = new Admin(conn, username);
		        frame.setVisible(true);
			}
		});
		btnNewButton.setBounds(267, 6, 153, 21);
		contentPane.add(btnNewButton);
		
		JLabel lblUsername = new JLabel("Logged in as "+username);
		lblUsername.setBounds(10, 243, 178, 13);
		contentPane.add(lblUsername);
		
		
	}
	
	public void close() {
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
	}
}
