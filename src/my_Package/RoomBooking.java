package my_Package;

import java.sql.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import com.github.lgooddatepicker.components.DateTimePicker;

public class RoomBooking extends JFrame {

	private JPanel contentPane;
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
					RoomBooking frame = new RoomBooking(null, "TestCustomer","123");
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
	public RoomBooking(Connection conn, String username, String roomNumber) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 797, 576);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton logoutButton = new JButton("Log Out");
		logoutButton.setBounds(10, 10, 85, 21);
		contentPane.add(logoutButton);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(70, 180, 80, 15);
		contentPane.add(lblPrice);
		
		JLabel lblRoomNumber = new JLabel("Room Number:");
		lblRoomNumber.setHorizontalAlignment(SwingConstants.LEFT);
		lblRoomNumber.setBounds(70, 60, 80, 15);
		contentPane.add(lblRoomNumber);
		
		JLabel lblLuxuryLevel = new JLabel("Luxury Level:");
		lblLuxuryLevel.setBounds(70, 90, 80, 15);
		contentPane.add(lblLuxuryLevel);
		
		JLabel lblBalcony = new JLabel("Balcony:");
		lblBalcony.setBounds(70, 120, 80, 15);
		contentPane.add(lblBalcony);
		
		JLabel lblOutlook = new JLabel("Outlook:");
		lblOutlook.setBounds(70, 150, 80, 15);
		contentPane.add(lblOutlook);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Verify dates from LGoodDatepicker
				//Run query to see if the room has a booking after system time and see if the timing conflicts with the chosen time.
				
				//Close the window once the change is made.
				close();
		        Customer frame = new Customer(conn, username);
		        frame.setVisible(true);
			}
		});
		btnNewButton.setBounds(295, 307, 85, 21);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
		        Customer frame = new Customer(conn, username);
		        frame.setVisible(true);
		        //Ensure all reserved status is changed
			}
		});
		btnNewButton_1.setBounds(123, 307, 85, 21);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Back to Main Page");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
		        Customer frame = new Customer(conn, username);
		        frame.setVisible(true);
		        //Ensure all reserved status is changed
			}
		});
		btnNewButton_2.setBounds(105, 10, 147, 21);
		contentPane.add(btnNewButton_2);
		
		JLabel dlbRoomNumber = new JLabel("TestRoomNumber");
		dlbRoomNumber.setHorizontalAlignment(SwingConstants.LEFT);
		dlbRoomNumber.setBounds(160, 60, 100, 15);
		contentPane.add(dlbRoomNumber);
		
		JLabel dlbLuxuryLevel = new JLabel("TestLuxuryLevel");
		dlbLuxuryLevel.setBounds(160, 90, 100, 15);
		contentPane.add(dlbLuxuryLevel);
		
		JLabel dlbBalcony = new JLabel("TestBalcony");
		dlbBalcony.setBounds(160, 120, 100, 15);
		contentPane.add(dlbBalcony);
		
		JLabel dlbOutlook = new JLabel("TestOutlook");
		dlbOutlook.setBounds(160, 150, 100, 15);
		contentPane.add(dlbOutlook);
		
		JLabel dlbPrice = new JLabel("TestPrice");
		dlbPrice.setBounds(160, 180, 100, 15);
		contentPane.add(dlbPrice);
		
		JLabel lblNewLabel = new JLabel("Check-In Time");
		lblNewLabel.setBounds(52, 220, 85, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Check-Out Time");
		lblNewLabel_1.setBounds(428, 220, 100, 13);
		contentPane.add(lblNewLabel_1);
		
		DateTimePicker dateTimePicker = new DateTimePicker();
		dateTimePicker.setBounds(52, 258, 271, 23);
		contentPane.add(dateTimePicker);
		
		DateTimePicker dateTimePicker_1 = new DateTimePicker();
		dateTimePicker_1.setBounds(428, 258, 248, 23);
		contentPane.add(dateTimePicker_1);
	}
	
	public void close() {
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
	}
}
