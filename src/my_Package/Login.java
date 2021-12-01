package my_Package;

import java.sql.*;
import java.awt.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import java.util.regex.Matcher; 
import java.util.regex.Pattern; 

/*
 * Class Login Information
 * Only class with public static void main() in the final build
 * Only keep main function for other classes just for JFrame testing
 * */
public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField username_txtfield;
	private JPasswordField password_passfield;
	
	public static void main(String[] args) throws Exception{
		//Starting the Application
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() throws Exception{
		setTitle("Hotel Xenios | Login");
		setResizable(false);
		Pattern special = Pattern.compile ("[*()_+=|<>?{}\\[\\]~-]"); 
		//Start the DB connection
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection
						("jdbc:oracle:thin:@localhost:1521:orcl", "sys as sysdba", "Student*1234");
		Statement myStatement = conn.createStatement();
		System.out.println("Connected to system DB");
				
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(120, 90, 75, 15);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(120, 140, 75, 15);
		contentPane.add(lblPassword);
		
		username_txtfield = new JTextField();
		username_txtfield.setBounds(200, 90, 150, 20);
		contentPane.add(username_txtfield);
		username_txtfield.setColumns(10);
		
		JLabel lblCapsLockOn = new JLabel("Caps Lock is On");
		lblCapsLockOn.setBounds(200, 165, 106, 13);
		contentPane.add(lblCapsLockOn);
		lblCapsLockOn.setVisible(false);
		
		password_passfield = new JPasswordField();
		password_passfield.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				lblCapsLockOn.setVisible(false);
			}
			@Override
			public void focusGained(FocusEvent e) {
				boolean isOn = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
				if(isOn)
					lblCapsLockOn.setVisible(true);
				else
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
		password_passfield.setBounds(200, 140, 150, 20);
		contentPane.add(password_passfield);
		
		//Login Button and handling Login
		JButton loginButton = new JButton("Log In");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Check if any of the text fields are empty first
				if((username_txtfield.getText().equals(""))||String.valueOf(password_passfield.getPassword()).equals("")) {
					//If any field is empty
					JOptionPane.showMessageDialog(null, "Username or Password field is empty", "Login: Fields Empty", JOptionPane.INFORMATION_MESSAGE);
					System.out.println("password or username is empty");
				}
				else {
					Matcher hasSpecial = special.matcher( username_txtfield.getText()); 
					Matcher hasSpecial2 = special.matcher( String.valueOf(password_passfield.getPassword())); 
					if(hasSpecial.find()||hasSpecial2.find()) {
						JOptionPane.showMessageDialog(null, "Username and Password cannot have the characters [*()_+=|<>?{}\\\\[\\\\]~-]", "Invalid Characters", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					//If the fields are not empty, then check if the username and password is greater than 5 and 8 respectively
					if((String.valueOf(password_passfield.getPassword()).length()>=8)&&(username_txtfield.getText().length()>=5)) {
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
					            //System.out.println(hashtext);//Check matching user from USERS table here,if found, open corresponding page
					            //Run query to obtain hash and type for tuple with the specified username
					            String query = "SELECT user_type FROM Users_v1 WHERE username='"+username_txtfield.getText()+"' AND password= '"+hashtext+"'";
					            //String query = "SELECT user_type FROM Users_v1";
					            ResultSet myResult = myStatement.executeQuery(query);
					            //If no user found, give error message
					            if(!myResult.isBeforeFirst()) {
					            	JOptionPane.showMessageDialog(null, "Username or Password incorrect", "Invalid Credentials", JOptionPane.ERROR_MESSAGE);
					            }
					            else {
					            	myResult.next();
					            	String user_type = myResult.getString("user_type");
					            	System.out.println("Test"+user_type);
					            	//If user found, compare hash	
					            	//If hash is not correct, give error message
					            	//If hash is correct, then login according to type (jump to Admin or Customer)
					            	String username = username_txtfield.getText();
					            	if(user_type.equals("Admin")) {
					            		close();
								        Admin frame = new Admin(conn, username);
								        frame.setVisible(true);
					            	}
					            	else if(user_type.equals("Customer")){
					            		close();
								        Customer frame = new Customer(conn, username);
								        frame.setVisible(true);
					            	}
					           }
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else {
						//If there aren't enough characters
						JOptionPane.showMessageDialog(null, "Username or Password does not meet minimum length", "Login: Invalid Input", JOptionPane.INFORMATION_MESSAGE);
						System.out.println("not enoguh characters");
					}
				}
			}
		});
		loginButton.setBounds(250, 200, 89, 23);
		contentPane.add(loginButton);
		
		//Register Button and handling registration
		JButton registerButton = new JButton("Register");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Check if any of the text fields are empty first
				if((username_txtfield.getText().equals(""))||String.valueOf(password_passfield.getPassword()).equals("")) {
					//If any field is empty
					JOptionPane.showMessageDialog(null, "Username or Password field is empty", "Registration: Fields Empty", JOptionPane.INFORMATION_MESSAGE);
					System.out.println("password or username is empty");
				}
				else {
					Matcher hasSpecial = special.matcher( username_txtfield.getText()); 
					Matcher hasSpecial2 = special.matcher( String.valueOf(password_passfield.getPassword())); 
					if(hasSpecial.find()||hasSpecial2.find()) {
						JOptionPane.showMessageDialog(null, "Username and Password cannot have the characters [*()+=|<>?{}~-]", "Invalid Characters", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					//If the fields are not empty, then check if the username and password is greater than 5 and 8 respectively
					if((String.valueOf(password_passfield.getPassword()).length()>=8)&&(username_txtfield.getText().length()>=5)) {
						try {
							//Run query to check if the username already exist in the DB
							String query = "SELECT * FROM users_v1 WHERE username = '"+username_txtfield.getText()+"'";
			            	//String query = "SELECT user_type FROM Users_v1";
							ResultSet myResult = myStatement.executeQuery(query);
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
						            query = "INSERT INTO Users_v1 VALUES ('"+username_txtfield.getText()+"','"+hashtext+"','Customer')";
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
		registerButton.setBounds(120, 200, 89, 23);
		contentPane.add(registerButton);
		
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
