import java.util.List;

import bean.ProxyIpBean;
import manager.Constance;
import util.NetUtil;
import util.ParseUtil;

public class Main {
	
	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String result = NetUtil.sendPost("http://http.dztwo.com/getip?", "num=10&type=2&pro=&city=0&yys=0&port=1&time=1");
				System.out.println("result------>>>"+result);
				List<ProxyIpBean> ipBeans = ParseUtil.parseIpBeans(result);
				
				
//				ProxyIpBean bean = ipBeans.get(0);
//				// 更改代理
//				NetUtil.changeProxy(bean.ip,bean.port);
				
//				// 获取ip
//				String proxyResult = NetUtil.getProxyIp(bean.ip,bean.port);
//				System.out.println("proxyResult------->>"+proxyResult);
				
//				String realResult = NetUtil.getRealIp(bean.ip,bean.port);
//				System.out.println("realResult------->>"+realResult);
				
				
				for (int i = 0; i < ipBeans.size(); i++) {
					ProxyIpBean bean = ipBeans.get(i);
					System.out.println("开始设置代理\n设置ip为------->>>"+bean.ip+"\n设置端口为------>>>"+bean.port);
					String proxyResult = NetUtil.getProxyIp(bean.ip,bean.port);
					System.out.println("proxyResult------->>"+proxyResult);
					
//					String realResult = NetUtil.getRealIp(bean.ip,bean.port);
//					System.out.println("realResult------->>"+realResult);
					
					System.out.println(" ");
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}).start();
	}
}
