package manager.thread;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import bean.AdInfo;
import bean.DeviceInfo;
import bean.ProxyIpBean;
import manager.Constance;
import util.NetUtil;
import util.ParseUtil;
import util.Tools;
import util.listener.OnLoadAdListener;

/**
 * ˢ��������߳���
 * 
 * @author dell
 *
 */
public class AdTask implements Runnable {

	/**
	 * �豸��Ϣ
	 */
	private DeviceInfo deviceInfo;

	/**
	 * ����ip��Ϣ
	 */
	private ProxyIpBean ipBean;

	 /**
	 * ��ǰ�̵߳ı�ʶ
	 * �������� �磺1
	 */
	 private int threadNum;

	/**
	 * ���߳���Ҫˢ�Ĺ��λ����
	 */
	private String[] adUnitIds;

	/**
	 * Ѷ�ɺ�̨Ӧ�ö�Ӧ��appId
	 */
	private String appId;

	/**
	 * Ѷ�ɺ�̨Ӧ�ö�Ӧ��Ӧ������
	 */
	private String appName;

	/**
	 * Ѷ�ɺ�̨Ӧ�ö�Ӧ�İ���
	 */
	private String packageName;
	
	/**
	 * ��ʶ��ǰ�Ƿ���ʹ�õ��µļ����е�ip��Ϣ
	 */
	private boolean isUseNew = true;
	
	
	/**
	 * �»�ȡ�Ĵ���ip����
	 */
	private LinkedList<ProxyIpBean> newProxyIpBeanList = new LinkedList<>();
	
	/**
	 * ��ʹ�ù�һ�εĴ���ip����
	 */
	private LinkedList<ProxyIpBean> oldProxyIpBeanList = new LinkedList<>();
	
	/** ipʹ�ù�һ��֮����һ������Ѷ��ʱ����Ӧ���豸��Ϣ����(��Ҫ��Ϊ������pv/uvֵ,���ٴ�ʹ��ĳ��ip����Ѷ��ʱ,��֤���ϴ�����Ѷ�ɵ��豸��Ϣ����һ��)*/
	public static Map<String, DeviceInfo> ipDeviceInfoMap = new Hashtable<>();

	public AdTask(String[] adUnitIds, String appId, String appName, String packageName,int threadNum) {
		// this.deviceInfo = deviceInfo;
		this.adUnitIds = adUnitIds;
//		this.adWidthAndHeight = adWidthAndHeight;
		this.appId = appId;
		this.appName = appName;
		this.packageName = packageName;
		this.threadNum = threadNum;
	}
	
