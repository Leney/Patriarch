package manager;

/**
 * ������
 * 
 * @author dell
 *
 */
public class Constance {
	
	/** �����԰Ѷ�ɹ��λid����*/
	public static final String[] TTGY_AD_UNIT_IDS = {"72ADDD0A7D6DB73002B50127EFB9058E","4DC8E82FA7FD669A3F62C6838D9FFF6D","09840B44546AA78634331F0B5AADDDAC"};
	
	/**
     * �����԰չʾ���λ�ؼ��ֱ��Ӧ�Ŀ�͸�
     * [0]=ȫ����[1]=banner,[2]=����,[3]=��Ϣ��
     */
    public static final int[][] TTGY_AD_WIDTHS_HEIGHT = {new int[]{1080, 1920},new int[]{1080, 270},new int[]{960, 640},new int[]{480, 320}};
    
    /**
     * �ƴ�Ѷ�ɺ�̨��Ӧ��Ӧ��appId
     */
    public static final String KDXF_APP_ID = "595b4ceb";
    public static final String KDXF_APP_NAME = "ÿ������";
    public static final String KDXF_APP_PACKAGE_NAME = "cn.missfresh.application";
	
	
//	/** ��ǰ�豸������ip��ַ(�����ж��Ƿ����ô���ɹ�������֮һ����Ϊ��ʱ���ô����ɹ�����ȡ��ǰip�᷵�ر���ip) */
//	public static String CUR_IP_ADDRESS = "";
//
//	/** ��֥����������ÿһ�λ�ȡ�������д���ip���˿ںŵ���Ϣ����(ÿ����һ��ip��Ϣ����һ���û���һ��appʹ��ϰ��) */
//	public static ListHelper<ProxyIpBean> newProxyIpBeanList = new ListHelper<>();
//	
//	/** ��ǰ�»�ȡ�Ĵ���ip��Ϣ��ʹ�ù�����Ч�Ĵ���ip��Ϣ(��Ϊ֥�����Ĵ���ip��Ϣ����Чʱ��ܳ�, ��¼�����Ϊ�˸���Ч���ô���ip��������ô���ip,��Ȼ̫�˷�)*/
//	public static ListHelper<ProxyIpBean> oldProxyIpBeanList = new ListHelper<>();
//	
//	/** ipʹ�ù�һ��֮����һ������Ѷ��ʱ����Ӧ���豸��Ϣ����(��Ҫ��Ϊ������pv/uvֵ,���ٴ�ʹ��ĳ��ip����Ѷ��ʱ,��֤���ϴ�����Ѷ�ɵ��豸��Ϣ����һ��)*/
//	public static Map<String, DeviceInfo> ipDeviceInfoMap = new Hashtable<>();
	
//	/** ��¼��ǰ����ʹ�õĴ���ip��������ʹ�õ�indexֵ(�п����� newProxyIpBeanList ���� validProxyIpBeanList,���� isUseNewIp �ж�)*/
//	public static AtomicInteger curIndex = new AtomicInteger(); 
	
//	/** ��ʶ��ǰʹ�õ��Ƿ����»�ȡ�Ĵ���ip����  true=ʹ���»�ȡ�Ĵ���ip����(��:newProxyIpBeanList),false=ʹ���Ѿ�ʹ�ù�����Ч�Ĵ���ip����(��:validProxyIpBeanList)*/
//	public static AtomicBoolean isUseNewIp = new AtomicBoolean();
}
