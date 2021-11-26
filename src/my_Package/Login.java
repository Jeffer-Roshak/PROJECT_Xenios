package my_Package;

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
import java.awt.event.KeyAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField username_txtfield;
	private JPasswordField password_passfield;
	//private static JLabel lblCapsLockOn;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
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
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 641, 523);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(63, 81, 77, 14);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(63, 144, 61, 14);
		contentPane.add(lblPassword);
		
		username_txtfield = new JTextField();
		username_txtfield.setBounds(224, 78, 150, 20);
		contentPane.add(username_txtfield);
		username_txtfield.setColumns(10);
		
		JLabel lblCapsLockOn = new JLabel("Caps Lock is On");
		lblCapsLockOn.setBounds(224, 177, 106, 13);
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
		password_passfield.setBounds(224, 141, 150, 20);
		contentPane.add(password_passfield);
		
		JButton loginButton = new JButton("Log In");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((username_txtfield.getText().equals(""))||String.valueOf(password_passfield.getPassword()).equals("")) {
					System.out.println("password or username is empty");
				}
				else {
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
					            System.out.println(hashtext);//Check matching user from USERS table here,if found, open corresponding page
					  
						} catch (NoSuchAlgorithmException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		loginButton.setBounds(224, 200, 89, 23);
		contentPane.add(loginButton);
		
		JButton registerButton = new JButton("Register");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
				            System.out.println(hashtext);//Insert to USERS table here with type Customer
				  
					} catch (NoSuchAlgorithmException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				
			}
		});
		registerButton.setBounds(73, 200, 89, 23);
		contentPane.add(registerButton);
		
		
	}
}
