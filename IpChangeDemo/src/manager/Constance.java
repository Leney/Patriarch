package manager;

/**
 * 常量类
 * 
 * @author dell
 *
 */
public class Constance {
	
	/** 天天果园讯飞广告位id数组*/
	public static final String[] TTGY_AD_UNIT_IDS = {"72ADDD0A7D6DB73002B50127EFB9058E","4DC8E82FA7FD669A3F62C6838D9FFF6D","09840B44546AA78634331F0B5AADDDAC"};
	
	/**
     * 天天果园展示广告位控件分别对应的宽和高
     * [0]=全屏，[1]=banner,[2]=插屏,[3]=信息流
     */
    public static final int[][] TTGY_AD_WIDTHS_HEIGHT = {new int[]{1080, 1920},new int[]{1080, 270},new int[]{960, 640},new int[]{480, 320}};
    
    /**
     * 科大讯飞后台对应的应用appId
     */
    public static final String KDXF_APP_ID = "595b4ceb";
    public static final String KDXF_APP_NAME = "每日优鲜";
    public static final String KDXF_APP_PACKAGE_NAME = "cn.missfresh.application";
	
	
//	/** 当前设备的真正ip地址(用来判断是否设置代理成功的条件之一，因为有时设置代理不成功，获取当前ip会返回本机ip) */
//	public static String CUR_IP_ADDRESS = "";
//
//	/** 从芝麻代理服务器每一次获取到的所有代理ip、端口号的信息集合(每用完一次ip信息表明一个用户的一次app使用习惯) */
//	public static ListHelper<ProxyIpBean> newProxyIpBeanList = new ListHelper<>();
//	
//	/** 当前新获取的代理ip信息中使用过且有效的代理ip信息(因为芝麻代理的代理ip信息的有效时间很长, 记录这个是为了更有效利用代理ip，多次利用代理ip,不然太浪费)*/
//	public static ListHelper<ProxyIpBean> oldProxyIpBeanList = new ListHelper<>();
//	
//	/** ip使用过一次之后，上一次请求讯飞时所对应的设备信息集合(主要是为了增加pv/uv值,当再次使用某个ip请求讯飞时,保证和上次请求讯飞的设备信息保持一致)*/
//	public static Map<String, DeviceInfo> ipDeviceInfoMap = new Hashtable<>();
	
//	/** 记录当前正在使用的代理ip集合中所使用的index值(有可能是 newProxyIpBeanList 或者 validProxyIpBeanList,根据 isUseNewIp 判断)*/
//	public static AtomicInteger curIndex = new AtomicInteger(); 
	
//	/** 标识当前使用的是否是新获取的代理ip集合  true=使用新获取的代理ip集合(即:newProxyIpBeanList),false=使用已经使用过且有效的代理ip集合(即:validProxyIpBeanList)*/
//	public static AtomicBoolean isUseNewIp = new AtomicBoolean();
}
