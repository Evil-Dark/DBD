package com.jxc.mainWindow;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.jxc.tool.SetUpComponent;

public class Main implements ActionListener {
	private JDesktopPane desktopPane;
	private JFrame frame;
	private Container cp;
	private JPanel westPanel, northPanel, southPanel;
	// private BackImage centerPanel;
	private CardLayout cl;
	private JLabel timeNow;
	private javax.swing.Timer t;
	// ����ͼ��ǩ
	private JLabel backLabel;;
	private ImageIcon backIcon;
	// ͷ����ǩ
	private JLabel topLabel;
	/* ������Ϣ������� */
	private JPanel panel1, baseManagePanel, panel1other;
	private JButton baseManagebut_1, drugManagebut_1, sellManagebut_1,
			sysManagebut_1;
	/* ҩƷ������� */
	private JPanel panel2, drugManagePanel, panel2top, panel2bot;
	private JButton baseManagebut_2, drugManagebut_2, sellManagebut_2,
			sysManagebut_2;
	/* ���۹������ */
	private JPanel panel3, sellManagePanel, panel3top, panel3bot;
	private JButton baseManagebut_3, drugManagebut_3, sellManagebut_3,
			sysManagebut_3;
	/* ϵͳ������� */
	private JPanel panel4, sysManagePanel, panel4top;
	private JButton baseManagebut_4, drugManagebut_4, sellManagebut_4,
			sysManagebut_4;
	// ���������Map���ͼ��϶���
	private Map<String, JInternalFrame> ifs = new HashMap<String, JInternalFrame>();
	private String username;
	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void main(String args[]){
		new Main("linxiaosheng");
	}
	public Main(String username) {
		this.username=username;
		init();
	}

