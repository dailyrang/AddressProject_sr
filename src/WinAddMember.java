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

public class WinAddMember extends JDialog {
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinAddMember dialog = new WinAddMember();
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
	public WinAddMember() {
		setTitle("회원등록");
		setBounds(100, 100, 490, 353);
		getContentPane().setLayout(null);
		
		lblPic = new JLabel("");
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
		lblPic.setToolTipText("더블클릭해서 사진 선택하시오.");
		lblPic.setOpaque(true);
		lblPic.setBackground(new Color(255, 255, 0));
		lblPic.setBounds(12, 10, 120, 140);
		getContentPane().add(lblPic);
		
		JLabel lblName = new JLabel("이름:");
		lblName.setBounds(156, 27, 57, 15);
		getContentPane().add(lblName);
		
		tfName = new JTextField();
		tfName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					tfMobile2.requestFocus();
			}
		});
		tfName.setBounds(225, 24, 136, 21);
		getContentPane().add(tfName);
		tfName.setColumns(10);
		
		tfMobile2 = new JTextField();
		tfMobile2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					tfMobile3.requestFocus();
			}
		});
		tfMobile2.setHorizontalAlignment(SwingConstants.RIGHT);
		tfMobile2.setColumns(10);
		tfMobile2.setBounds(294, 66, 74, 21);
		getContentPane().add(tfMobile2);
		
		JLabel lblMobile = new JLabel("전화번호:");
		lblMobile.setBounds(156, 69, 57, 15);
		getContentPane().add(lblMobile);
		
		tfEmailId = new JTextField();
		tfEmailId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					tfBirth.requestFocus();
			}
		});
		tfEmailId.setColumns(10);
		tfEmailId.setBounds(225, 114, 93, 21);
		getContentPane().add(tfEmailId);
		
		JLabel lblEmail = new JLabel("이메일:");
		lblEmail.setBounds(156, 113, 57, 15);
		getContentPane().add(lblEmail);
		
		tfBirth = new JTextField();
		tfBirth.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					WinCalendar winCalendar = new WinCalendar();
					winCalendar.setModal(true);
					winCalendar.setVisible(true);
					tfBirth.setText(winCalendar.getDate());
					tfAddress.requestFocus();
				}
			}
		});
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
		tfAddress.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					if(isOK()) {
						insertRecord();
						clearAll();
					}else
						JOptionPane.showMessageDialog(null, "필수 입력을 확인하세요\n이름, 전화번호,이메일,생년월일,주소");
			}
		});
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
		
		JButton btnInsert = new JButton("회원 가입");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isOK()) {
					insertRecord();
					clearAll();
				}else
					JOptionPane.showMessageDialog(null, "필수 입력을 확인하세요\n이름, 전화번호,이메일,생년월일,주소");
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
		tfMobile3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					tfEmailId.requestFocus();
			}
		});
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
	}

	protected void clearAll() {
		tfName.setText("");
		tfMobile2.setText("");
		tfMobile3.setText("");
		tfEmailId.setText("");
		tfBirth.setText("");
		tfAddress.setText("");
		tfName.requestFocus();
		
		filePath = "C:\\FileIO\\사진\\마동석.jpg";
		ImageIcon icon = new ImageIcon(filePath);
		Image image = icon.getImage();
		image = image.getScaledInstance(120, 140, Image.SCALE_SMOOTH);
		icon = new ImageIcon(image);
		lblPic.setIcon(icon);	
	}

	protected boolean isOK() {
		boolean bOK = true;
		if(tfName.getText().equals(""))
			bOK = false;
		else if(tfMobile2.getText().equals(""))
			bOK = false;
		else if(tfMobile3.getText().equals(""))
			bOK = false;
		else if(tfEmailId.getText().equals(""))
			bOK = false;
		else if(tfBirth.getText().equals(""))
			bOK = false;
		else if(tfAddress.getText().equals(""))
			bOK = false;
		return bOK;
	}

	protected void insertRecord() {
		String sName = tfName.getText();
		String sMobile = cbMobile1.getSelectedItem().toString() + tfMobile2.getText() + tfMobile3.getText();
		String sEmail = tfEmailId.getText() + "@" + cbEmailCompany.getSelectedItem();
		String sBirth = tfBirth.getText();
		String sGradYear = cbGradYear.getSelectedItem().toString();
		String sAddress = tfAddress.getText();
		String sPath = filePath;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlDB","root","1234");						
			Statement stmt = con.createStatement();
			
			String sql = "insert into addrTBL values(null,'" + sName + "','" + sMobile + "','";  
			sql = sql + sEmail + "','" + sAddress + "','" + sBirth + "'," + sGradYear + ",'" + sPath + "')";
			
			if(stmt.executeUpdate(sql) < 1)
				JOptionPane.showMessageDialog(null, "회원 가입 오류!!");
			
			
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
}
