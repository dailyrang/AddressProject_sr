import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTable;
import javax.swing.JLabel;

public class WinSearchMember extends JDialog {
	private JTextField tfName;
	private String sAddress;
	private JTable table;

	private String sID;
	
	public String getID() {
		return sID;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinSearchMember dialog = new WinSearchMember();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public String getAddress() {
		return sAddress;
	}
	/**
	 * Create the dialog.
	 */
	public WinSearchMember() {
		setTitle("회원 검색 후 선택");
		setBounds(100, 100, 446, 396);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		tfName = new JTextField();
		tfName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					showAddress();
			}
		});
		
		JLabel lblName = new JLabel("회원명:");
		panel.add(lblName);
		panel.add(tfName);
		tfName.setColumns(10);
		
		JButton btnSearch = new JButton("탐색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAddress();
			}
		});
		panel.add(btnSearch);
		
		JPanel panelResult = new JPanel();
		getContentPane().add(panelResult, BorderLayout.CENTER);
		panelResult.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panelResult.add(scrollPane, BorderLayout.CENTER);
		
		String columns[] = {"ID", "이름", "전화번호", "졸업년도", "주소"};
		DefaultTableModel dtm = new DefaultTableModel(columns, 0);
		
		table = new JTable(dtm);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				sID = table.getValueAt(row, 0).toString();
				dispose();
			}
		});
		scrollPane.setViewportView(table);

	}

	protected void showAddress() {		
		//===========================
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");						
			Statement stmt = con.createStatement();
			
			String sql = "select * from addrTBL where name like '" + tfName.getText() + "%'";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			DefaultTableModel dtm = (DefaultTableModel)table.getModel();
			dtm.setRowCount(0);
			
			while(rs.next()) {
				Vector<String> cols = new Vector<>();
				cols.add(rs.getString("idx"));
				cols.add(rs.getString("name"));
				cols.add(rs.getString("mobile"));
				cols.add(rs.getString("gradYear"));
				cols.add(rs.getString("address"));
				
				dtm.addRow(cols);
			}
			
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//===========================
	}

}
