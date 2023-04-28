import java.awt.EventQueue;

import javax.swing.JDialog;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WinMain extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinMain dialog = new WinMain();
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
	public WinMain() {
		setResizable(false);
		setTitle("ICI 주소록");
		setBounds(100, 100, 307, 298);
		getContentPane().setLayout(new GridLayout(2, 2, 0, 0));
		
		JButton btnAdd = new JButton("");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinAddMember winAddMember = new WinAddMember();
				winAddMember.setModal(true);
				winAddMember.setVisible(true);
			}
		});
		ImageIcon icon = new ImageIcon(WinMain.class.getResource("/images/add.png"));
		Image image = icon.getImage();
		image = image.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
		ImageIcon pic = new ImageIcon(image);
		btnAdd.setIcon(pic);
		getContentPane().add(btnAdd);
		
		JButton btnDrop = new JButton("");
		ImageIcon icon2 = new ImageIcon(WinMain.class.getResource("/images/delete.png"));
		Image image2 = icon2.getImage();
		image2 = image2.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
		ImageIcon pic2 = new ImageIcon(image2);
		btnDrop.setIcon(pic2);
		getContentPane().add(btnDrop);
		
		JButton btnAlter = new JButton("");
		btnAlter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinMultiSearch winMultisearch = new WinMultiSearch();
				winMultisearch.setModal(true);
				winMultisearch.setVisible(true);
			}
		});
		ImageIcon icon1 = new ImageIcon(WinMain.class.getResource("/images/update.png"));
		Image image1 = icon1.getImage();
		image1 = image1.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
		ImageIcon pic1 = new ImageIcon(image1);
		btnAlter.setIcon(pic1);
		getContentPane().add(btnAlter);
		
		JButton btnSearch = new JButton("");		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WinSearchResult2 wsr2 = new WinSearchResult2();
				wsr2.setModal(true);
				wsr2.setVisible(true);
			}
		});
		btnSearch.setIcon(new ImageIcon(WinMain.class.getResource("/images/search.png")));
		getContentPane().add(btnSearch);

	}

}
