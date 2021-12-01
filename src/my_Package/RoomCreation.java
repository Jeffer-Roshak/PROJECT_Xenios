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
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.Color;

public class RoomCreation extends JFrame {

	private JPanel contentPane;
	private JTextField roomNumber_tf;
	private JTextField price_tf;
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
					RoomCreation frame = new RoomCreation(null, "TestAdmin");
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
	public RoomCreation(Connection conn, String username) {
		setTitle("Admin | Create Room");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNumber = new JLabel("Number");
		lblNumber.setBounds(50, 130, 46, 14);
		contentPane.add(lblNumber);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(50, 175, 46, 14);
		contentPane.add(lblPrice);
		
		JLabel lblLuxuryLevel = new JLabel("Luxury Level");
		lblLuxuryLevel.setBounds(50, 220, 75, 14);
		contentPane.add(lblLuxuryLevel);
		
		JLabel lblBalcony = new JLabel("Balcony");
		lblBalcony.setBounds(50, 265, 46, 14);
		contentPane.add(lblBalcony);
		
		JLabel lblOutlook = new JLabel("Outlook");
		lblOutlook.setBounds(50, 310, 46, 14);
		contentPane.add(lblOutlook);
		
		roomNumber_tf = new JTextField();
		roomNumber_tf.setBounds(230, 127, 86, 20);
		contentPane.add(roomNumber_tf);
		roomNumber_tf.setColumns(10);
		
		price_tf = new JTextField();
		price_tf.setColumns(10);
		price_tf.setBounds(230, 172, 86, 20);
		contentPane.add(price_tf);
		
		JComboBox luxury_cb = new JComboBox();
		luxury_cb.setEditable(true);
		luxury_cb.setModel(new DefaultComboBoxModel(new String[] {"Single", "Double", "Suite"}));
		luxury_cb.setBounds(230, 217, 86, 21);
		contentPane.add(luxury_cb);
		
		JComboBox balcony_cb = new JComboBox();
		balcony_cb.setModel(new DefaultComboBoxModel(new String[] {"Y", "N"}));
		balcony_cb.setBounds(230, 262, 86, 21);
		contentPane.add(balcony_cb);
		
		JComboBox outlook_cb = new JComboBox();
		outlook_cb.setModel(new DefaultComboBoxModel(new String[] {"Beach", "Garden", "Pool"}));
		outlook_cb.setEditable(true);
		outlook_cb.setBounds(230, 307, 86, 21);
		contentPane.add(outlook_cb);
		
		JButton add = new JButton("Submit");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*if(!number.getText().equals(null)&&!price.getText().equals(null)&&!lux.getText().equals(null)&&!balcony.getText().equals(null)&&!outlook.getText().equals(null)) {
					System.out.println("Insert: "+number.getText()+" "+price.getText()+" "+lux.getText()+" "+balcony.getText()+" "+outlook.getText()+" ");
				}//Add check for repeated number*/
				/*if((username_txtfield.getText().equals(""))||String.valueOf(password_passfield.getPassword()).equals("")) {
					//If any field is empty
					System.out.println("password or username is empty");
				}*/
				if(roomNumber_tf.getText().equals("")||price_tf.getText().equals("")||luxury_cb.getSelectedItem().equals("")||outlook_cb.getSelectedItem().equals("")) {
					JOptionPane.showMessageDialog(null, "Ensure all fields are filled", "Create Room: Fields Empty", JOptionPane.INFORMATION_MESSAGE);
					System.out.println("Ensure all fields are filled!");
				}
				else {
					//Perform field checks here
					boolean incorrect=false;
					String message="Please fix the following issue(s):\n";
					//Checking RoomNumber
					try{ 
						if(Integer.parseInt(roomNumber_tf.getText())<0)
							incorrect=true;
					}catch(NumberFormatException e1){ 
						 message+="Room number must be a postive integer\n";
						 incorrect=true;
					} 
					//Checking Price
					try{ 
						Float.parseFloat(price_tf.getText()); 
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
						myStatement = conn.createStatement();
						//Run query to check if the roomnumber already exist in the DB
						query = "SELECT * FROM rooms_v1 WHERE room_number = '"+roomNumber_tf.getText()+"'";
						myResult = myStatement.executeQuery(query);
						//If it exists, print error message
						//Else, generate hash and run query to insert new user
						if(!myResult.isBeforeFirst()) {
					            query = "INSERT INTO rooms_v1 VALUES ( "+roomNumber_tf.getText()+","+price_tf.getText()+",'"+luxury_cb.getSelectedItem()+"','"+balcony_cb.getSelectedItem()+"','"+outlook_cb.getSelectedItem()+"')";
					            myStatement.executeQuery(query);
					            JOptionPane.showMessageDialog(null, "Room created successfully", "Create Room: Successful", JOptionPane.INFORMATION_MESSAGE);
					            roomNumber_tf.setText("");
					            price_tf.setText("");
					            luxury_cb.setSelectedItem("");
					            outlook_cb.setSelectedItem("");
							}
						else {
							JOptionPane.showMessageDialog(null, "Room number already exists", "Create Room: Invalid Room Number", JOptionPane.ERROR_MESSAGE);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		add.setBounds(230, 350, 89, 23);
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
		logOut.setBounds(390, 10, 85, 21);
		contentPane.add(logOut);
		
		JLabel lblUsername = new JLabel("Logged in as "+username);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUsername.setBounds(21, 70, 250, 20);
		contentPane.add(lblUsername);
		
		JButton btnNewButton = new JButton("Back To Main Page");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
		        Admin frame = new Admin(conn, username);
		        frame.setVisible(true);
			}
		});
		btnNewButton.setBounds(325, 430, 150, 21);
		contentPane.add(btnNewButton);
		
		JLabel lblTitle = new JLabel("Hotel Xenios");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblTitle.setBounds(20, 5, 300, 60);
		contentPane.add(lblTitle);
		
		JLabel lblXenios = new JLabel("Powered by XeniOS");
		lblXenios.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblXenios.setBounds(22, 50, 110, 15);
		contentPane.add(lblXenios);
		
	}
	
	public void close() {
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
	}
}
