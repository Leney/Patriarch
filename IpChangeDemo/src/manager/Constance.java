package manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import bean.DeviceInfo;
import bean.ProxyIpBean;
import util.ListHelper;

/**
 * ������
 * 
 * @author dell
 *
 */
public class Constance {
	
	/** �����԰Ѷ�ɹ��λid����*/
	public static final String[] TTGY_AD_UNIT_IDS = {"","","",""};
	
	/**
     * �����԰չʾ���λ�ؼ��ֱ��Ӧ�Ŀ�͸�
     * [0]=ȫ����[1]=banner,[2]=����,[3]=��Ϣ��
     */
    public static final int[][] TTGY_AD_WIDTHS_HEIGHT = {new int[]{1080, 1920},new int[]{1080, 270},new int[]{960, 640},new int[]{480, 320}};
	
	
	/** ��ǰ�豸������ip��ַ(�����ж��Ƿ����ô���ɹ�������֮һ����Ϊ��ʱ���ô����ɹ�����ȡ��ǰip�᷵�ر���ip) */
	public static String CUR_IP_ADDRESS = "";

	/** ��֥����������ÿһ�λ�ȡ�������д���ip���˿ںŵ���Ϣ����(ÿ����һ��ip��Ϣ����һ���û���һ��appʹ��ϰ��) */
	public static ListHelper<ProxyIpBean> newProxyIpBeanList = new ListHelper<>();
	
	/** ��ǰ�»�ȡ�Ĵ���ip��Ϣ��ʹ�ù�����Ч�Ĵ���ip��Ϣ(��Ϊ֥�����Ĵ���ip��Ϣ����Чʱ��ܳ�, ��¼�����Ϊ�˸���Ч���ô���ip��������ô���ip,��Ȼ̫�˷�)*/
	public static ListHelper<ProxyIpBean> oldProxyIpBeanList = new ListHelper<>();
	
	/** ipʹ�ù�һ��֮����һ������Ѷ��ʱ����Ӧ���豸��Ϣ����(��Ҫ��Ϊ������pv/uvֵ,���ٴ�ʹ��ĳ��ip����Ѷ��ʱ,��֤���ϴ�����Ѷ�ɵ��豸��Ϣ����һ��)*/
	public static Map<String, DeviceInfo> ipDeviceInfoMap = new Hashtable<>();
	
//	/** ��¼��ǰ����ʹ�õĴ���ip��������ʹ�õ�indexֵ(�п����� newProxyIpBeanList ���� validProxyIpBeanList,���� isUseNewIp �ж�)*/
//	public static AtomicInteger curIndex = new AtomicInteger(); 
	
//	/** ��ʶ��ǰʹ�õ��Ƿ����»�ȡ�Ĵ���ip����  true=ʹ���»�ȡ�Ĵ���ip����(��:newProxyIpBeanList),false=ʹ���Ѿ�ʹ�ù�����Ч�Ĵ���ip����(��:validProxyIpBeanList)*/
//	public static AtomicBoolean isUseNewIp = new AtomicBoolean();
}