	@Override
	public void run() {
		while(true) {
			
			// ��ȡipBean��Ϣ
			if(isUseNew) {
				// ʹ���µĴ���ip�����е�����
				if(newProxyIpBeanList.isEmpty()) {
					// ���������Ϊ�գ�������µ�ip�Ѿ�����
					// ���֥���ȡһ���µĴ���ip����
					List<ProxyIpBean> ipBeans = Tools.getProxyIpList();
					newProxyIpBeanList.addAll(ipBeans);
					if(newProxyIpBeanList.isEmpty()) {
						// ��֥���̨��ȡ����ip��Ϣ��
						// ��������һ������
						continue;
					}
				}
				this.ipBean = newProxyIpBeanList.getFirst();
				if(this.ipBean == null) {
					newProxyIpBeanList.removeFirst();
					continue;
				}
			}else {
				// ʹ�õ��Ǿɵ���Ч��ip�����������
				if(oldProxyIpBeanList.isEmpty()) {
					// �ɵļ�����û��������,ʹ������
					isUseNew = true;
					continue;
				}else {
					this.ipBean = oldProxyIpBeanList.getFirst();
					if(this.ipBean == null) {
						oldProxyIpBeanList.removeFirst();
						continue;
					}
				}
			}
			
			
			// ipBeanΪ��  ����������������һ��ѭ��
			if(this.ipBean == null) {
				continue;
			}
//			&vendor=&dvw=480&devicetype=4&osv=4.1.2
			
			// ���ipBean�Ƿ���Ч
			if(isUseNew) {
				if(NetUtil.isValidProxyIp(ipBean)) {
					// ��Ч��ip
					System.out.println("��Чip---->>"+ipBean.ip);
					
					//TODO �����ݿ��л�ȡ�ֻ��豸��Ϣ
					this.deviceInfo = new DeviceInfo();
					deviceInfo.androidId = "114826349dd17ab0";
					deviceInfo.imei = "863288034320306";
					deviceInfo.mac = "00:71:f3:47:3c:ad";
					deviceInfo.userAgent = "Mozilla/5.0%20(Linux;%20U;%20Android%204.2.2;%20zh-CN;%20U59GT-C4B%20Build/JDQ39)%20AppleWebKit/537.36%20(KHTML,%20like%20Gecko)%20Version/4.0%20Chrome/40.0.2214.89%20UCBrowser/11.4.9.941%20Mobile%20Safari/537.36";
					deviceInfo.model = "meetuu%20G7";
					deviceInfo.language = "zh-CN";
					deviceInfo.deviceType = 0;
					deviceInfo.osVersion = "4.1";
					deviceInfo.density = "2.0";
					deviceInfo.operator = "46000";
					deviceInfo.net = 2;
					deviceInfo.deviceScreenWidth = 1080;
					deviceInfo.deviceScreenHeight = 1920;
					deviceInfo.orientation = 0;
					deviceInfo.vendor = "HuaWei";
					
				}else {
					// ��Ч��ip
					ipBean = null;
					continue;
				}
			}else {
				// �ǴӾɵ�ip��Ϣ������
				if(oldProxyIpBeanList.isEmpty()) {
					isUseNew = true;
					continue;
				}
				// ��ȡ����Ӧ���豸��Ϣ
				this.deviceInfo = ipDeviceInfoMap.get(ipBean.ip);
			}
			
			
			
			// deviceInfoΪ��  ����������������һ��ѭ��
			if(this.deviceInfo == null) {
				this.ipBean = null;
				continue;
			}
			
			
			for (int i = 0; i < adUnitIds.length; i++) {
				// ����ƴ�Ѷ������
				NetUtil.requestKDXFAdInfos(deviceInfo, ipBean, Constance.TTGY_AD_WIDTHS_HEIGHT[i], adUnitIds[i], i == 0 ? true:false, appId, appName, packageName, new OnLoadAdListener() {
					
					@Override
					public void onNetError(String msg) {
					}
					
					@Override
					public void onLoadSuccess(JSONObject resultObject, int[] showAdWidthAndHeight) {
						disposeGetAdSuccess(resultObject, showAdWidthAndHeight);
					}
					
					@Override
					public void onLoadFailed(String msg) {
					}
				});
			}
			
			
			// ���е��߼����������֮��
			if(isUseNew) {
				// ��ӵ���Ч��ip������ȥ
				oldProxyIpBeanList.add(ipBean);
				ipDeviceInfoMap.put(ipBean.ip, deviceInfo);
				// �Ƴ��µ�ip�����е�����
				newProxyIpBeanList.remove(ipBean);
			}else {
				// �Ƴ��ɵ�
				oldProxyIpBeanList.removeFirst();
				ipDeviceInfoMap.remove(ipBean.ip);
			}
			
			// �ж���һ��ѭ����ʹ���ĸ������е�����(�µĻ��Ǿɵ�)
			if(isUseNew) {
				if(newProxyIpBeanList.isEmpty() && oldProxyIpBeanList.size() > 1000) {
					// ���ôӾɵ�ip��Ϣ�����л�ȡ����
					isUseNew = false;
				}
			}else {
				if(oldProxyIpBeanList.isEmpty()) {
					ipDeviceInfoMap.clear();
					isUseNew = true;
				}
			}
			
			// �������ÿ�
			this.ipBean = null;
			this.deviceInfo = null;
		}
	}
	
	
	
	/**
	 * ��������Ѷ�ɹ��ɹ�֮��Ĳ���(�й������)
	 * @param resultObject
	 * @param showAdWidthAndHeight
	 */
	private void disposeGetAdSuccess(JSONObject resultObject,int[] showAdWidthAndHeight) {
		// �������ɹ�
		AdInfo adInfo = ParseUtil.getAdInfo(resultObject);
		System.out.println("adInfo == null------>>>"+(adInfo == null));
			if (adInfo == null) {
				return;
			}
			System.out.println("��ʼչʾ�ϱ�");
			// �ϱ�չʾ���ݳɹ� �����ϱ�����
			NetUtil.requestUrlsByProxy(ipBean,adInfo.getImprUrls());
			// ���ðٷ�֮6�ĵ����
			if (Tools.isClick(6)) {
				// ���������ĳ����� 4 ���� 5 ���һ��ʱ����е���ϱ�
				System.out.println("����һ��ʱ�� ׼���ϱ����url");
				// ������ʱ�����ȴ�һ��ʱ��Ĳ���
//				 int sleepTimeRandom = Tools.randomMinMax(1000, 6000);
//				 try {
//				 // ���������ʱ��
//				 Thread.sleep(sleepTimeRandom);
//				 } catch (InterruptedException e) {
//				 e.printStackTrace();
//				 }
			     // �����ϱ����
				 Tools.doClick(adInfo,this.ipBean,showAdWidthAndHeight,threadNum+"");
			}
	}
}
