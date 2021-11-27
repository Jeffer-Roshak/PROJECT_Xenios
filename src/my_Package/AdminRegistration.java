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
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AdminRegistration extends JFrame {

	private JPanel contentPane;
	private JTextField username_textfield;
	private JPasswordField password_passfield;
	
	//Connection Variables
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
					AdminRegistration frame = new AdminRegistration(null, "TestAdmin");
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
	public AdminRegistration(Connection conn, String username) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 741, 542);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(111, 87, 77, 14);
		contentPane.add(lblUsername);
		
		username_textfield = new JTextField();
		username_textfield.setColumns(10);
		username_textfield.setBounds(240, 85, 150, 20);
		contentPane.add(username_textfield);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(111, 147, 61, 14);
		contentPane.add(lblPassword);
		
		JLabel lblCapsLockOn = new JLabel("Caps Lock is On");
		lblCapsLockOn.setBounds(240, 197, 122, 13);
		contentPane.add(lblCapsLockOn);
		lblCapsLockOn.setVisible(false);
		
		password_passfield = new JPasswordField();
		password_passfield.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				boolean isOn = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
				if(isOn)
					lblCapsLockOn.setVisible(true);
				else
					lblCapsLockOn.setVisible(false);
			}
			@Override
			public void focusLost(FocusEvent e) {
				lblCapsLockOn.setVisible(false);
			}
		});
		password_passfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				boolean isOn = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
				if(isOn)
					lblCapsLockOn.setVisible(true);
				else
					lblCapsLockOn.setVisible(false);
			}
		});
		password_passfield.setBounds(240, 145, 150, 20);
		contentPane.add(password_passfield);
		
		JButton registerButton = new JButton("Register");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Check if any of the text fields are empty first
				if((username_textfield.getText().equals(""))||String.valueOf(password_passfield.getPassword()).equals("")) {
					//If any field is empty
					JOptionPane.showMessageDialog(null, "Username or Password field is empty", "Registration: Fields Empty", JOptionPane.INFORMATION_MESSAGE);
					System.out.println("password or username is empty");
				}
				else {
					//If the fields are not empty, then check if the username and password is greater than 5 and 8 respectively
					if((String.valueOf(password_passfield.getPassword()).length()>=8)&&(username_textfield.getText().length()>=5)) {
						try {
							myStatement = conn.createStatement();
							//Run query to check if the username already exist in the DB
							query = "SELECT * FROM users_v1 WHERE username = '"+username_textfield.getText()+"'";
			            	//String query = "SELECT user_type FROM Users_v1";
							myResult = myStatement.executeQuery(query);
							//If it exists, print error message
							//Else, generate hash and run query to insert new user
							if(!myResult.isBeforeFirst()) {
								MessageDigest md = null;
								try {
									md = MessageDigest.getInstance("SHA-512");
						            // digest() method is called
						            // to calculate message digest of the input string
						            // returned as array of byte
						            byte[] messageDigest = md.digest(String.valueOf(password_passfield.getPassword()).getBytes());
						            // Convert byte array into signum representation
						            BigInteger no = new BigInteger(1, messageDigest);
						  
						            // Convert message digest into hex value
						            String hashtext = no.toString(16);
						  
						            // Add preceding 0s to make it 32 bit
						            while (hashtext.length() < 32) {
						                hashtext = "0" + hashtext;
						            }
						            //System.out.println(hashtext);//Insert to USERS table here with type Customer
						            query = "INSERT INTO Users_v1 VALUES ('"+username_textfield.getText()+"','"+hashtext+"','Admin')";
						            JOptionPane.showMessageDialog(null, "Account created successfully", "Registration: Successful", JOptionPane.INFORMATION_MESSAGE);
						            myStatement.executeQuery(query);
									} catch (NoSuchAlgorithmException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
							}
							else {
								JOptionPane.showMessageDialog(null, "Username already exists", "Registration: Invalid username", JOptionPane.ERROR_MESSAGE);
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					else {
						//If there aren't enough characters
						JOptionPane.showMessageDialog(null, "Username or Password does not meet minimum length", "Registration: Invalid Input", JOptionPane.INFORMATION_MESSAGE);
						System.out.println("not enoguh characters");
					}
				}
			}
		});
		registerButton.setBounds(179, 232, 89, 23);
		contentPane.add(registerButton);
		
		
		
		JLabel lblNewLabel = new JLabel("Admin Registration");
		lblNewLabel.setBounds(51, 35, 128, 13);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton_2 = new JButton("Back to Main Page");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
		        Admin frame = new Admin(conn, username);
		        frame.setVisible(true);
		        //Ensure all reserved status is changed
			}
		});
		btnNewButton_2.setBounds(488, 31, 147, 21);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton = new JButton("Log Out");
		btnNewButton.setBounds(494, 84, 85, 21);
		contentPane.add(btnNewButton);
	}
	
	public void close() {
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
	}
}
