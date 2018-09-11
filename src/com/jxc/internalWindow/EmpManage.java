package com.jxc.internalWindow;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jxc.internalWindow.empManage.EmpAddPanel;
import com.jxc.internalWindow.empManage.EmpAlterPanel;

public class EmpManage extends JInternalFrame{
	public EmpManage(){
		setIconifiable(true);
		setClosable(true);
		setTitle("��Ӧ�̹���");
		JTabbedPane tabPane = new JTabbedPane();
		final EmpAddPanel empAddPanel = new EmpAddPanel();
		final EmpAlterPanel empAlterPanel = new EmpAlterPanel();
		tabPane.addTab("Ա����Ϣ���", null, empAddPanel, "Ա�����");
		tabPane.addTab("Ա����Ϣ�޸���ɾ��", null, empAlterPanel, "�޸���ɾ��");
		getContentPane().add(tabPane);
		tabPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				empAlterPanel.initComboBox();
			}
		});
		pack();
		setResizable(true);
		setMaximizable(true);
		setBounds(50, 50, 500,240);
		setVisible(true);
	}
}
