package util;

import java.util.List;

import bean.ProxyIpBean;
import manager.Constance;

public class Tools {

	/**
	 * ��ȡ����֥�����ip��Ϣ
	 */
	public static void getProxyIpList() {
		String result = NetUtil.sendPost("http://http.dztwo.com/getip?",
				"num=10&type=2&pro=&city=0&yys=0&port=1&time=1");
		List<ProxyIpBean> ipBeans = ParseUtil.parseIpBeans(result);
		Constance.newProxyIpBeanList.clear();
		// ����ȡ�����µĴ���ip���ϱ��浽ָ�������б���ȥ
		Constance.newProxyIpBeanList.addAll(ipBeans);
	}
}
