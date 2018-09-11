package com.jxc.internalWindow.userManage;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jxc.dao.Dao;
import com.jxc.model.UserInfo;
import com.jxc.tool.SetUpComponent;

public class UserAddDialog extends JDialog {
	private JPanel panel, southPanel;
	private JTextField uidField, usernameField;
	private JComboBox roleBox;
	private JPasswordField passwordField;
	private JButton addButton, resetButton;

	public UserAddDialog() {
		setUpComponent();
		int width = this.getParent().getToolkit().getDefaultToolkit()
				.getScreenSize().width;
		int height = this.getParent().getToolkit().getDefaultToolkit()
				.getScreenSize().height;
		this.setBounds(width / 2 - 120, height / 2 - 100, 450, 200);
		this.setTitle("����û���Ϣ");
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	public void setUpComponent() {
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		new SetUpComponent(panel, new JLabel("�û�ID��"), 0, 0, 1, 1, false);
		uidField = new JTextField();
		new SetUpComponent(panel, uidField, 1, 0, 3, 160, true);

		new SetUpComponent(panel, new JLabel("�û�����"), 0, 1, 1, 1, false);
		usernameField = new JTextField();
		new SetUpComponent(panel, usernameField, 1, 1, 3, 160, true);

		new SetUpComponent(panel, new JLabel("���룺"), 0, 2, 1, 1, false);
		passwordField = new JPasswordField();
		new SetUpComponent(panel, passwordField, 1, 2, 3, 160, true);

		addButton = new JButton("���");
		addButton.addActionListener(new AddActionListener());
		resetButton = new JButton("����");
		resetButton.addActionListener(new ResetActionListener());
		southPanel = new JPanel();
		southPanel.add(addButton);
		southPanel.add(resetButton);
		new SetUpComponent(panel, southPanel, 3, 3, 1, 1, true);

		this.setLayout(new BorderLayout());
		this.add(panel);

	}

	private class AddActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (uidField.getText().equals("")
					|| usernameField.getText().equals("")
					|| passwordField.getText().equals("")) {
				JOptionPane.showMessageDialog(UserAddDialog.this, "����д��Ҫ��Ϣ��");
				return;
			}
			ResultSet rs = Dao.query("select * from tb_user where uid='"
					+ uidField.getText().trim() + "'");
			try {
				if (rs != null && rs.next()) {
					JOptionPane.showMessageDialog(UserAddDialog.this, "ѧ���Ѵ��ڣ�");
				} else {
					UserInfo userInfo = new UserInfo();
					userInfo.setUid(uidField.getText().trim());
					userInfo.setUsername(usernameField.getText().trim());
					userInfo.setPassword(String.valueOf(passwordField.getPassword()));
					int count = Dao.insertUserInfo(userInfo);
					if (count > 0) {
						resetButton.doClick();
						String str[] = { "�������", "����" };
						int yn = JOptionPane.showOptionDialog(
								UserAddDialog.this, "��Ӽ�¼�ɹ���", "��ʾ��Ϣ",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.INFORMATION_MESSAGE, null, str,
								null);
						if (yn == 0) {
							resetButton.doClick();
						} else {
							UserAddDialog.this.dispose();
						}
					} else {
						JOptionPane.showMessageDialog(UserAddDialog.this,
								"��Ӽ�¼ʧ�ܣ�");
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private class ResetActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			uidField.setText("");
			usernameField.setText("");
			passwordField.setText("");
		}

	}
}