	private void init() {
		frame = new JFrame("ҩƷ���������ϵͳ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		// ���ñ���ͼƬ
		backIcon = new ImageIcon("images/back.png");
		backLabel = new JLabel(backIcon);
		backLabel.setVerticalAlignment(SwingConstants.TOP);// ָ������ͼƬ��ǩ��Y��Ķ��뷽ʽ
		backLabel.setHorizontalAlignment(SwingConstants.CENTER);// ���ر�ǩ������X��Ķ��뷽ʽ��
		frame.getLayeredPane().add(backLabel, new Integer(Integer.MIN_VALUE));
		backLabel.setBounds(0, 0, backIcon.getIconWidth(),
				backIcon.getIconHeight());// ���ñ�����ǩ��λ��

		cp = frame.getContentPane();
		cp.setLayout(new BorderLayout());
		setUpUIComponent();
		((JPanel) cp).setOpaque(false);

		getTime();
		// ���þ���
		int width = frame.getToolkit().getDefaultToolkit().getScreenSize().width;
		int height = frame.getToolkit().getDefaultToolkit().getScreenSize().height;
		frame.setBounds(width / 2 - 350, height / 2 - 350, 790, 700);
		frame.setVisible(true);

	}

	private void setUpUIComponent() {
		desktopPane = new JDesktopPane();
		desktopPane.setOpaque(false);
		cp.add(desktopPane);

		// �Ϸ����
		northPanel = new JPanel();
		northPanel.setBackground(new Color(222, 239, 240));
		topLabel = new JLabel(new ImageIcon("images/topback.png"));
		northPanel.add(topLabel);

		// ��ർ�����
		westPanel = new JPanel();
		cl = new CardLayout();
		westPanel.setLayout(cl);
		// ���1���
		panel1 = new JPanel(new BorderLayout());
		baseManagebut_1 = new JButton("��Ϣ����");
		baseManagePanel = new JPanel(new GridLayout(6, 1));
		baseManagePanel.add(createSonButton("CusManage", "�ͻ���Ϣ����"));
		baseManagePanel.add(createSonButton("GysManage", "��Ӧ����Ϣ����"));
		baseManagePanel.add(createSonButton("EmpManage", "Ա����Ϣ����"));
		baseManagePanel.add(createSonButton("CusQuery", "�ͻ���ѯ"));
		baseManagePanel.add(createSonButton("GysQuery", "��Ӧ�̲�ѯ"));
		baseManagePanel.add(createSonButton("EmpQuery", "Ա����ѯ"));
		panel1other = new JPanel(new GridLayout(3, 1));
		drugManagebut_1 = createFatherButton("ҩƷ����");
		drugManagebut_1.addActionListener(new DrugManagePanel());
		sellManagebut_1 = createFatherButton("���۹���");
		sellManagebut_1.addActionListener(new SellManagepanel());
		sysManagebut_1 = createFatherButton("ϵͳ����");
		sysManagebut_1.addActionListener(new SysManagepanel());
		panel1other.add(drugManagebut_1);
		panel1other.add(sellManagebut_1);
		panel1other.add(sysManagebut_1);
		panel1.add(baseManagebut_1, "North");
		panel1.add(baseManagePanel, "Center");
		panel1.add(panel1other, "South");
		// ���2���
		panel2 = new JPanel(new BorderLayout());
		panel2top = new JPanel(new GridLayout(2, 1));
		baseManagebut_2 = createFatherButton("��Ϣ����");
		baseManagebut_2.addActionListener(new BaseManagePanel());
		drugManagebut_2 = new JButton("ҩƷ����");
		panel2top.add(baseManagebut_2);
		panel2top.add(drugManagebut_2);
		drugManagePanel = new JPanel(new GridLayout(4, 1));
		drugManagePanel.add(createSonButton("DrugManage", "ҩƷ��Ϣ����"));
		drugManagePanel.add(createSonButton("DrugRK", "ҩƷ���"));
		drugManagePanel.add(createSonButton("DrugQuery", "ҩƷ��ѯ"));
		drugManagePanel.add(createSonButton("DrugRkQuery", "ҩƷ����ѯ"));
		panel2bot = new JPanel(new GridLayout(2, 1));
		sellManagebut_2 = createFatherButton("���۹���");
		sellManagebut_2.addActionListener(new SellManagepanel());
		sysManagebut_2 = createFatherButton("ϵͳ����");
		sysManagebut_2.addActionListener(new SysManagepanel());
		panel2bot.add(sellManagebut_2);
		panel2bot.add(sysManagebut_2);
		panel2.add(panel2top, "North");
		panel2.add(drugManagePanel, "Center");
		panel2.add(panel2bot, "South");
		// ���3���
		panel3 = new JPanel(new BorderLayout());
		panel3top = new JPanel(new GridLayout(3, 1));
		baseManagebut_3 = createFatherButton("��Ϣ����");
		baseManagebut_3.addActionListener(new BaseManagePanel());
		drugManagebut_3 = createFatherButton("ҩƷ����");
		drugManagebut_3.addActionListener(new DrugManagePanel());
		sellManagebut_3 = new JButton("���۹���");
		panel3top.add(baseManagebut_3);
		panel3top.add(drugManagebut_3);
		panel3top.add(sellManagebut_3);
		sellManagePanel = new JPanel(new GridLayout(3, 1));
		sellManagePanel.add(createSonButton("DrugSell", "����¼��"));
		sellManagePanel.add(createSonButton("DrugSellQuery", "���۲�ѯ"));
		sellManagePanel.add(createSonButton("DrugKcQuery", "����̵�"));
		panel3bot = new JPanel(new GridLayout(1, 1));
		sysManagebut_3 = createFatherButton("ϵͳ����");
		sysManagebut_3.addActionListener(new SysManagepanel());
		panel3bot.add(sysManagebut_3);
		panel3.add(panel3top, "North");
		panel3.add(sellManagePanel, "Center");
		panel3.add(panel3bot, "South");
		// ���4���
		panel4 = new JPanel(new BorderLayout());
		panel4top = new JPanel(new GridLayout(4, 1));
		baseManagebut_4 = createFatherButton("��Ϣ����");
		baseManagebut_4.addActionListener(new BaseManagePanel());
		drugManagebut_4 = createFatherButton("ҩƷ����");
		drugManagebut_4.addActionListener(new DrugManagePanel());
		sellManagebut_4 = createFatherButton("���۹���");
		sellManagebut_4.addActionListener(new SellManagepanel());
		sysManagebut_4 = new JButton("ϵͳ����");
		panel4top.add(baseManagebut_4);
		panel4top.add(drugManagebut_4);
		panel4top.add(sellManagebut_4);
		panel4top.add(sysManagebut_4);
		sysManagePanel = new JPanel(new GridLayout(4, 1));
		sysManagePanel.add(createSonButton("UserManage", "�û�����"));
		panel4.add(panel4top, "North");
		panel4.add(sysManagePanel, "Center");

		westPanel.add(panel1, "1");
		westPanel.add(panel2, "2");
		westPanel.add(panel3, "3");
		westPanel.add(panel4, "4");

		cp.add(northPanel, BorderLayout.NORTH);
		cp.add(westPanel, BorderLayout.WEST);

	}

	/** *********************��������************************* */
	// �õ�ϵͳ��ǰʱ��
	public void getTime() {
		southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		t = new Timer(1000, this);// ÿ��1000�����ʱһ��
		t.start();
		int hours = Calendar.getInstance().getTime().getHours();
		String hello;
		if (hours < 12)
			hello = "���Ϻã�";
		else if (hours > 12 && hours < 18)
			hello = "����ã�";
		else
			hello = "���Ϻã�";
		timeNow = new JLabel(hello+username+",�����ǣ�"+Calendar.getInstance().getTime().toLocaleString());// ȡ��ϵͳʱ��
		//timeNow.setFont(new Font("΢���ź�", Font.BOLD, 14));
		JPanel p1 = new JPanel(new BorderLayout());
		p1.add(timeNow, BorderLayout.EAST);
		southPanel.add(p1);
		frame.add(southPanel, BorderLayout.SOUTH);
	}

	private JButton createFatherButton(String text) {
		JButton button = new JButton(text, new ImageIcon("images/��.png"));
		button.setVerticalTextPosition(JButton.CENTER);
		button.setHorizontalTextPosition(JButton.LEFT);
		return button;
	}

	// Ϊ�ڲ��������Action�ķ���
	private JButton createSonButton(String fName, String cName) {
		String imgUrl = "images/" + cName + ".png";
		Icon icon = new ImageIcon(imgUrl);
		Action action = new openFrameAction(fName, cName, icon);
		JButton button = new JButton(action);
		Cursor myCursor = new Cursor(Cursor.HAND_CURSOR);// ��״���
		button.setCursor(myCursor);
		button.setVerticalTextPosition(JButton.BOTTOM);
		button.setHorizontalTextPosition(JButton.CENTER);
		button.setMargin(new Insets(0, 0, 0, 0));// ���ñ߿����ǩ�ľ���
		// button.setHideActionText(true);//����ʾAction�ı�
		button.setFocusPainted(false);// ���ý���״̬
		button.setBorderPainted(false);// �����ư�ť�߿�
		button.setContentAreaFilled(false);// ͼƬ�����ư�ť��������
		return button;
	}

	// ��ȡ�ڲ������Ψһʵ������
	private JInternalFrame getIFrame(String frameName) {
		JInternalFrame jf = null;
		if (!ifs.containsKey(frameName)) {
			try {

				Class fClass = Class.forName("com.jxc.internalWindow."
						+ frameName);
				Constructor constructor = fClass.getConstructor(null);
				jf = (JInternalFrame) constructor.newInstance(null);
				jf.setVisible(true);
				ifs.put(frameName, jf);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			jf = ifs.get(frameName);
		return jf;
	}

	// ������˵���ĵ����¼�������
	protected final class openFrameAction extends AbstractAction {
		private String frameName = null;

		private openFrameAction() {
		}

		public openFrameAction(String frameName, String cname, Icon icon) {
			this.frameName = frameName;
			putValue(Action.NAME, cname);
			putValue(Action.SHORT_DESCRIPTION, cname);
			putValue(Action.SMALL_ICON, icon);
		}

		public void actionPerformed(final ActionEvent e) {
			JInternalFrame jf = getIFrame(frameName);
			// ���ڲ�����չ�ʱ�����ڲ���������ifs����������ô��塣
			jf.addInternalFrameListener(new InternalFrameAdapter() {
				public void internalFrameClosed(InternalFrameEvent e) {
					ifs.remove(frameName);
				}
			});
			if (jf.getDesktopPane() == null) {

				desktopPane.add(jf);
				jf.setVisible(true);
			}
			try {
				jf.setSelected(true);
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
		}
	}

	// �����ڲ���������ÿ���¼�Դ�������¼�
	private class DrugManagePanel implements ActionListener {// ���

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			cl.show(westPanel, "2");// ��ʾ���������
		}

	}

	private class BaseManagePanel implements ActionListener {// ���

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			cl.show(westPanel, "1");// ��ʾ������Ϣ�������
		}

	}

	private class SellManagepanel implements ActionListener {// ���

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			cl.show(westPanel, "3");// ��ʾ�������
		}

	}

	private class SysManagepanel implements ActionListener {// ���

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			cl.show(westPanel, "4");// ��ʾϵͳ�������
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
