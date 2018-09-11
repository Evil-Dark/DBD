package com.jxc.internalWindow.gysManage;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import com.jxc.dao.Dao;
import com.jxc.model.GysInfo;
import com.jxc.tool.Item;
import com.jxc.tool.SetUpComponent;

public class GysAlterPanel extends JPanel {
	private JTextField gysmcField;
	private JTextField lxrField;// ��ϵ��
	private JTextField telField;
	private JTextField faxField;
	private JTextField emailField;
	private JTextField yzbmFied;
	private JTextField khyhField;
	private JTextField yhzhField;
	private JTextField addressField;
	private JComboBox gys;
	private JButton modifyButton;
	private JButton delButton;

	public GysAlterPanel() {
		setLayout(new GridBagLayout());

		new SetUpComponent(this, new JLabel("��Ӧ�����ƣ�"), 0, 0, 1, 1, false);
		gysmcField = new JTextField();
		new SetUpComponent(this, gysmcField, 1, 0, 1, 160, true);

		new SetUpComponent(this, new JLabel("��ϵ��"), 2, 0, 1, 1, false);
		lxrField = new JTextField();
		new SetUpComponent(this, lxrField, 3, 0, 1, 160, true);
		new SetUpComponent(this, new JLabel("��ϵ�˵绰��"), 0, 1, 1, 1, false);
		telField = new JTextField();
		new SetUpComponent(this, telField, 1, 1, 1, 160, true);

		new SetUpComponent(this, new JLabel("���棺"), 2, 1, 1, 1, false);
		faxField = new JTextField();
		// yzbm.addKeyListener(new InputKeyListener());
		new SetUpComponent(this, faxField, 3, 1, 1, 0, true);

		new SetUpComponent(this, new JLabel("��ַ��"), 0, 2, 1, 1, false);
		addressField = new JTextField();
		new SetUpComponent(this, addressField, 1, 2, 3, 0, true);

		new SetUpComponent(this, new JLabel("�ʼ���"), 0, 3, 1, 1, false);
		emailField = new JTextField();
		// tel.addKeyListener(new InputKeyListener());
		new SetUpComponent(this, emailField, 1, 3, 1, 0, true);

		new SetUpComponent(this, new JLabel("�������룺"), 2, 3, 1, 1, false);
		yzbmFied = new JTextField();
		// fax.addKeyListener(new InputKeyListener());
		new SetUpComponent(this, yzbmFied, 3, 3, 1, 0, true);

		new SetUpComponent(this, new JLabel("�������У�"), 0, 4, 1, 1, false);
		khyhField = new JTextField();
		new SetUpComponent(this, khyhField, 1, 4, 1, 0, true);

		new SetUpComponent(this, new JLabel("�����˺ţ�"), 2, 4, 1, 1, false);
		yhzhField = new JTextField();
		// yh.addKeyListener(new InputKeyListener());
		new SetUpComponent(this, yhzhField, 3, 4, 1, 0, true);

		new SetUpComponent(this, new JLabel("ѡ��Ӧ��"), 0, 7, 1, 0, false);
		gys = new JComboBox();
		gys.setPreferredSize(new Dimension(230, 21));
		initComboBox();// ��ʼ������ѡ���
		// ����Ӧ����Ϣ������ѡ����ѡ���¼�
		gys.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doGysSelectAction();
			}
		});
		// ��λ��Ӧ����Ϣ������ѡ���
		new SetUpComponent(this, gys, 1, 7, 2, 0, true);

		modifyButton = new JButton("�޸�");
		delButton = new JButton("ɾ��");
		JPanel panel = new JPanel();
		panel.add(modifyButton);
		panel.add(delButton);
		// ��λ��ť
		new SetUpComponent(this, panel, 3, 7, 1, 0, false);
		// ����ɾ����ť�ĵ����¼�
		delButton.addActionListener(new DelActionListener());
		// �����޸İ�ť�ĵ����¼�
		modifyButton.addActionListener(new ModifyActionListener());
	}

	public void initComboBox() {
		List<List> gysInfoList = Dao.getGysInfos();
		List<Item> items = new ArrayList<Item>();
		gys.removeAllItems();
		for (Iterator iterator = gysInfoList.iterator(); iterator.hasNext();) {
			Item item = new Item();
			List<String> list = (List<String>) iterator.next();
			item.setId(list.get(0).toString().trim());
			item.setName(list.get(1).toString().trim());
			if (items.contains(item))
				continue;
			items.add(item);
			gys.addItem(item);
		}
		doGysSelectAction();
	}

	private void doGysSelectAction() {
		Item selectedItem;
		if (!(gys.getSelectedItem() instanceof Item)) {
			return;
		}
		selectedItem = (Item) gys.getSelectedItem();
		GysInfo gysInfo = Dao.getGysInfo(selectedItem);
		gysmcField.setText(gysInfo.getGysmc().trim());
		lxrField.setText(gysInfo.getLxr().trim());
		telField.setText(gysInfo.getTel().trim());
		faxField.setText(gysInfo.getFax().trim());
		emailField.setText(gysInfo.getEmail().trim());
		yzbmFied.setText(gysInfo.getYhzh().trim());
		khyhField.setText(gysInfo.getYhzh().trim());
		yhzhField.setText(gysInfo.getYhzh().trim());
		addressField.setText(gysInfo.getAddress().trim());
	}

	private class DelActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int count;
			Item item = (Item) gys.getSelectedItem();
			String gid = item.getId();
			count = Dao.delGysInfo(gid);
			if (count > 0) {
				JOptionPane.showMessageDialog(GysAlterPanel.this, "ɾ����Ӧ��"
						+ item.getName() + "�ɹ���");
				gys.removeItem(item);
			} else {
				JOptionPane.showMessageDialog(GysAlterPanel.this, "ɾ����Ӧ��"
						+ item.getName() + "ʧ�ܣ�");
			}
		}

	}

	private class ModifyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Item item = (Item) gys.getSelectedItem();
			String gid = item.getId();
			GysInfo gysInfo = new GysInfo();
			gysInfo.setGid(gid);
			gysInfo.setGysmc(gysmcField.getText().trim());
			gysInfo.setLxr(lxrField.getText().trim());
			gysInfo.setTel(telField.getText().trim());
			gysInfo.setFax(faxField.getText().trim());
			gysInfo.setEmail(emailField.getText().trim());
			gysInfo.setYzbm(yzbmFied.getText().trim());
			gysInfo.setKhyh(khyhField.getText().trim());
			gysInfo.setAddress(addressField.getText().trim());
			int count = Dao.modifyGysInfo(gysInfo);
			if (count > 0) {
				JOptionPane.showMessageDialog(GysAlterPanel.this, "������Ϣ�ɹ���");
			} else {
				JOptionPane.showMessageDialog(GysAlterPanel.this, "������Ϣʧ�ܣ�");
			}
		}

	}
}
