package manager;

/**
 * ������
 * 
 * @author dell
 *
 */
public class Constance {

	/** �����԰Ѷ�ɹ��λid���� */
	public static final String[] TTGY_AD_UNIT_IDS = { "72ADDD0A7D6DB73002B50127EFB9058E",
			"4DC8E82FA7FD669A3F62C6838D9FFF6D", "09840B44546AA78634331F0B5AADDDAC" };

	/**
	 * �����԰չʾ���λ�ؼ��ֱ��Ӧ�Ŀ�͸� [0]=ȫ����[1]=banner,[2]=����,[3]=��Ϣ��
	 */
	public static final int[][] TTGY_AD_WIDTHS_HEIGHT = { new int[] { 1080, 1920 }, new int[] { 1080, 270 },
			new int[] { 960, 640 }, new int[] { 480, 320 } };

	/**
	 * �ƴ�Ѷ�ɺ�̨��Ӧ��Ӧ��appId
	 */
	public static final String KDXF_APP_ID = "595b4ceb";
	public static final String KDXF_APP_NAME = "ÿ������";
	public static final String KDXF_APP_PACKAGE_NAME = "cn.missfresh.application";

	/** ��ǰ���ݿ�������� */
	public static int DB_TOTAL_DATA_COUNT = 0;

	// /** ��ǰ�豸������ip��ַ(�����ж��Ƿ����ô���ɹ�������֮һ����Ϊ��ʱ���ô����ɹ�����ȡ��ǰip�᷵�ر���ip) */
	// public static String CUR_IP_ADDRESS = "";
	//
	// /** ��֥����������ÿһ�λ�ȡ�������д���ip���˿ںŵ���Ϣ����(ÿ����һ��ip��Ϣ����һ���û���һ��appʹ��ϰ��) */
	// public static ListHelper<ProxyIpBean> newProxyIpBeanList = new
	// ListHelper<>();
	//
	// /** ��ǰ�»�ȡ�Ĵ���ip��Ϣ��ʹ�ù�����Ч�Ĵ���ip��Ϣ(��Ϊ֥�����Ĵ���ip��Ϣ����Чʱ��ܳ�,
	// ��¼�����Ϊ�˸���Ч���ô���ip��������ô���ip,��Ȼ̫�˷�)*/
	// public static ListHelper<ProxyIpBean> oldProxyIpBeanList = new
	// ListHelper<>();
	//
	// /**
	// ipʹ�ù�һ��֮����һ������Ѷ��ʱ����Ӧ���豸��Ϣ����(��Ҫ��Ϊ������pv/uvֵ,���ٴ�ʹ��ĳ��ip����Ѷ��ʱ,��֤���ϴ�����Ѷ�ɵ��豸��Ϣ����һ��)*/
	// public static Map<String, DeviceInfo> ipDeviceInfoMap = new
	// Hashtable<>();

	// /** ��¼��ǰ����ʹ�õĴ���ip��������ʹ�õ�indexֵ(�п����� newProxyIpBeanList ����
	// validProxyIpBeanList,���� isUseNewIp �ж�)*/
	// public static AtomicInteger curIndex = new AtomicInteger();

	// /** ��ʶ��ǰʹ�õ��Ƿ����»�ȡ�Ĵ���ip����
	// true=ʹ���»�ȡ�Ĵ���ip����(��:newProxyIpBeanList),false=ʹ���Ѿ�ʹ�ù�����Ч�Ĵ���ip����(��:validProxyIpBeanList)*/
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
	 * Android ϵͳ�汾
	 */
	public static final String[] OS_VERSIONS = { "2.2.1", "2.2.2", "2.2.3", "2.2.4", "2.3.3", "2.3.4", "2.3.7", "4.0.3",
			"4.0.4", "4.1.1", "4.1.2", "4.1.3", "4.1.4", "4.1.5", "4.2.1", "4.2.2", "4.2.3", "4.2.4", "4.3.1", "4.3.2",
			"4.3.3", "4.4.1", "4.4.2", "4.4.3", "4.4.4", "5.0.1", "5.0.2", "5.0.3", "5.1.1", "5.1.2", "5.1.3", "6.0.1",
			"6.0.2", "6.0.3", "7.0.1", "7.0.2", "7.1.1" };

	/**
	 * Android ��Ļdensity
	 */
	public static final String[] DENSITY = { "0.75", "1.0", "1.5", "2.0", "2.5", "3.0", "4.0" };

	/**
	 * ��Ӫ�̵ı�� �ƶ���࣬�����ͨ��������
	 */
	public static final String[] OPERATORS = { "46000", "46000", "46000", "46001", "46001", "46003" };

	/**
	 * �����ֻ���Ļ�ֱ��� ������Ļ�ֱ��ʻ��һЩ
	 */
	public static final int[][] SCREEN_RESSOLUTION = { new int[] { 240, 320 }, new int[] { 320, 480 },
			new int[] { 480, 800 }, new int[] { 480, 800 }, new int[] { 480, 854 }, new int[] { 540, 960 },
			new int[] { 720, 1280 }, new int[] { 720, 1280 }, new int[] { 720, 1280 }, new int[] { 1080, 1920 },
			new int[] { 1080, 1920 }, new int[] { 1080, 1920 } };

}
