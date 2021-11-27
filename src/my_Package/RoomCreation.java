package my_Package;

import java.sql.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class RoomCreation extends JFrame {

	private JPanel contentPane;
	private JTextField roomNumber;
	private JTextField price;
	private JTextField luxury;
	private JTextField balcony;
	private JTextField outlook;

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
		
		roomNumber = new JTextField();
		roomNumber.setBounds(220, 33, 86, 20);
		contentPane.add(roomNumber);
		roomNumber.setColumns(10);
		
		price = new JTextField();
		price.setColumns(10);
		price.setBounds(220, 77, 86, 20);
		contentPane.add(price);
		
		luxury = new JTextField();
		luxury.setColumns(10);
		luxury.setBounds(220, 119, 86, 20);
		contentPane.add(luxury);
		
		balcony = new JTextField();
		balcony.setColumns(10);
		balcony.setBounds(220, 165, 86, 20);
		contentPane.add(balcony);
		
		outlook = new JTextField();
		outlook.setColumns(10);
		outlook.setBounds(220, 209, 86, 20);
		contentPane.add(outlook);
		
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
				if(roomNumber.getText().equals("")||price.getText().equals("")||luxury.getText().equals("")||balcony.getText().equals("")||outlook.getText().equals("")) {
					System.out.println("Ensure all fields are filled!");
				}
				else {
					//Run the query here
					
				}
			}
		});
		add.setBounds(335, 238, 89, 23);
		contentPane.add(add);
		
		JButton logOut = new JButton("Log Out");
		logOut.setBounds(217, 240, 89, 23);
		contentPane.add(logOut);
		
		JLabel lblNewLabel_1 = new JLabel("Create Room Page");
		lblNewLabel_1.setBounds(10, 10, 100, 13);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblUsername = new JLabel("Logged in as "+username);
		lblUsername.setBounds(10, 236, 169, 13);
		contentPane.add(lblUsername);
		
		JButton btnNewButton = new JButton("Back To Main");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
		        Admin frame = new Admin(conn, username);
		        frame.setVisible(true);
			}
		});
		btnNewButton.setBounds(273, 6, 153, 21);
		contentPane.add(btnNewButton);
	}
	
	public void close() {
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
	}
}
