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
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.table.TableModel;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;

@SuppressWarnings({ "serial", "unused" })
public class Admin extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JButton button = new JButton();
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
					Admin frame = new Admin(null, "TestAdmin");
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
	public Admin(Connection conn, String username){
		setTitle("Admin | Home Page");
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(45, 110, 502, 226);
		contentPane.add(scrollPane);
		String[] columns={
				"Number", "Price", "Luxury Level", "Balcony", "Outlook"
			};
		DefaultTableModel model = new DefaultTableModel();
	    model.setColumnIdentifiers(columns);
		table = new JTable(model){
	        public boolean isCellEditable(int row, int column) {
	        	/*if (column!=5)
	        		return false; 
	        	else 
	        		return true;*/
	        	return column == 5 || column == 6; //Need to return both edit and delete columns
        };
		};
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
				
			},
			new String[] {
				"Number", "Price", "Luxury Level", "Balcony", "Outlook", "Edit Room", "Delete"
			}
		));
		
		model = (DefaultTableModel) table.getModel();
		//Load the table
		try {
			myStatement= conn.createStatement();
			query = "SELECT * FROM Rooms_v1";
			myResult = myStatement.executeQuery(query);
			while(myResult.next()) {
				model.addRow(new Object[]{myResult.getInt(1), myResult.getFloat(2), myResult.getString(3), myResult.getString(4), myResult.getString(5), "Edit", "Delete"});
			}
			} catch (Exception ex){
				ex.printStackTrace();
			}
		
		table.setModel(model);
	    Action delete=null;
	    //Delete Button
	    
			delete = new AbstractAction()
			{
			    public void actionPerformed(ActionEvent e)
			    {
			    	JTable table = (JTable)e.getSource();
			        int modelRow = Integer.valueOf( e.getActionCommand() );
			        String value = table.getModel().getValueAt(modelRow, 0).toString();
			        //System.out.println("Row: "+value);
			        int i = JOptionPane.showConfirmDialog(null,"Do you want to delete Room: "+value+"?", "Confirm Room Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			        if(i==0) {
			        query = "DELETE FROM Rooms_v1 WHERE Room_Number='"+value+"'";
			        try {
						myResult = myStatement.executeQuery(query);
						((DefaultTableModel)table.getModel()).removeRow(modelRow);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			        }
			    }
			};
	    ButtonColumn buttonColumn = new ButtonColumn(table, delete, 6);
	    buttonColumn.setMnemonic(KeyEvent.VK_D);
	    
	    //Edit Button
	    Action edit = new AbstractAction()
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	            JTable table = (JTable)e.getSource();
	            int modelRow = Integer.valueOf( e.getActionCommand() );
	            String value = table.getModel().getValueAt(modelRow, 0).toString();
	            System.out.println("Room Number: "+value);
	            close();
		        RoomEdit frame = new RoomEdit(conn, username, value);
		        frame.setVisible(true);
	        }
	    };
	    ButtonColumn buttonColumn2 = new ButtonColumn(table, edit, 5);
	    buttonColumn2.setMnemonic(KeyEvent.VK_D);
	    
		table.setBackground(Color.LIGHT_GRAY);
		table.setForeground(Color.black);
		table.setPreferredScrollableViewportSize(new Dimension(500, 200));
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		table.setBackground(Color.WHITE);
		
		JButton btnNewButton = new JButton("Add Admin");
		btnNewButton.setBounds(460, 70, 83, 21);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
		        AdminRegistration frame = new AdminRegistration(conn, username);
		        frame.setVisible(true);
			}
		});
		contentPane.add(btnNewButton);
		
		JButton logOut = new JButton("Log Out");
		logOut.setBounds(520, 10, 67, 21);
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
		
		JLabel lblUsername = new JLabel("Logged in as "+username);
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUsername.setBounds(21, 70, 250, 20);
		contentPane.add(lblUsername);
		
		JLabel lblTitle = new JLabel("Hotel Xenios");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblTitle.setBounds(20, 5, 300, 60);
		contentPane.add(lblTitle);
		
		JLabel lblXenios = new JLabel("Powered by XeniOS");
		lblXenios.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblXenios.setBounds(22, 50, 110, 15);
		contentPane.add(lblXenios);
		
		
		
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
