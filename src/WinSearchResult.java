import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WinSearchResult extends JDialog {
	private JTable table;
	private DefaultTableModel dtm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinSearchResult dialog = new WinSearchResult(1, "마동석");
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public WinSearchResult() {
		setTitle("검색 결과");
		setBounds(100, 100, 609, 360);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		String columnNames[] = {"ID","이름","전화번호","이메일","졸업년도"};
		dtm = new DefaultTableModel(columnNames, 0);
		table = new JTable(dtm);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				int id = Integer.parseInt(table.getValueAt(row, 0).toString());
				
				dispose();
				WinUpdateMember winUpdateMember = new WinUpdateMember(id);
				winUpdateMember.setModal(true);
				winUpdateMember.setVisible(true);
			}
		});
		
		scrollPane.setViewportView(table);

	}

	public WinSearchResult(int type, String value) {
		this(); // 생성자 호출(매개변수 0개짜리)
		setTitle("검색 결과 (" + type + "," + value + ")");
		
		showTable(type, value);
	}

	private void showTable(int type, String value) {
		//===========================
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");						
			Statement stmt = con.createStatement();
			
			String sql = "";
			if(type==1)
				sql = "select * from addrTBL where name = '" + value + "'";
			else if(type==2)
				sql = "select * from addrTBL where mobile = '" + value + "'";
			else if(type==3)
				sql = "select * from addrTBL where gradYear = " + value;
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Vector<String> vector = new Vector<>();
				vector.add(rs.getString("idx"));
				vector.add(rs.getString("name"));
				vector.add(rs.getString("mobile"));
				vector.add(rs.getString("email"));
				vector.add(rs.getString("gradYear"));
				
				dtm.addRow(vector);
			}			
			
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//===========================
		
	}

}
