package com.jxc.dao;

import java.io.BufferedInputStream;   
import java.io.FileInputStream;   
import java.io.FileNotFoundException;   
import java.io.FileOutputStream;   
import java.io.IOException;   
import java.io.InputStream;   
import java.io.OutputStream;   
import java.util.Properties;   

public class PropertiesUtils {
	static String profilepath = "src/dbconfig.properties";
	/**
	 * ���þ�̬����
	 */
	private static Properties prop = new Properties();
	static {
		try {
			 prop.load(new FileInputStream(profilepath));   
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			System.exit(-1);
		}
	}

	/**
	 * ����properties�ļ��ļ�ֵ�� ����������Ѿ����ڣ����¸�������ֵ�� ��������������ڣ�����һ�Լ�ֵ��
	 * 
	 * @param keyname
	 *            ����
	 * @param keyvalue
	 *            ��ֵ
	 */
	public static void updateProperties(String keyname, String keyvalue) {
		try {
			 prop.load(new FileInputStream(profilepath));   
			// ���� Hashtable �ķ��� put��ʹ�� getProperty �����ṩ�����ԡ�
			// ǿ��Ҫ��Ϊ���Եļ���ֵʹ���ַ���������ֵ�� Hashtable ���� put �Ľ����
			OutputStream fos = new FileOutputStream(profilepath);
			prop.setProperty(keyname, keyvalue);
			// ���ʺ�ʹ�� load �������ص� Properties ���еĸ�ʽ��
			// ���� Properties ���е������б�����Ԫ�ضԣ�д�������
			prop.store(fos, "Update '" + keyname + "' value");
		} catch (IOException e) {
			System.err.println("�����ļ����´���");
		}
	}

	/**
	 * ����KEYȡ����Ӧ��value
	 * 
	 * @param key
	 * @return
	 */
	public static String getPropertyValue(String key) {
		return prop.getProperty(key);
	}
}