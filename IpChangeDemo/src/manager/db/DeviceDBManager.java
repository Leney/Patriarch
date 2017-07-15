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
	 * 获取数据库中总的条数
	 * 
	 * @return
	 */
	public int getTotalCount() {
		int count = 0;
		try {
			Class.forName("org.sqlite.JDBC");
//			Connection conn = DriverManager.getConnection("jdbc:sqlite:E:\\db\\50wan.db"); // filename为sqlite数据库文件名，最好带上绝对路径
			Connection conn = DriverManager.getConnection("jdbc:sqlite:"+Constance.dbUrl+""); // filename为sqlite数据库文件名，最好带上绝对路径

			String queryAllSql = "Select count(_id) from device_tab;";
			// String sql = "insert into tb_users values('张三','111','男','25')";
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
	 * 根据id查找数据库中的一条Device数据
	 *
	 * @return
	 */
	public DeviceInfo queryDeviceInfo(int id) {

		DeviceInfo deviceInfo = new DeviceInfo();
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:"+Constance.dbUrl+""); // filename为sqlite数据库文件名，最好带上绝对路径

			String querySql = "Select * from device_tab where _id = " + id + ";";
			// String sql = "insert into tb_users values('张三','111','男','25')";
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
