package com.jxc.internalWindow;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import com.jxc.internalWindow.drugManage.*;

public class DrugManage extends JInternalFrame{
	public DrugManage() {
		setIconifiable(true);
		setClosable(true);
		setTitle("ҩƷ����");
		JTabbedPane tabPane = new JTabbedPane();
		final DrugAddPanel drugAddPanel = new DrugAddPanel();
		final DrugAlterPanel drugAlterPanel = new DrugAlterPanel();
		tabPane.addTab("ҩƷ��Ϣ���", null, drugAddPanel, "ҩƷ���");
		tabPane.addTab("ҩƷ��Ϣ�޸���ɾ��", null, drugAlterPanel, "�޸���ɾ��");
		getContentPane().add(tabPane);
		tabPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				drugAlterPanel.initSccjBox();
				drugAlterPanel.initDrugBox();
			}
		});
		//��ʼ����Ӧ������ѡ���
		addInternalFrameListener(new InternalFrameAdapter(){
			public void internalFrameActivated(InternalFrameEvent e) {
				super.internalFrameActivated(e);
				drugAddPanel.initSccjBox();
			}
		});
		pack();
		setBounds(50, 50, 500,220);
		setResizable(true);
		setMaximizable(true);
		setVisible(true);
	}
}
