import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class WinUpdateMember extends JDialog {
	private JTextField tfName;
	private JTextField tfMobile2;
	private JTextField tfEmailId;
	private JTextField tfBirth;
	private JTextField tfAddress;
	private JTextField tfMobile3;
	private JComboBox cbGradYear;
	private JComboBox cbMobile1;
	private JComboBox cbEmailCompany;
	private JLabel lblPic;
	private String filePath;
	private int gId;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinUpdateMember dialog = new WinUpdateMember();
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
	public WinUpdateMember() {
		setTitle("회원 정보 변경");
		setBounds(100, 100, 498, 338);
		getContentPane().setLayout(null);
		
		lblPic = new JLabel("");		
		lblPic.setToolTipText("더블클릭해서 사진 선택하시오.");
		lblPic.setOpaque(true);
		lblPic.setBackground(Color.YELLOW);
		lblPic.setBounds(12, 10, 120, 140);
		lblPic.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					JFileChooser chooser = new JFileChooser("C:\\FileIO\\사진\\");
					FileNameExtensionFilter filter = new FileNameExtensionFilter("이미지파일","jpg","png","gif");
					chooser.setFileFilter(filter);
					if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						
						filePath = chooser.getSelectedFile().getPath();
						filePath = filePath.replaceAll("\\\\", "\\\\\\\\");
						ImageIcon icon = new ImageIcon(filePath);
						Image image = icon.getImage();
						image = image.getScaledInstance(120, 140, Image.SCALE_SMOOTH);
						icon = new ImageIcon(image);
						lblPic.setIcon(icon);						
					}
				}
			}
		});
		getContentPane().add(lblPic);
		
		JLabel lblName = new JLabel("이름:");
		lblName.setBounds(156, 27, 57, 15);
		getContentPane().add(lblName);
		
		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(225, 24, 136, 21);
		getContentPane().add(tfName);
		
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
		
		JButton btnSearchDoro = new JButton("도로명 찾기...");
		btnSearchDoro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinSearchDoro winSearchDoro = new WinSearchDoro();
				winSearchDoro.setModal(true);
				winSearchDoro.setVisible(true);
				tfAddress.setText(winSearchDoro.getAddress() + " ");
				tfAddress.requestFocus();
			}
		});
		btnSearchDoro.setBounds(354, 219, 109, 23);
		getContentPane().add(btnSearchDoro);
		
		JButton btnCalendar = new JButton("달력...");
		btnCalendar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinCalendar winCalendar = new WinCalendar();
				winCalendar.setModal(true);
				winCalendar.setVisible(true);
				tfBirth.setText(winCalendar.getDate());
				tfAddress.requestFocus();
			}
		});
		btnCalendar.setBounds(373, 145, 74, 23);
		getContentPane().add(btnCalendar);
		
		JButton btnUpdate = new JButton("회원 정보 변경");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateRecord();
			}
		});
		btnUpdate.setBounds(183, 244, 135, 45);
		getContentPane().add(btnUpdate);
		
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
		cbEmailCompany.setBounds(348, 113, 115, 23);
		getContentPane().add(cbEmailCompany);

		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		for(int i=2000; i <= year; i++)
			cbGradYear.addItem(i);
		cbGradYear.setSelectedItem(year);
	}

	protected void updateRecord() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");						
			Statement stmt = con.createStatement();
			
			String sql = "update addrTBL set name='" + tfName.getText() + "', mobile='" ;
			sql = sql + cbMobile1.getSelectedItem() + tfMobile2.getText() + tfMobile3.getText() + "', email='";
			sql = sql + tfEmailId.getText() + "@" + cbEmailCompany.getSelectedItem() + "', address='";
			sql = sql + tfAddress.getText() + "', birth='" + tfBirth.getText() + "', gradYear=";
			sql = sql + cbGradYear.getSelectedItem() + ", pic='" + filePath + "'";			
			sql = sql +  " where idx=" + gId;
					
			if(stmt.executeUpdate(sql) < 1)
				JOptionPane.showMessageDialog(null, "변경되지 않았습니다");
			dispose();
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//===========================
		
	}

	public WinUpdateMember(int id) {
		this();
		setTitle("회원 정보 변경: "+ id);
		gId = id;
		showRecord(id);   //showRecordID(id) 랑 WinAddMember 클래스 이용해서 작성할 것.		
	}

	private void showRecord(int id) {
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
				
				filePath = rs.getString("pic");
				filePath = filePath.replaceAll("\\\\", "\\\\\\\\");
				
				ImageIcon icon = new ImageIcon(filePath);
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
