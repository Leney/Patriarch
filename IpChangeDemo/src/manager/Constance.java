package manager;

/**
 * 常量类
 * 
 * @author dell
 *
 */
public class Constance {

	/** 天天果园讯飞广告位id数组 */
	public static final String[] TTGY_AD_UNIT_IDS = { "72ADDD0A7D6DB73002B50127EFB9058E",
			"4DC8E82FA7FD669A3F62C6838D9FFF6D", "09840B44546AA78634331F0B5AADDDAC" };

	/**
	 * 天天果园展示广告位控件分别对应的宽和高 [0]=全屏，[1]=banner,[2]=插屏,[3]=信息流
	 */
	public static final int[][] TTGY_AD_WIDTHS_HEIGHT = { new int[] { 1080, 1920 }, new int[] { 1080, 270 },
			new int[] { 960, 640 }, new int[] { 480, 320 } };

	/**
	 * 科大讯飞后台对应的应用appId
	 */
	public static final String KDXF_APP_ID = "595b4ceb";
	public static final String KDXF_APP_NAME = "每日优鲜";
	public static final String KDXF_APP_PACKAGE_NAME = "cn.missfresh.application";

	/** 当前数据库的总条数 */
	public static int DB_TOTAL_DATA_COUNT = 0;

	// /** 当前设备的真正ip地址(用来判断是否设置代理成功的条件之一，因为有时设置代理不成功，获取当前ip会返回本机ip) */
	// public static String CUR_IP_ADDRESS = "";
	//
	// /** 从芝麻代理服务器每一次获取到的所有代理ip、端口号的信息集合(每用完一次ip信息表明一个用户的一次app使用习惯) */
	// public static ListHelper<ProxyIpBean> newProxyIpBeanList = new
	// ListHelper<>();
	//
	// /** 当前新获取的代理ip信息中使用过且有效的代理ip信息(因为芝麻代理的代理ip信息的有效时间很长,
	// 记录这个是为了更有效利用代理ip，多次利用代理ip,不然太浪费)*/
	// public static ListHelper<ProxyIpBean> oldProxyIpBeanList = new
	// ListHelper<>();
	//
	// /**
	// ip使用过一次之后，上一次请求讯飞时所对应的设备信息集合(主要是为了增加pv/uv值,当再次使用某个ip请求讯飞时,保证和上次请求讯飞的设备信息保持一致)*/
	// public static Map<String, DeviceInfo> ipDeviceInfoMap = new
	// Hashtable<>();

	// /** 记录当前正在使用的代理ip集合中所使用的index值(有可能是 newProxyIpBeanList 或者
	// validProxyIpBeanList,根据 isUseNewIp 判断)*/
	// public static AtomicInteger curIndex = new AtomicInteger();

	// /** 标识当前使用的是否是新获取的代理ip集合
	// true=使用新获取的代理ip集合(即:newProxyIpBeanList),false=使用已经使用过且有效的代理ip集合(即:validProxyIpBeanList)*/
	// public static AtomicBoolean isUseNewIp = new AtomicBoolean();

	/**
	 * UserAgent
	 */
	public static final String[] USER_AGENTS = {
			"Mozilla/5.0 (Linux; U; Android OS_VERSION; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
			"MQQBrowser/26 Mozilla/5.0 (Linux; U; Android OS_VERSION; zh-cn; MB200 Build/GRJ22; CyanogenMod-7) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
			"JUC (Linux; U; OS_VERSION; zh-cn; MB200; 320*480) UCWEB7.9.3.103/139/999",
			"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:7.0a1) Gecko/20110623 Firefox/7.0a1 Fennec/7.0a1",
			"Opera/9.80 (Android OS_VERSION; Linux; Opera Mobi/build-1107180945; U; en-GB) Presto/2.8.149 Version/11.10" };

	/**
	 * Android 系统版本
	 */
	public static final String[] OS_VERSIONS = { "2.2.1", "2.2.2", "2.2.3", "2.2.4", "2.3.3", "2.3.4", "2.3.7", "4.0.3",
			"4.0.4", "4.1.1", "4.1.2", "4.1.3", "4.1.4", "4.1.5", "4.2.1", "4.2.2", "4.2.3", "4.2.4", "4.3.1", "4.3.2",
			"4.3.3", "4.4.1", "4.4.2", "4.4.3", "4.4.4", "5.0.1", "5.0.2", "5.0.3", "5.1.1", "5.1.2", "5.1.3", "6.0.1",
			"6.0.2", "6.0.3", "7.0.1", "7.0.2", "7.1.1" };

	/**
	 * Android 屏幕density
	 */
	public static final String[] DENSITY = { "0.75", "1.0", "1.5", "2.0", "2.5", "3.0", "4.0" };

	/**
	 * 运营商的编号 移动最多，其次联通，最后电信
	 */
	public static final String[] OPERATORS = { "46000", "46000", "46000", "46001", "46001", "46003" };

	/**
	 * 常见手机屏幕分辨率 主流屏幕分辨率会多一些
	 */
	public static final int[][] SCREEN_RESSOLUTION = { new int[] { 240, 320 }, new int[] { 320, 480 },
			new int[] { 480, 800 }, new int[] { 480, 800 }, new int[] { 480, 854 }, new int[] { 540, 960 },
			new int[] { 720, 1280 }, new int[] { 720, 1280 }, new int[] { 720, 1280 }, new int[] { 1080, 1920 },
			new int[] { 1080, 1920 }, new int[] { 1080, 1920 } };

}
