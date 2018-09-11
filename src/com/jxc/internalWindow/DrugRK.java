package com.jxc.internalWindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.jxc.dao.Dao;
import com.jxc.model.DrugInfo;
import com.jxc.model.GysInfo;
import com.jxc.model.RuKuDetailInfo;
import com.jxc.model.RuKuMainInfo;
import com.jxc.tool.Item;
import com.jxc.tool.SetUpComponent;

public class DrugRK extends JInternalFrame {
	private JPanel panel;
	private final JTable table;
	private final JTextField rkrqField = new JTextField(); // ����ʱ��
	private final JTextField jsrField = new JTextField(); // ������
	private final JTextField lxrField = new JTextField(); // ��ϵ��
	private final JComboBox gysBox = new JComboBox(); // ��Ӧ��
	private final JTextField ridField = new JTextField();// ��ⵥ��
	private final JTextField pzsField = new JTextField("0"); // Ʒ������
	private final JTextField rksField = new JTextField("0");// ��Ʒ����
	private final JTextField rkjeField = new JTextField("0");// �ϼƽ��
	private Date rkrqDate;
	private JComboBox ypBox;
	private final JScrollPane scrollPanel;

	public DrugRK() {
		super();
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		getContentPane().setLayout(new BorderLayout());
		setTitle("��ⵥ");
		setBounds(50, 50, 700, 400);

		panel = new JPanel(new GridBagLayout());
		this.add(panel);

		new SetUpComponent(panel, new JLabel("��ⵥ�ţ�"), 0, 0, 1, 0, false, false);
		ridField.setFocusable(false);
		new SetUpComponent(panel, ridField, 1, 0, 1, 140, true, false);

		new SetUpComponent(panel, new JLabel("��Ӧ�̣�"), 2, 0, 1, 0, false, false);
		gysBox.setPreferredSize(new Dimension(160, 21));
		// ��Ӧ������ѡ����ѡ���¼�
		gysBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doGysSelectAction();
			}
		});
		new SetUpComponent(panel, gysBox, 3, 0, 1, 1, true, false);

		new SetUpComponent(panel, new JLabel("��ϵ�ˣ�"), 4, 0, 1, 0, false, false);
		lxrField.setFocusable(false);
		new SetUpComponent(panel, lxrField, 5, 0, 1, 80, true, false);

		new SetUpComponent(panel, new JLabel("���ʱ�䣺"), 0, 1, 1, 0, false, false);
		rkrqField.setFocusable(false);
		new SetUpComponent(panel, rkrqField, 1, 1, 1, 1, true, false);

		new SetUpComponent(panel, new JLabel("�ϼƽ�"), 2, 1, 1, 0, false, false);
		rkjeField.setFocusable(false);
		new SetUpComponent(panel, rkjeField, 3, 1, 1, 1, true, false);

		new SetUpComponent(panel, new JLabel("�����ˣ�"), 4, 1, 1, 0, false, false);
		new SetUpComponent(panel, jsrField, 5, 1, 1, 1, true, false);

		ypBox = new JComboBox();
		ypBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DrugInfo drugInfo = (DrugInfo) ypBox.getSelectedItem();
				// ���ѡ����Ч�͸��±��
				if (drugInfo != null && drugInfo.getDid() != null) {
					updateTable();
				}
			}
		});

		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		initTable();
		// ����¼����Ʒ����������Ʒ�������ϼƽ��ļ���
		table.addContainerListener(new computeInfo());
		scrollPanel = new JScrollPane(table);
		scrollPanel.setPreferredSize(new Dimension(380, 200));
		new SetUpComponent(panel, scrollPanel, 0, 2, 6, 1, true, true);

		new SetUpComponent(panel, new JLabel("Ʒ��������"), 0, 3, 1, 0, false, false);
		pzsField.setFocusable(false);
		new SetUpComponent(panel, pzsField, 1, 3, 1, 1, true, false);

		new SetUpComponent(panel, new JLabel("��Ʒ������"), 2, 3, 1, 0, false, false);
		rksField.setFocusable(false);
		new SetUpComponent(panel, rksField, 3, 3, 1, 1, true, false);

		// ������Ӱ�ť�ڱ��������µ�һ��
		JButton addButton = new JButton("���");
		addButton.addActionListener(new AddActionListener());
		new SetUpComponent(panel, addButton, 4, 3, 1, 1, false, false);

		// ������ⰴť���������Ϣ
		JButton rkButton = new JButton("���");
		rkButton.addActionListener(new RkActionListener());
		new SetUpComponent(panel, rkButton, 5, 3, 1, 1, false, false);
		// ��Ӵ������������ɳ�ʼ��
		addInternalFrameListener(new initTasks());
	}

	// ��ʼ�����
	private void initTable() {
		String[] columnNames = { "��Ʒ����", "��Ʒ���", "������", "����", "��;", "��������",
				"������" };
		((DefaultTableModel) table.getModel())
				.setColumnIdentifiers(columnNames);
		TableColumn column = table.getColumnModel().getColumn(0);
		column.setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(150);
		final DefaultCellEditor editor = new DefaultCellEditor(ypBox);
		editor.setClickCountToStart(2);
		column.setCellEditor(editor);
	}

	class AddActionListener implements ActionListener { // ��Ӱ�ť���¼�������
		public void actionPerformed(ActionEvent e) {
			// ��ʼ��Ʊ��
			initRid();
			// ���������û�б�д�ĵ�Ԫ
			stopTableCellEditing();
			// �������л��������У������������
			for (int i = 0; i < table.getRowCount(); i++) {
				DrugInfo info = (DrugInfo) table.getValueAt(i, 0);
				// if (table.getValueAt(i, 0) == null)
				// return;
			}
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.addRow(new Vector());
			initYpBox();
		}
	}

	// ��ʼ��ҩƷ����ѡ���
	private void initYpBox() {
		List list = new ArrayList();
		ResultSet gysrs = Dao.query("select * from tb_gysInfo where gysmc='"
				+ gysBox.getSelectedItem() + "'");
		String gid = null;
		try {
			if (gysrs != null && gysrs.next()) {
				gid = gysrs.getString("gid");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResultSet set = Dao.query("select * from tb_drugInfo where sccj='"
				+ gid + "'");
		ypBox.removeAllItems();
		ypBox.addItem(new DrugInfo());
		for (int i = 0; table != null && i < table.getRowCount(); i++) {
			DrugInfo drugInfo = (DrugInfo) table.getValueAt(i, 0);
			if (drugInfo != null && drugInfo.getDid() != null) {
				list.add(drugInfo.getDid());
			}
		}
		try {
			while (set.next()) {
				DrugInfo drugInfo = new DrugInfo();
				drugInfo.setDid(set.getString("did").trim());
				// ���������Դ���ͬ����Ʒ����Ʒ�������оͲ��ٰ�������Ʒ
				if (list.contains(drugInfo.getDid()))
					continue;
				drugInfo.setYpmc(set.getString("ypmc").trim());
				drugInfo.setXsj(Float.parseFloat(set.getString("xsj").trim()));
				drugInfo.setJhj(Float.parseFloat(set.getString("jhj").trim()));
				drugInfo.setSl(Integer.parseInt(set.getString("sl").trim()));
				drugInfo.setYt(set.getString("yt").trim());
				drugInfo.setScrq(set.getString("scrq").trim());
				drugInfo.setBzq(set.getString("bzq").trim());
				drugInfo.setSccj(set.getString("sccj").trim());
				ypBox.addItem(drugInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// ����ĳ�ʼ������
	private final class initTasks extends InternalFrameAdapter {
		public void internalFrameActivated(InternalFrameEvent e) {
			super.internalFrameActivated(e);
			initTimeField();
			initGysField();
			initRid();
			initYpBox();
		}

		private void initGysField() {// ��ʼ����Ӧ���ֶ�
			List gysInfos = Dao.getGysInfos();
			for (Iterator iter = gysInfos.iterator(); iter.hasNext();) {
				List list = (List) iter.next();
				Item item = new Item();
				item.setId(list.get(0).toString().trim());
				item.setName(list.get(1).toString().trim());
				gysBox.addItem(item);
			}
			doGysSelectAction();
		}

		private void initTimeField() {// ��������ʱ���߳�
			new Thread(new Runnable() {
				public void run() {
					try {
						while (true) {
							rkrqDate = new Date();
							rkrqField.setText(rkrqDate.toLocaleString());
							Thread.sleep(100);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	class RkActionListener implements ActionListener { // ��ⰴť���¼�������
		public void actionPerformed(ActionEvent e) {
			// ���������û�б�д�ĵ�Ԫ
			stopTableCellEditing();
			// �������
			clearEmptyRow();
			String rksStr = rksField.getText(); // ��Ʒ����
			String pzsStr = pzsField.getText(); // Ʒ����
			String rkjeStr = rkjeField.getText(); // �ϼƽ��
			String jsrStr = jsrField.getText().trim(); // ������
			ResultSet emprs = Dao.query("select * from tb_empInfo where ename='"
					+ jsrStr + "'");
			String eid = null;
			try {
				if (emprs != null && emprs.next()) {
					eid = emprs.getString("eid");
				}
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String rkrqStr = rkrqDate.toLocaleString(); // ���ʱ��
			String rid = ridField.getText(); // ����
			String gysName = gysBox.getSelectedItem().toString();// ��Ӧ������
			ResultSet gysrs = Dao
					.query("select * from tb_gysInfo where gysmc='" + gysName
							+ "'");
			String gid = null;
			try {
				if (gysrs != null && gysrs.next()) {
					gid = gysrs.getString("gid");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (jsrStr == null || jsrStr.isEmpty()) {
				JOptionPane.showMessageDialog(DrugRK.this, "����д������");
				return;
			}
			if (table.getRowCount() <= 0) {
				JOptionPane.showMessageDialog(DrugRK.this, "��������Ʒ");
				return;
			}
			RuKuMainInfo rukuMain = new RuKuMainInfo(rid,
					Integer.parseInt(pzsStr), Integer.parseInt(rksStr),
					Float.parseFloat(rkjeStr), rkrqStr, eid, gid);
			Set<RuKuDetailInfo> set = rukuMain.getTabRukuDetails();
			int rows = table.getRowCount();
			for (int i = 0; i < rows; i++) {
				DrugInfo druginfo = (DrugInfo) table.getValueAt(i, 0);
				RuKuDetailInfo detail = new RuKuDetailInfo();
				detail.setRid(rukuMain.getRid());
				detail.setDid(druginfo.getDid());
				Integer num = Integer.parseInt(table.getValueAt(i, 3)
						.toString());
				detail.setSl(num.intValue());
				detail.setJhj(druginfo.getJhj());
				Float f=num.intValue() * druginfo.getJhj();
				detail.setJe(f);
				set.add(detail);
			}
			boolean rs = Dao.insertDrugRukuInfo(rukuMain);
			if (rs) {
				JOptionPane.showMessageDialog(DrugRK.this, "������");
				DefaultTableModel dftm = new DefaultTableModel();
				table.setModel(dftm);
				initTable();
				pzsField.setText("0");
				rksField.setText("0");
				rkjeField.setText("0");
			}
		}
	}

	// ���¼��м���Ʒ����������Ʒ�������ϼƽ��
	private final class computeInfo implements ContainerListener {
		public void componentRemoved(ContainerEvent e) {
			// �������
			clearEmptyRow();
			// �������
			int rows = table.getRowCount();
			int count = 0;
			double money = 0.0;
			// ����Ʒ������
			DrugInfo column = null;
			if (rows > 0)
				column = (DrugInfo) table.getValueAt(rows - 1, 0);
			if (rows > 0 && (column == null || column.getDid().isEmpty()))
				rows--;
			// �����Ʒ�����ͽ��
			for (int i = 0; i < rows; i++) {
				Float column2 = (Float) table.getValueAt(i, 2);
				String column3 = (String) table.getValueAt(i, 3);
				int c3 = (column3 == null || column3.isEmpty()) ? 0 : Integer
						.parseInt(column3);
				float c2 = (column2 == null) ? 0 : column2.floatValue();
				count += c3;
				money += c3 * c2;
			}

			pzsField.setText(rows + "");
			rksField.setText(count + "");
			rkjeField.setText(money + "");
			// /////////////////////////////////////////////////////////////////
		}

		public void componentAdded(ContainerEvent e) {
		}
	}

	// ��Ӧ��ѡ��ʱ������ϵ���ֶ�
	private void doGysSelectAction() {
		Item item = (Item) gysBox.getSelectedItem();
		GysInfo gysInfo = Dao.getGysInfo(item);
		lxrField.setText(gysInfo.getLxr());
		initYpBox();
	}

	// ��ʼ����ⵥ����ı���ķ���
	private void initRid() {
		java.sql.Date date = new java.sql.Date(rkrqDate.getTime());
		String maxId = Dao.getRuKuMainMaxId(date);
		ridField.setText(maxId);
	}

	// ����ҩƷ�������ѡ�񣬸��±��ǰ�е�����
	private synchronized void updateTable() {
		DrugInfo drugInfo = (DrugInfo) ypBox.getSelectedItem();
		int row = table.getSelectedRow();
		if (row >= 0 && drugInfo != null) {
			table.setValueAt(drugInfo.getDid(), row, 1);
			table.setValueAt(drugInfo.getJhj(), row, 2);
			table.setValueAt("0", row, 3);
			table.setValueAt(drugInfo.getYt(), row, 4);
			table.setValueAt(drugInfo.getScrq(), row, 5);
			table.setValueAt(drugInfo.getBzq(), row, 6);
			table.editCellAt(row, 3);
		}
	}

	// �������
	private synchronized void clearEmptyRow() {
		DefaultTableModel dftm = (DefaultTableModel) table.getModel();
		for (int i = 0; i < table.getRowCount(); i++) {
			DrugInfo info2 = (DrugInfo) table.getValueAt(i, 0);
			if (info2 == null || info2.getDid() == null
					|| info2.getDid().isEmpty()) {
				dftm.removeRow(i);
			}
		}
	}

	// ֹͣ���Ԫ�ı༭
	private void stopTableCellEditing() {
		TableCellEditor cellEditor = table.getCellEditor();
		if (cellEditor != null)
			cellEditor.stopCellEditing();
	}
}
