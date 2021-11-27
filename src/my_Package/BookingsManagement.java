package my_Package;

import java.sql.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.List;
import java.awt.Toolkit;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.table.TableModel;
import java.awt.FlowLayout;

@SuppressWarnings({ "serial", "unused" })
public class BookingsManagement extends JFrame {

	private JPanel contentPane;
	private JTable table;
	public String selectedNum=null;
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookingsManagement frame = new BookingsManagement(null, "TestCustomer");
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
	public BookingsManagement(Connection conn, String username) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 563, 318);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		String[] columns={
				"Booking ID", "Customer Name", "Room Number", "Check In Time", "Check Out Time","Cancel"
			};
		DefaultTableModel model = new DefaultTableModel();
	    model.setColumnIdentifiers(columns);
		table = new JTable(model);
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
				
			},
			new String[] {
					"Booking ID", "Customer Name", "Room Number", "Check In Time", "Check Out Time", "Cancel"
			}
		));
		for(int i =0;i<100;i++) {
			model = (DefaultTableModel) table.getModel();
			model.addRow(new Object[]{i, "2", "3","4","5","Cancel"});
		}
		table.setModel(model);
		
		//Book Button
	    Action cancel = new AbstractAction()
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	            JTable table = (JTable)e.getSource();
	            int modelRow = Integer.valueOf( e.getActionCommand() );
	            String value = table.getModel().getValueAt(modelRow, 0).toString();
	            System.out.println("Room Number: "+value);
	            //Cancel the booking
	            //Remove the booking from the table
	        }
	    };
	    ButtonColumn buttonColumn = new ButtonColumn(table, cancel, 5);
	    buttonColumn.setMnemonic(KeyEvent.VK_D);
	    
		table.setBackground(Color.LIGHT_GRAY);
		table.setForeground(Color.black);
		table.setPreferredScrollableViewportSize(new Dimension(500, 200));
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		table.setBackground(Color.WHITE);
		
		JButton logOut = new JButton("Log Out");
		contentPane.add(logOut);
		
		JButton cancelButton = new JButton("Cancel Booking");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Cancel:"+selectedNum);
			}
		});
		contentPane.add(cancelButton);
		
		JButton btnNewButton_2 = new JButton("Back to Main Page");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
		        Customer frame = new Customer(conn, username);
		        frame.setVisible(true);
		        //Ensure all reserved status is changed
			}
		});
		contentPane.add(btnNewButton_2);
		
		
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
		        DefaultTableModel model2 = new DefaultTableModel();
		        model2=(DefaultTableModel) table.getModel();
		        selectedNum= model2.getValueAt(row, 0).toString();
		    }
		});
	
	}
	
	public void close() {
		WindowEvent closeWindow = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closeWindow);
	}
}
