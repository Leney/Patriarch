package manager.thread;

import bean.DeviceInfo;
import bean.ProxyIpBean;
import manager.Constance;
import util.NetUtil;
import util.Tools;

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

	// /**
	// * ��ǰ�̵߳ı�ʶ
	// * �������� �磺1
	// */
	// private int threadNum;

	/**
	 * ���߳���Ҫˢ�Ĺ��λ����
	 */
	private String[] adUnitIds;

	/**
	 * ���߳���Ҫ���Ĺ��չʾ�Ŀ�͸�
	 */
	private int[][] adWidthAndHeight;

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

	public AdTask(String[] adUnitIds, int[][] adWidthAndHeight, String appId, String appName, String packageName) {
		// this.deviceInfo = deviceInfo;
		this.adUnitIds = adUnitIds;
		this.adWidthAndHeight = adWidthAndHeight;
		this.appId = appId;
		this.appName = appName;
		this.packageName = packageName;
		// this.threadNum = threadNum;
	}
	
	private void init() {
		// ��ʶ�Ƿ���ʹ���µļ����е�ip��Ϣ
		if(Constance.newProxyIpBeanList.isEmpty()) {
			// ���������Ϊ�գ�������µ�ip�Ѿ�����
			if(Constance.oldProxyIpBeanList.size()>1000) {
				// �����Ч�ļ����У��Ѿ�����1000���ˣ����ٴ�ʹ��֮ǰ�������Ч��ip��Ϣ
				this.ipBean = Constance.oldProxyIpBeanList.getFirst();
				isUseNew = true;
			}else {
				// �����Ч�ļ����У���δװ��1000�����ݣ���֥������л�ȡ�µ�һ��ip��Ϣ
				Tools.getProxyIpList();
				if(Constance.newProxyIpBeanList.isEmpty()) {
					// ��֥���̨��ȡ����ip��Ϣ��
					// ��������һ������
					init();
					return;
				}
				this.ipBean = Constance.newProxyIpBeanList.getFirst();
				isUseNew = false;
			}
		}else {
			// �µ�ip�����л�������
			this.ipBean = Constance.newProxyIpBeanList.getFirst();
			isUseNew = true;
		}
		
		if(this.ipBean == null) {
			init();
			return;
		}
		
		
		if(isUseNew) {
			// ֻ����ʹ���µļ����е�ip��Ϣʱ��ȥ���ip�Ƿ�����Ч��
			if(NetUtil.isValidProxyIp(ipBean)) {
				// ����Ч��ipBean���뵽��Ч������ȥ
				Constance.oldProxyIpBeanList.add(ipBean);
				// ���Ƴ����µ�ip�����е�����
				Constance.newProxyIpBeanList.remove(ipBean);
				// TODO �����ݿ��л�ȡ�ֻ��豸��Ϣ
//				this.deviceInfo = 
			}else {
				// �������Ч��  ���ٳ�ʼ��һ�Σ���ȡipBean��DeviceInfo
				init();
			}
		}else {
			// ʹ�õ�����Ч�����е�����
			
		}
		
	}

	@Override
	public void run() {
		while(true) {
			
			// ��ȡipBean��Ϣ
			if(isUseNew) {
				// ʹ���µĴ���ip�����е�����
				if(Constance.newProxyIpBeanList.isEmpty()) {
					// ���������Ϊ�գ�������µ�ip�Ѿ�����
					// ���֥���ȡһ���µĴ���ip����
					Tools.getProxyIpList();
					if(Constance.newProxyIpBeanList.isEmpty()) {
						// ��֥���̨��ȡ����ip��Ϣ��
						// ��������һ������
						continue;
					}
				}
				this.ipBean = Constance.newProxyIpBeanList.getFirst();
				if(this.ipBean == null) {
					continue;
				}
			}else {
				// ʹ�õ��Ǿɵ���Ч��ip�����������
				if(Constance.oldProxyIpBeanList.isEmpty()) {
					// �ɵļ�����û��������,ʹ������
					isUseNew = true;
					continue;
				}else {
					this.ipBean = Constance.oldProxyIpBeanList.getFirst();
					if(this.ipBean == null) {
						continue;
					}
				}
			}
			
			// ���ipBean�Ƿ���Ч
			if(isUseNew) {
				if(NetUtil.isValidProxyIp(ipBean)) {
					// ��Ч��ip
					// ��ӵ���Ч��ip������ȥ
					Constance.oldProxyIpBeanList.add(ipBean);
					Constance.ipDeviceInfoMap.put(ipBean.ip, deviceInfo);
					// �Ƴ��µ�ip�����е�����
					Constance.newProxyIpBeanList.remove(ipBean);
				}else {
					// ��Ч��ip
					ipBean = null;
					continue;
				}
			}else {
				// �ǴӾɵ�ip��Ϣ������
				if(Constance.oldProxyIpBeanList.isEmpty()) {
					isUseNew = true;
					continue;
				}
			}
			
			
			
			// ��ȡdeviceInfo
			if(isUseNew) {
				// ���µ�ip�����л�ȡ����
				// TODO ����������ݿ��л�ȡ�豸��Ϣ
//				this.deviceInfo = 
				if(this.deviceInfo == null) {
					ipBean = null;
					continue;
				}
			}else {
				// �Ӿɵ�ip�����л�ȡ����
				this.deviceInfo = Constance.ipDeviceInfoMap.get(ipBean.ip);
				if(deviceInfo == null) {
					ipBean = null;
					continue;
				}
			}
			
			
			
			
			
			// ���е��߼����������֮��
			if(isUseNew) {
				if(Constance.newProxyIpBeanList.isEmpty() && Constance.oldProxyIpBeanList.size() > 1000) {
					// ���ôӾɵ�ip��Ϣ�����л�ȡ����
					isUseNew = false;
				}
			}else {
				Constance.oldProxyIpBeanList.remove(ipBean);
				Constance.ipDeviceInfoMap.remove(ipBean.ip);
			}
			
			// �������ÿ�
			this.ipBean = null;
			this.deviceInfo = null;
		}
		
		

		if (Constance.isUseNewIp.get()) {
			// ���ô�֥������ȡ���µĴ���ip��Ϣ
			if (!Constance.newProxyIpBeanList.isEmpty()) {
				// �µĴ���ip������û���˴���ip��Ϣ��
				if (Constance.oldProxyIpBeanList.size() > 1000) {
					// �������Ч�Ĵ���ip�Ѿ�������1000�� ��ȥʹ��֮ǰ�������Ч�Ĵ���ip��Ϣ����Ч���ô���ip��Ϣ
					Constance.isUseNewIp.set(false);
				} else {
					// ����ȥ��ȡ����Ĵ���ip��Ϣ
					Tools.getProxyIpList();
				}
			} else {
				// �µĴ���ip�����л��д���ip��Ϣ����
				// ��ȡ��Ҫʹ�õĴ���ip��Ϣ����
				ProxyIpBean tempIpBean = Constance.newProxyIpBeanList.getFirst();
				// ������ip�Ƿ���Ч
				if (NetUtil.isValidProxyIp(tempIpBean)) {
					// ����Ч�Ĵ���ip
					this.ipBean = tempIpBean;

					// TODO ����������ݿ��л�ȡ�豸��Ϣ
					// TODO
					DeviceInfo tempDeviceInfo = new DeviceInfo();

					if (tempDeviceInfo == null) {
						// ֱ�ӷ��ػ����ٴ�ȥ��ȡ�ֻ��豸��Ϣ���ݣ�����
						return;
					}
					// ����Ч�Ĵ���ip��Ϣ��ӵ�������ȥ
					Constance.validProxyIpBeanList.add(tempIpBean);
					// ������ip��Ϣ���ֻ��豸��Ϣ��ӵ���Ӧ��Map������ȥ���Ա�֮���ٴ�ʹ��
					Constance.ipDeviceInfoMap.put(tempIpBean.ip, tempDeviceInfo);
					this.deviceInfo = tempDeviceInfo;
				} else {
					// ������Ч�Ĵ���ip,���Ƴ���ǰ�Ĵ���ip��Ϣ
					Constance.newProxyIpBeanList.remove(tempIpBean);
				}
			}
		} else {
			// ��ʹ��֮ǰ�������Ч�Ĵ���ip��Ϣ
			if (!Constance.validProxyIpBeanList.isEmpty()) {
				this.ipBean = Constance.validProxyIpBeanList.getFirst();
				this.deviceInfo = Constance.ipDeviceInfoMap.get(ipBean.ip);
			} else {
				// �������Ч����ip��Ϣ�Ѿ�ʹ����
				// ����ȥ��ȡ����Ĵ���ip��Ϣ
				Tools.getProxyIpList();
				Constance.isUseNewIp.set(true);
			}
		}

		// final int length = adUnitIds.length;
		// for (int i = 0; i < length; i++) {
		// int[] adWidthHeight = adWidthAndHeight[i];
		// if (adWidthHeight == null) {
		// return;
		// }
		//
		// final int curIndex = i;
		// NetUtil.requestKDXFAdInfos(deviceInfo, ipBean, adUnitIds[i],
		// adWidthHeight[0], adWidthHeight[1],
		// i == 0 ? true : false, appId, appName, packageName, new
		// OnLoadAdListener() {
		//
		// @Override
		// public void onLoadSuccess(JSONObject resultObject, int adWidth, int
		// adHeight) {
		// // �������ɹ�
		// AdInfo adInfo = ParseUtil.getAdInfo(resultObject);
		// if (adInfo == null) {
		// return;
		// }
		// // �ϱ�չʾ���ݳɹ� �����ϱ�����
		// NetUtil.requestUrlsByProxy(ipBean,adInfo.getImprUrls());
		//
		// // ���ðٷ�֮6�ĵ����
		// if (Tools.isClick(6)) {
		// // ���������ĳ����� 4 ���� 5 ���һ��ʱ����е���ϱ�
		//
		// Log.i("llj", "����һ��ʱ�� ׼���ϱ����url");
		// int sleepTimeRandom = Tools.randomMinMax(1000, 6000);
		// try {
		// // ���������ʱ��
		// Thread.sleep(sleepTimeRandom);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// // �����ϱ����
		// Tools.doClick(adInfo, Constance.curDeviceInfo, adWidth, adHeight);
		// }
		//
		// if (curIndex == length - 1) {
		// // ���һ��ѭ��������� �˴��豸�����й���������
		// switch (threadNum) {
		// case 1:
		// Constance.IS_FIRST_THREAD_DONE = true;
		// break;
		// case 2:
		// Constance.IS_SECOND_THREAD_DONE = true;
		// break;
		// case 3:
		// Constance.IS_THIRED_THREAD_DONE = true;
		// break;
		// }
		// }
		//
		// if (Constance.IS_FIRST_THREAD_DONE && Constance.IS_SECOND_THREAD_DONE
		// && Constance.IS_THIRED_THREAD_DONE) {
		// // 3���̶߳�������
		// // TODO ֪ͨ�л�ip
		// }
		// }
		//
		// @Override
		// public void onNetError(String msg) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void onLoadFailed(String msg) {
		// if (curIndex == length - 1) {
		// // ���һ��ѭ��������� �˴��豸�����й���������
		// switch (threadNum) {
		// case 1:
		// Constance.IS_FIRST_THREAD_DONE = true;
		// break;
		// case 2:
		// Constance.IS_SECOND_THREAD_DONE = true;
		// break;
		// case 3:
		// Constance.IS_THIRED_THREAD_DONE = true;
		// break;
		// }
		// }
		//
		// if (Constance.IS_FIRST_THREAD_DONE && Constance.IS_SECOND_THREAD_DONE
		// && Constance.IS_THIRED_THREAD_DONE) {
		// // 3���̶߳�������
		// // TODO ֪ͨ�л�ip
		// }
		// }
		// });
		// }
	}
}
