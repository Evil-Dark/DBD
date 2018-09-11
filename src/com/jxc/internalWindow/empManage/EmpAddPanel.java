package com.jxc.internalWindow.empManage;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextHitInfo;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.jxc.dao.Dao;
import com.jxc.internalWindow.gysManage.GysAddPanel;
import com.jxc.model.EmpInfo;
import com.jxc.model.GysInfo;
import com.jxc.tool.Item;
import com.jxc.tool.SetUpComponent;

public class EmpAddPanel extends JPanel {
	private JTextField enameField;
	private JComboBox sexBox;
	private JTextField ageField;
	private JTextField telField;
	private JTextField xlField;
	private JTextField zhiwuField;
	private JTextField addressField;
	private JButton addButton;
	private JButton resetButton;
	private JPanel panel;

	public EmpAddPanel() {
		setLayout(new GridBagLayout());

		new SetUpComponent(this, new JLabel("Ա��������"), 0, 0, 1, 1, false);
		enameField = new JTextField();
		new SetUpComponent(this, enameField, 1, 0, 1, 160, true);

		new SetUpComponent(this, new JLabel("�Ա�"), 2, 0, 1, 1, false);
		sexBox = new JComboBox();
		sexBox.addItem("��");
		sexBox.addItem("Ů");
		new SetUpComponent(this, sexBox, 3, 0, 1, 160, true);

		new SetUpComponent(this, new JLabel("���䣺"), 0, 1, 1, 1, false);
		ageField = new JTextField();
		new SetUpComponent(this, ageField, 1, 1, 1, 160, true);

		new SetUpComponent(this, new JLabel("�绰��"), 2, 1, 1, 1, false);
		telField = new JTextField();
		// yzbm.addKeyListener(new InputKeyListener());
		new SetUpComponent(this, telField, 3, 1, 1, 0, true);

		new SetUpComponent(this, new JLabel("ѧ����"), 0, 2, 1, 1, false);
		xlField = new JTextField();
		new SetUpComponent(this, xlField, 1, 2, 1, 0, true);

		new SetUpComponent(this, new JLabel("ְ��"), 2, 2, 1, 1, false);
		zhiwuField = new JTextField();
		// tel.addKeyListener(new InputKeyListener());
		new SetUpComponent(this, zhiwuField, 3, 2, 1, 0, true);

		new SetUpComponent(this, new JLabel("סַ��"), 0, 3, 1, 1, false);
		addressField = new JTextField();
		// fax.addKeyListener(new InputKeyListener());
		new SetUpComponent(this, addressField, 1, 3, 3, 0, true);

		panel = new JPanel();
		addButton = new JButton("���");
		addButton.addActionListener(new AddActionListener());
		resetButton = new JButton("����");
		resetButton.addActionListener(new ResetActionListener());
		panel.add(addButton);
		panel.add(resetButton);
		new SetUpComponent(this, panel, 3, 4, 1, 1, true);

	}

	private class AddActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (enameField.getText().equals("")
					|| ageField.getText().equals("")
					|| telField.getText().equals("")
					|| xlField.getText().equals("")
					|| zhiwuField.getText().equals("")) {
				JOptionPane.showMessageDialog(EmpAddPanel.this, "����дȫ����Ϣ��");
				return;
			}
			ResultSet rs = Dao.query("select max(eid) from tb_empInfo");
			String id = null;
			try {
				if (rs != null && rs.next()) {
					String eid = rs.getString(1);
					if (eid == null) {
						id = "emp1001";
					} else {
						String s = eid.substring(3);
						id = "emp" + (Integer.parseInt(s) + 1);
					}
				}
				EmpInfo empInfo = new EmpInfo();
				empInfo.setEid(id);
				empInfo.setEname(enameField.getText().trim());
				empInfo.setSex((String)sexBox.getSelectedItem());
				empInfo.setAge(Integer.parseInt(ageField.getText().trim()));
				empInfo.setTel(telField.getText().trim());
				empInfo.setXl(xlField.getText().trim());
				empInfo.setZhi(zhiwuField.getText().trim());
				empInfo.setAddress(addressField.getText().trim());
				int count = Dao.insertEmpInfo(empInfo);
				if (count > 0) {
					JOptionPane.showMessageDialog(EmpAddPanel.this, "��Ӽ�¼�ɹ���");
					resetButton.doClick();
				} else {
					JOptionPane.showMessageDialog(EmpAddPanel.this, "��Ӽ�¼ʧ�ܣ�");
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
			enameField.setText(null);
			ageField.setText(null);
			telField.setText(null);
			xlField.setText(null);
			zhiwuField.setText(null);
			addressField.setText(null);
		}
	}
}
