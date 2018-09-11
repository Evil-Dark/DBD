package com.jxc.internalWindow;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.jxc.internalWindow.gysManage.GysAddPanel;
import com.jxc.internalWindow.gysManage.GysAlterPanel;

public class GysManage extends JInternalFrame{
	public GysManage() {
		setIconifiable(true);
		setClosable(true);
		setTitle("��Ӧ�̹���");
		JTabbedPane tabPane = new JTabbedPane();
		final GysAddPanel gysAddPanel = new GysAddPanel();
		final GysAlterPanel gysAlterPanel = new GysAlterPanel();
		tabPane.addTab("��Ӧ����Ϣ���", null, gysAddPanel, "��Ӧ�����");
		tabPane.addTab("��Ӧ����Ϣ�޸���ɾ��", null, gysAlterPanel, "�޸���ɾ��");
		getContentPane().add(tabPane);
		tabPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				gysAlterPanel.initComboBox();
			}
		});
		pack();
		setResizable(true);
		setMaximizable(true);
		setBounds(50, 50, 510,260);
		setVisible(true);
	}
}
