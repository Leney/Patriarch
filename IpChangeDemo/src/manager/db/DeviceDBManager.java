package manager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import bean.DeviceInfo;
import manager.Constance;

public class DeviceDBManager {
	private static volatile DeviceDBManager instance = null;

	public static DeviceDBManager getInstance() {
		if (instance == null) {
			synchronized (DeviceDBManager.class) {
				if (instance == null) {
					instance = new DeviceDBManager();
				}
			}
		}
		return instance;
	}

	/**
	 * ��ȡ���ݿ����ܵ�����
	 * 
	 * @return
	 */
	public int getTotalCount() {
		int count = 0;
		try {
			Class.forName("org.sqlite.JDBC");
//			Connection conn = DriverManager.getConnection("jdbc:sqlite:E:\\db\\50wan.db"); // filenameΪsqlite���ݿ��ļ�������ô��Ͼ���·��
			Connection conn = DriverManager.getConnection("jdbc:sqlite:"+Constance.dbUrl+""); // filenameΪsqlite���ݿ��ļ�������ô��Ͼ���·��

			String queryAllSql = "Select count(_id) from device_tab;";
			// String sql = "insert into tb_users values('����','111','��','25')";
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(queryAllSql);
			count = resultSet.getInt(1);
			conn.close();
			return count;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * ����id�������ݿ��е�һ��Device����
	 *
	 * @return
	 */
	public DeviceInfo queryDeviceInfo(int id) {

		DeviceInfo deviceInfo = new DeviceInfo();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:"+Constance.dbUrl+""); // filenameΪsqlite���ݿ��ļ�������ô��Ͼ���·��

			String querySql = "Select * from device_tab where _id = " + id + ";";
			// String sql = "insert into tb_users values('����','111','��','25')";
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(querySql);
			deviceInfo.imei = resultSet.getString(2);
			deviceInfo.androidId = resultSet.getString(3);
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			deviceInfo = null;
		}
		return deviceInfo;
	}
}
