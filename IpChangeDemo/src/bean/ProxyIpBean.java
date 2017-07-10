package bean;

/**
 * 获取到的代理ip对象实例
 * @author dell
 *
 */
public class ProxyIpBean {
	public String ip;
	/** 端口号*/
	public int port;
	/**过期时间戳*/
	public long expire_time;
	/** 过期时间(字符串形式)*/
	public String expire_time_str;
	public String city;
	/** 运营商名称*/
	public String isp;
}
