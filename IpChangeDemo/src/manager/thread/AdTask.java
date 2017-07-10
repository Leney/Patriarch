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

	public AdTask(String[] adUnitIds, int[][] adWidthAndHeight, String appId, String appName, String packageName) {
		// this.deviceInfo = deviceInfo;
		this.adUnitIds = adUnitIds;
		this.adWidthAndHeight = adWidthAndHeight;
		this.appId = appId;
		this.appName = appName;
		this.packageName = packageName;
		// this.threadNum = threadNum;
	}

	@Override
	public void run() {

		if (Constance.isUseNewIp.get()) {
			// ���ô�֥������ȡ���µĴ���ip��Ϣ
			if (!Constance.newProxyIpBeanList.isEmpty()) {
				// �µĴ���ip������û���˴���ip��Ϣ��
				if (Constance.validProxyIpBeanList.size() > 1000) {
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
