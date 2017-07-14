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
	
	/** ͳ������Ѷ�ɵĴ���*/
	private int time;

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
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			// ��ȡipBean��Ϣ
			if(isUseNew) {
				System.out.println("ʹ�õ����µļ����е����ݣ������̱߳��---->>>"+threadNum);
				// ʹ���µĴ���ip�����е�����
				if(newProxyIpBeanList.isEmpty()) {
					System.out.println("�¼����е������ǿյģ���");
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
					System.out.println("���¼����еĻ�ȡ����ipBean�����ǿյģ���");
					newProxyIpBeanList.removeFirst();
					continue;
				}
			}else {
				System.out.println("ʹ�õ��Ǿɵļ����е����ݣ������̱߳��---->>>"+threadNum);
				// ʹ�õ��Ǿɵ���Ч��ip�����������
				if(oldProxyIpBeanList.isEmpty() || ipDeviceInfoMap.isEmpty()) {
					// �ɵļ�����û��������,ʹ������
					System.out.println("�ɵļ�����û��������,ʹ�����ˣ���");
					isUseNew = true;
					continue;
				}else {
					this.ipBean = oldProxyIpBeanList.getFirst();
					if(this.ipBean == null) {
						System.out.println("�Ӿɵļ����л�ȡ����ipBeanΪ�գ�����");
						oldProxyIpBeanList.removeFirst();
						continue;
					}
				}
			}
			
			
			// ipBeanΪ��  ����������������һ��ѭ��
			if(this.ipBean == null) {
				System.out.println("�¼����е������ǿյģ���");
				continue;
			}
			// ���ipBean�Ƿ���Ч
			if(isUseNew) {
				if(NetUtil.isValidProxyIp(ipBean)) {
					// ��Ч��ip
					System.out.println("��ip��Ч---->>"+ipBean.ip);
					
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
					System.out.println("��ip��Ч---->>"+ipBean.ip);
					newProxyIpBeanList.removeFirst();
					ipBean = null;
					continue;
				}
			}else {
				// �ǴӾɵ�ip��Ϣ������
//				if(oldProxyIpBeanList.isEmpty()) {
//					isUseNew = true;
//					continue;
//				}
				if(ipDeviceInfoMap.isEmpty()) {
					oldProxyIpBeanList.clear();
					isUseNew = true;
					continue;
				}
				// ��ȡ����Ӧ���豸��Ϣ
				this.deviceInfo = ipDeviceInfoMap.get(ipBean.ip);
				if(deviceInfo == null) {
					ipDeviceInfoMap.clear();
					oldProxyIpBeanList.clear();
					isUseNew = true;
					continue;
				}
			}
			
			
			
			// deviceInfoΪ��  ����������������һ��ѭ��
			if(this.deviceInfo == null) {
				System.out.println("�õ����豸��ϢdeviceInfo�ǿյģ�����");
				this.ipBean = null;
				continue;
			}
			
			
			for (int i = 0; i < adUnitIds.length; i++) {
				// ����ƴ�Ѷ������
				time ++;
				System.out.println("��ǰ�����Ѷ�ɵ��ܴ���----->>>"+time);
				NetUtil.requestKDXFAdInfos(deviceInfo, ipBean, Constance.TTGY_AD_WIDTHS_HEIGHT[i], adUnitIds[i], i == 0 ? true:false, appId, appName, packageName, new OnLoadAdListener() {
					
					@Override
					public void onNetError(String msg) {
					}
					
					@Override
					public void onLoadSuccess(JSONObject resultObject, int[] showAdWidthAndHeight) {
						System.out.println("�й�����ݣ����̱߳��---->>>"+threadNum);
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
				if(newProxyIpBeanList.isEmpty() && oldProxyIpBeanList.size() >= 5) {
					// ���ôӾɵ�ip��Ϣ�����л�ȡ����
					isUseNew = false;
					System.out.println("׼����ʼʹ�þɵļ����е�������Ϣ������");
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
//			if (Tools.isClick(6)) {
				// ���������ĳ����� 4 ���� 5 ���һ��ʱ����е���ϱ�
				System.out.println("����һ��ʱ�� ׼���ϱ����url");
//				// ������ʱ�����ȴ�һ��ʱ��Ĳ���
//				 int sleepTimeRandom = Tools.randomMinMax(1000, 6000);
//				 try {
//				 // ���������ʱ��
//				 Thread.sleep(2000);
//				 } catch (InterruptedException e) {
//				 e.printStackTrace();
//				 }
			     // �����ϱ����
				 Tools.doClick(adInfo,this.ipBean,showAdWidthAndHeight,threadNum+"");
//			}
	}
}
