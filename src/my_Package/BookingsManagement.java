package my_Package;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
		model = (DefaultTableModel) table.getModel();
		//Load the table
		try {
			LocalDateTime lt = LocalDateTime.now();
			DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			myStatement= conn.createStatement();
			query = "SELECT booking_id, customer_name, room_number, to_char(check_in_time, 'YYYY-MM-DD HH24:MI:SS') \"Check In Time\", to_char(check_out_time, 'YYYY-MM-DD HH24:MI:SS') \"Check Out Time\" FROM Bookings_v1 WHERE to_date('"+lt.format(formatter)+"', 'YYYY-MM-DD HH24:MI:SS')<check_in_time AND status='BOOKED' AND customer_name='"+username+"'";
			myResult = myStatement.executeQuery(query);
			while(myResult.next()) {
				model.addRow(new Object[]{myResult.getString(1), myResult.getString(2), myResult.getString(3), myResult.getString(4), myResult.getString(5), "Cancel"});
			}
		} catch (Exception ex){
			ex.printStackTrace();
		}
				
		table.setModel(model);
		
		//Cancel Button
	    Action cancel = new AbstractAction()
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	            JTable table = (JTable)e.getSource();
	            int modelRow = Integer.valueOf( e.getActionCommand() );
	            String value = table.getModel().getValueAt(modelRow, 0).toString();
	            System.out.println("Booking ID "+value);
	            //Cancel the booking
	            try {
	            	query="UPDATE Bookings_v1 SET status='CANCELLED' WHERE Booking_id="+value;
	            	myResult = myStatement.executeQuery(query);
	            	((DefaultTableModel)table.getModel()).removeRow(modelRow);
	            } catch (Exception ex) {
	            	ex.printStackTrace();
	            }
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
