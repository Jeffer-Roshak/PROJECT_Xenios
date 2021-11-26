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
public class Admin extends JFrame {

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
					Admin frame = new Admin();
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
	public Admin() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 563, 318);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		JButton roomCreate = new JButton("Create Room");
		roomCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		String[] columns={
				"Number", "Price", "Luxury Level", "Balcony", "Outlook"
			};
		DefaultTableModel model = new DefaultTableModel();
	    model.setColumnIdentifiers(columns);
		table = new JTable(model);
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
				
			},
			new String[] {
				"Number", "Price", "Luxury Level", "Balcony", "Outlook"
			}
		));
		for(int i =0;i<100;i++) {
			model = (DefaultTableModel) table.getModel();
			model.addRow(new Object[]{i, "2", "3","4","5"});
		}
		table.setModel(model);
		table.setBackground(Color.LIGHT_GRAY);
		table.setForeground(Color.black);
		table.setPreferredScrollableViewportSize(new Dimension(500, 200));
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		table.setBackground(Color.WHITE);
		contentPane.add(roomCreate);
		
		JButton logOut = new JButton("Log Out");
		contentPane.add(logOut);
		
		JButton editRoom = new JButton("Edit Room");
		editRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Edit:"+selectedNum);
			}
		});
		contentPane.add(editRoom);
		
		
		
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
