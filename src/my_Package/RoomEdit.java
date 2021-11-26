package my_Package;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RoomEdit extends JFrame {

	private JPanel contentPane;
	private JTextField number;
	private JTextField price;
	private JTextField lux;
	private JTextField balcony;
	private JTextField outlook;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RoomEdit frame = new RoomEdit();
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
	public RoomEdit() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		number = new JTextField();
		number.setEditable(false);
		number.setBounds(220, 33, 86, 20);
		contentPane.add(number);
		number.setColumns(10);
		
		price = new JTextField();
		price.setColumns(10);
		price.setBounds(220, 77, 86, 20);
		contentPane.add(price);
		
		lux = new JTextField();
		lux.setColumns(10);
		lux.setBounds(220, 119, 86, 20);
		contentPane.add(lux);
		
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
				if(!number.getText().equals(null)&&!price.getText().equals(null)&&!lux.getText().equals(null)&&!balcony.getText().equals(null)&&!outlook.getText().equals(null)) {
					System.out.println("Update "+number.getText()+" to values :"+price.getText()+" "+lux.getText()+" "+balcony.getText()+" "+outlook.getText()+" ");
				}//Add check for repeated number
			}
		});
		add.setBounds(335, 238, 89, 23);
		contentPane.add(add);
		
		JButton logOut = new JButton("Log Out");
		logOut.setBounds(217, 240, 89, 23);
		contentPane.add(logOut);
	}
}
