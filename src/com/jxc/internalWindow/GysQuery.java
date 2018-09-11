package com.jxc.internalWindow;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.jxc.dao.Dao;
import com.jxc.model.CusInfo;
import com.jxc.model.GysInfo;
import com.jxc.tool.EvenOddRenderer;
import com.jxc.tool.Item;

public class GysQuery extends JInternalFrame{
	private JTable table;
	private JPanel northPanel;
	private JLabel label;
	private JComboBox tjBox;
	private JTextField textField;
	private JButton qButton;
	private JButton allButton;
	public GysQuery() {
		init();
		setUpCompent();

	}
	private void init() {
		
		setIconifiable(true);
		setClosable(true);
		setTitle("��Ӧ�̲�ѯ");
		pack();
		setResizable(true);
		setMaximizable(true);
		setBounds(50, 50, 520, 400);
		setVisible(true);
	}
	private void setUpCompent() {
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		
		table = new JTable();
		String[] tableHeads = new String[] { "��Ӧ��ID", "��Ӧ������", "��ϵ��", "��ϵ��ʽ", "����",
				"�����ʼ�", "��������", "��������", "�����˺�", "��ַ" };
		final DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setColumnIdentifiers(tableHeads);
		table = new JTable(model) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}// ��������༭
		};
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setDefaultRenderer(Object.class, new EvenOddRenderer());
		final JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setAutoscrolls(true);
		cp.add(scrollPane);

		label = new JLabel("ѡ���ѯ������");
		tjBox = new JComboBox();
		tjBox.addItem("��Ӧ�̱��");
		tjBox.addItem("��Ӧ������");
		textField = new JTextField(30);
		qButton = new JButton("��ѯ");
		qButton.addActionListener(new QButtonActionListener(model));
		allButton = new JButton("ˢ��");
		allButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				textField.setText("");
				List list = Dao.getGysInfos();
				updateTable(list, model);
			}
		});
		allButton.doClick();
		
		northPanel = new JPanel();
		northPanel.add(label);
		northPanel.add(tjBox);
		northPanel.add(textField);
		northPanel.add(qButton);
		northPanel.add(allButton);
		
		cp.add(northPanel, BorderLayout.NORTH);
		

	}

	private void updateTable(List list, final DefaultTableModel model) {
		int num = model.getRowCount();
		for (int i = 0; i < num; i++)
			model.removeRow(0);
		Iterator iterator = list.iterator();
		GysInfo gysInfo;
		while (iterator.hasNext()) {
			List info = (List) iterator.next();
			Item item = new Item();
			item.setId((String) info.get(0));
			item.setName((String) info.get(1));
			gysInfo = Dao.getGysInfo(item);
			Vector rowData = new Vector();
			rowData.add(gysInfo.getGid().trim());
			rowData.add(gysInfo.getGysmc().trim());
			rowData.add(gysInfo.getLxr().trim());
			rowData.add(gysInfo.getTel().trim());
			rowData.add(gysInfo.getFax().trim());
			rowData.add(gysInfo.getEmail().trim());
			rowData.add(gysInfo.getYzbm().trim());
			rowData.add(gysInfo.getKhyh().trim());
			rowData.add(gysInfo.getYhzh().trim());
			rowData.add(gysInfo.getAddress().trim());
			model.addRow(rowData);
		}
	}
	private class QButtonActionListener implements ActionListener {
		private final DefaultTableModel model;
		public QButtonActionListener(DefaultTableModel model) {
			this.model = model;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			String qStr = textField.getText().trim();
			int num = tjBox.getSelectedIndex();
			String tj = null;
			if (num == 0) {
				tj = "gid";
			} else {
				tj = "gysmc";
			}
			if (!qStr.isEmpty()) {
				List list = Dao.findForList("select * from tb_gysInfo where "
						+ tj + "='" + qStr + "'");
				updateTable(list, model);
			} else {
				allButton.doClick();
			}
		}

	}
}
