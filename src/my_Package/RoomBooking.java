package my_Package;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class RoomBooking extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RoomBooking frame = new RoomBooking();
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
	public RoomBooking() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 628, 576);
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
		btnNewButton.setBounds(295, 307, 85, 21);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.setBounds(123, 307, 85, 21);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Back to Main Page");
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
	}
}
