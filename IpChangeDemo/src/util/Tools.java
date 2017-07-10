package util;

import java.util.List;

import bean.ProxyIpBean;
import manager.Constance;

public class Tools {

	/**
	 * 获取更多芝麻代理ip信息
	 */
	public static void getProxyIpList() {
		String result = NetUtil.sendPost("http://http.dztwo.com/getip?",
				"num=10&type=2&pro=&city=0&yys=0&port=1&time=1");
		List<ProxyIpBean> ipBeans = ParseUtil.parseIpBeans(result);
		Constance.newProxyIpBeanList.clear();
		// 将获取到的新的代理ip集合保存到指定集合列表中去
		Constance.newProxyIpBeanList.addAll(ipBeans);
	}
}
