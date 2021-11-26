package my_Package;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.List;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
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
					BookingsManagement frame = new BookingsManagement();
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
	public BookingsManagement() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 563, 318);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		String[] columns={
				"Booking ID", "Customer Name", "Room Number", "Check In Time", "Check Out Time","Status"
			};
		DefaultTableModel model = new DefaultTableModel();
	    model.setColumnIdentifiers(columns);
		table = new JTable(model);
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
				
			},
			new String[] {
					"Booking ID", "Customer Name", "Room Number", "Check In Time", "Check Out Time","Status"
			}
		));
		for(int i =0;i<100;i++) {
			model = (DefaultTableModel) table.getModel();
			model.addRow(new Object[]{i, "2", "3","4","5","6"});
		}
		table.setModel(model);
		table.setBackground(Color.LIGHT_GRAY);
		table.setForeground(Color.black);
		table.setPreferredScrollableViewportSize(new Dimension(500, 200));
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		table.setBackground(Color.WHITE);
		
		JButton logOut = new JButton("Log Out");
		contentPane.add(logOut);
		
		JButton cancel = new JButton("Cancel Booking");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Cancel:"+selectedNum);
			}
		});
		contentPane.add(cancel);
		
		
		
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
}
