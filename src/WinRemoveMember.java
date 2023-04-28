import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WinRemoveMember extends JDialog {
	private JTextField tfName;
	private JTextField tfMobile2;
	private JTextField tfEmailId;
	private JTextField tfBirth;
	private JTextField tfAddress;
	private JTextField tfMobile3;
	private JComboBox cbMobile1;
	private JComboBox cbGradYear;
	private JComboBox cbEmailCompany;
	protected String filePath;
	private JLabel lblPic;

	private String sID;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinRemoveMember dialog = new WinRemoveMember();
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
	public WinRemoveMember() {
		setTitle("회원탈퇴");
		setBounds(100, 100, 490, 353);
		getContentPane().setLayout(null);
		
		lblPic = new JLabel("");		
		lblPic.setOpaque(true);
		lblPic.setBackground(new Color(255, 255, 0));
		lblPic.setBounds(12, 10, 120, 140);
		getContentPane().add(lblPic);
		
		JLabel lblName = new JLabel("이름:");
		lblName.setBounds(156, 27, 57, 15);
		getContentPane().add(lblName);
		
		tfName = new JTextField();
				tfName.setBounds(225, 24, 136, 21);
		getContentPane().add(tfName);
		tfName.setColumns(10);
		
		tfMobile2 = new JTextField();
		tfMobile2.setHorizontalAlignment(SwingConstants.RIGHT);
		tfMobile2.setColumns(10);
		tfMobile2.setBounds(294, 66, 74, 21);
		getContentPane().add(tfMobile2);
		
		JLabel lblMobile = new JLabel("전화번호:");
		lblMobile.setBounds(156, 69, 57, 15);
		getContentPane().add(lblMobile);
		
		tfEmailId = new JTextField();
		tfEmailId.setColumns(10);
		tfEmailId.setBounds(225, 114, 93, 21);
		getContentPane().add(tfEmailId);
		
		JLabel lblEmail = new JLabel("이메일:");
		lblEmail.setBounds(156, 113, 57, 15);
		getContentPane().add(lblEmail);
		
		tfBirth = new JTextField();
		tfBirth.setHorizontalAlignment(SwingConstants.RIGHT);
		tfBirth.setColumns(10);
		tfBirth.setBounds(225, 146, 136, 21);
		getContentPane().add(tfBirth);
		
		JLabel lblBirth = new JLabel("생년월일:");
		lblBirth.setBounds(156, 145, 57, 15);
		getContentPane().add(lblBirth);
		
		JLabel lblGradYear = new JLabel("졸업년도:");
		lblGradYear.setBounds(156, 185, 57, 15);
		getContentPane().add(lblGradYear);
		
		tfAddress = new JTextField();		
		tfAddress.setColumns(10);
		tfAddress.setBounds(57, 220, 285, 21);
		getContentPane().add(tfAddress);
		
		JLabel lblAddress = new JLabel("주소:");
		lblAddress.setBounds(12, 223, 57, 15);
		getContentPane().add(lblAddress);
		
		JButton btnInsert = new JButton("회원 탈퇴");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMember(sID);
			}
		});
		btnInsert.setBounds(57, 248, 135, 45);
		getContentPane().add(btnInsert);
		
		cbGradYear = new JComboBox();
		cbGradYear.setBounds(225, 185, 136, 23);
		getContentPane().add(cbGradYear);
		
		cbMobile1 = new JComboBox();
		cbMobile1.setModel(new DefaultComboBoxModel(new String[] {"010", "02", "031", "032", "033", "041", "042", "043", "044", "051", "052", "053", "054", "055", "061", "062", "063", "064"}));
		cbMobile1.setBounds(225, 65, 57, 23);
		getContentPane().add(cbMobile1);
		
		tfMobile3 = new JTextField();
		tfMobile3.setHorizontalAlignment(SwingConstants.RIGHT);
		tfMobile3.setColumns(10);
		tfMobile3.setBounds(380, 66, 74, 21);
		getContentPane().add(tfMobile3);
		
		JLabel lblNewLabel = new JLabel("@");
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 16));
		lblNewLabel.setBounds(325, 117, 23, 15);
		getContentPane().add(lblNewLabel);
		
		cbEmailCompany = new JComboBox();
		cbEmailCompany.setEditable(true);
		cbEmailCompany.setModel(new DefaultComboBoxModel(new String[] {"naver.com", "daum.net", "gmail.com", "nate.com", "ici.or.kr", "직접입력"}));
		cbEmailCompany.setBounds(348, 113, 115, 23);
		getContentPane().add(cbEmailCompany);
		
		//===================================
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		for(int i=2000; i <= year; i++)
			cbGradYear.addItem(i);
		cbGradYear.setSelectedItem(year);
		
		JButton btnExit = new JButton("종료");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnExit.setBounds(247, 251, 95, 42);
		getContentPane().add(btnExit);
		
		JButton btnSearchMember = new JButton("찾기...");
		btnSearchMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinSearchMember winSearchMember = new WinSearchMember();
				winSearchMember.setModal(true);
				winSearchMember.setVisible(true);
				
				sID = winSearchMember.getID();
				showRecordID(sID);
			}
		});
		btnSearchMember.setBounds(373, 23, 89, 23);
		getContentPane().add(btnSearchMember);
	}

	protected void deleteMember(String id) {
		//===========================
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");						
					Statement stmt = con.createStatement();					
					String sql = "delete from addrTBL where idx = " + id;					
					if(stmt.executeUpdate(sql)<0)
						JOptionPane.showMessageDialog(null, "삭제 오류!!!");
					else
						clearAll();
					
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//===========================		
	}

	protected void clearAll() {
		tfName.setText("");
		tfMobile2.setText("");
		tfMobile3.setText("");
		tfEmailId.setText("");
		tfBirth.setText("");
		tfAddress.setText("");
		tfName.requestFocus();
		
		filePath = "";
		ImageIcon icon = new ImageIcon(filePath);
		lblPic.setIcon(icon);	
	}

	protected void showRecordID(String id) {
		// id를 이용해서 테이블에서 레코드를 찾은 후, 각 텍스트 필드에 넣는다.
		//===========================
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");						
			Statement stmt = con.createStatement();
			
			String sql = "select * from addrTBL where idx = " + id;  // PK
			
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				tfName.setText(rs.getString("name"));
							
				tfAddress.setText(rs.getString("address"));
				tfBirth.setText(rs.getString("birth"));
				cbGradYear.setSelectedItem(rs.getInt("gradYear"));
				
				ImageIcon icon = new ImageIcon(rs.getString("pic"));
				Image image = icon.getImage();
				image = image.getScaledInstance(120, 140, Image.SCALE_SMOOTH);
				icon = new ImageIcon(image);
				lblPic.setIcon(icon);
				
				String sEmail[] = rs.getString("email").split("@");
				tfEmailId.setText(sEmail[0]);
				cbEmailCompany.setSelectedItem(sEmail[1]);
				
				String sMobile = rs.getString("mobile");
				if(sMobile.substring(0, 2).equals("02")) {
					cbMobile1.setSelectedItem("02");  //021234567
					tfMobile2.setText(sMobile.substring(2,sMobile.length()-4));
					tfMobile3.setText(sMobile.substring(sMobile.length()-4));
				}else {
					cbMobile1.setSelectedItem(sMobile.substring(0, 3));  //010 1234 4567, 032-525-1234
					tfMobile2.setText(sMobile.substring(3,sMobile.length()-4));
					tfMobile3.setText(sMobile.substring(sMobile.length()-4));
				}
					
			}
			
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//===========================
	}

}
