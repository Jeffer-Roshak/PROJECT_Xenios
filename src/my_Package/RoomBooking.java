package my_Package;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

import com.github.lgooddatepicker.components.*;
import java.awt.Color;
import java.awt.Font;

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
					RoomBooking frame = new RoomBooking(null, "TestCustomer","123","10001");
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
	public RoomBooking(Connection conn, String username, String roomNumber, String booking_id) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(100, 115, 80, 15);
		contentPane.add(lblPrice);
		
		JLabel lblRoomNumber = new JLabel("Room Number:");
		lblRoomNumber.setHorizontalAlignment(SwingConstants.LEFT);
		lblRoomNumber.setBounds(100, 70, 80, 15);
		contentPane.add(lblRoomNumber);
		
		JLabel lblLuxuryLevel = new JLabel("Luxury Level:");
		lblLuxuryLevel.setBounds(100, 160, 80, 15);
		contentPane.add(lblLuxuryLevel);
		
		JLabel lblBalcony = new JLabel("Balcony:");
		lblBalcony.setBounds(100, 205, 80, 15);
		contentPane.add(lblBalcony);
		
		JLabel lblOutlook = new JLabel("Outlook:");
		lblOutlook.setBounds(100, 250, 80, 15);
		contentPane.add(lblOutlook);
		
		DatePickerSettings dateSettings1 = new DatePickerSettings();
		TimePickerSettings timeSettings1 = new TimePickerSettings();
		timeSettings1.setAllowKeyboardEditing(false);
		DateTimePicker checkInTime = new DateTimePicker(dateSettings1, timeSettings1);
		checkInTime.setBounds(52, 315, 271, 25);
		contentPane.add(checkInTime);
		dateSettings1.setDateRangeLimits(LocalDate.now(), null);
		dateSettings1.setAllowKeyboardEditing(false);
		
		
		DatePickerSettings dateSettings2 = new DatePickerSettings();
		TimePickerSettings timeSettings2 = new TimePickerSettings();
		timeSettings2.setAllowKeyboardEditing(false);
		DateTimePicker checkOutTime = new DateTimePicker(dateSettings2, timeSettings2);
		checkOutTime.setBounds(400, 315, 248, 25);
		contentPane.add(checkOutTime);
		dateSettings2.setDateRangeLimits(LocalDate.now(), null);
		dateSettings2.setAllowKeyboardEditing(false);
		
		JLabel dlbRoomNumber = new JLabel("TestRoomNumber");
		dlbRoomNumber.setHorizontalAlignment(SwingConstants.LEFT);
		dlbRoomNumber.setBounds(200, 70, 100, 15);
		contentPane.add(dlbRoomNumber);
		
		JLabel dlbLuxuryLevel = new JLabel("TestLuxuryLevel");
		dlbLuxuryLevel.setBounds(200, 160, 100, 15);
		contentPane.add(dlbLuxuryLevel);
		
		JLabel dlbBalcony = new JLabel("TestBalcony");
		dlbBalcony.setBounds(200, 205, 100, 15);
		contentPane.add(dlbBalcony);
		
		JLabel dlbOutlook = new JLabel("TestOutlook");
		dlbOutlook.setBounds(200, 250, 100, 15);
		contentPane.add(dlbOutlook);
		
		JLabel dlbPrice = new JLabel("TestPrice");
		dlbPrice.setBounds(200, 115, 100, 15);
		contentPane.add(dlbPrice);
		
		//Loading the selected room and details in
		try {
			myStatement = conn.createStatement();
			query = "SELECT * FROM Rooms_v1 WHERE Room_number="+roomNumber;
			myResult = myStatement.executeQuery(query);
			myResult.next();
			dlbRoomNumber.setText(myResult.getString(1));
			dlbPrice.setText(myResult.getString(2));
			dlbLuxuryLevel.setText(myResult.getString(3));
			dlbBalcony.setText(myResult.getString(4));
			dlbOutlook.setText(myResult.getString(5));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		JButton logoutButton = new JButton("Log Out");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					query = "DELETE FROM Bookings_v1 WHERE booking_id="+booking_id;
					myResult = myStatement.executeQuery(query);
					close();
					Login frame = new Login();
					frame.setVisible(true);
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		logoutButton.setBounds(590, 10, 85, 21);
		contentPane.add(logoutButton);
		
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Verify dates from LGoodDatepicker
				LocalDateTime checkIn = checkInTime.getDateTimeStrict();
				LocalDateTime checkOut = checkOutTime.getDateTimeStrict();
				LocalDateTime lt = LocalDateTime.now();
				DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				if(checkIn==null||checkOut==null) {
					JOptionPane.showMessageDialog(null, "Please ensure that the time and date fields are filled", "No date or time chosen", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if(checkIn.isBefore(lt)) {
					JOptionPane.showMessageDialog(null, "Please choose a check in time after current time", "Incorrect Time", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if(checkIn.isBefore(checkOut)){
					//Run query to see if the room has a booking after system time and see if the timing conflicts with the chosen time.
					try {
						query = "SELECT Booking_id FROM Bookings_v1\r\n"
								+ "WHERE ((to_date('"+checkIn.format(formatter)+"', 'YYYY-MM-DD HH24:MI:SS') BETWEEN check_in_time AND check_out_time) \r\n"
								+ "OR (to_date('"+checkOut.format(formatter)+"', 'YYYY-MM-DD HH24:MI:SS') BETWEEN check_in_time AND check_out_time)) \r\n"
								+ "OR (to_date('"+checkIn.format(formatter)+"', 'YYYY-MM-DD HH24:MI:SS')<check_in_time AND to_date('"+checkOut.format(formatter)+"', 'YYYY-MM-DD HH24:MI:SS')>check_out_time)\r\n"
								+ "AND (check_in_time, check_out_time) IN (SELECT check_in_time, check_out_time \r\n"
								+ "FROM Bookings_v1 \r\n"
								+ "WHERE Room_number="+roomNumber+" AND Status='BOOKED')";
						myResult = myStatement.executeQuery(query);
						if(!myResult.isBeforeFirst()) {
							//Debugging
							/*query="DELETE FROM Bookings_v1 WHERE room_number="+roomNumber+" AND status='RESERVED'";
							myResult = myStatement.executeQuery(query);
							System.out.println(checkIn.format(formatter));
							System.out.println(checkOut.format(formatter));
							System.out.println("free to book");*/
							//Update to booked
							query="UPDATE Bookings_v1 SET check_in_time=to_date('"+checkIn.format(formatter)+"', 'YYYY-MM-DD HH24:MI:SS'), check_out_time=to_date('"+checkOut.format(formatter)+"', 'YYYY-MM-DD HH24:MI:SS'), status='BOOKED' WHERE room_number="+roomNumber+" AND customer_name='"+username+"'";
							myResult = myStatement.executeQuery(query);
							//Close the window once the change is made
							close();
							Customer frame = new Customer(conn, username);
							frame.setVisible(true);
						}
						else {
							query="DELETE FROM Bookings_v1 WHERE room_number="+roomNumber+" AND status='RESERVED'";
							myResult = myStatement.executeQuery(query);
							JOptionPane.showMessageDialog(null, "Please choose another time for your booking.", "Time Conflict", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(null, "Please choose a check in time before the check out.", "Incorrect Time", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		btnNewButton.setBounds(400, 358, 85, 21);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					query="DELETE FROM Bookings_v1 WHERE booking_id="+booking_id;
					myResult = myStatement.executeQuery(query);
					close();
			        Customer frame = new Customer(conn, username);
			        frame.setVisible(true);
				}catch (Exception ex) {
					ex.printStackTrace();
				}
				
			}
		});
		btnNewButton_1.setBounds(215, 358, 85, 21);
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
		btnNewButton_2.setBounds(525, 430, 150, 21);
		contentPane.add(btnNewButton_2);
		
		JLabel lblNewLabel = new JLabel("Check-In Time");
		lblNewLabel.setBounds(50, 295, 85, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Check-Out Time");
		lblNewLabel_1.setBounds(400, 295, 100, 13);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblTitle = new JLabel("Hotel Xenios");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblTitle.setBounds(20, 5, 300, 60);
		contentPane.add(lblTitle);
		
		JLabel lblXenios = new JLabel("Powered by XeniOS");
		lblXenios.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblXenios.setBounds(22, 50, 110, 15);
		contentPane.add(lblXenios);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	try {
					query="DELETE FROM Bookings_v1 WHERE booking_id="+booking_id;
					myResult = myStatement.executeQuery(query);
					System.out.println("Test");
					close();
			        Customer frame = new Customer(conn, username);
			        frame.setVisible(true);
				}catch (Exception ex) {
					ex.printStackTrace();
				}
		    	
		    }
		});
		
		/* SELECT Booking_id FROM Bookings_v1
WHERE ((to_date('2021-12-01 15:00:00', 'YYYY-MM-DD HH24:MI:SS') BETWEEN check_in_time AND check_out_time) 
OR (to_date('2021-12-02 16:45:00', 'YYYY-MM-DD HH24:MI:SS') BETWEEN check_in_time AND check_out_time)) 
OR (to_date('2021-12-01 15:00:00', 'YYYY-MM-DD HH24:MI:SS')<check_in_time AND to_date('2021-12-02 16:45:00', 'YYYY-MM-DD HH24:MI:SS')>check_out_time)
AND (check_in_time, check_out_time) IN (SELECT check_in_time, check_out_time 
FROM Bookings_v1 
WHERE Room_number=5 AND Status='BOOKED'); 
		*/
		
	}
	
	public void close() {
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
	}
}
