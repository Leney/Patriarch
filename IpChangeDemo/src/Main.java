import manager.Constance;
import manager.thread.AdTask;
import manager.thread.ThreadPoolManager;

public class Main {
	
	public static void main(String[] args) {
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				String result = NetUtil.sendPost("http://http.dztwo.com/getip?", "num=10&type=2&pro=&city=0&yys=0&port=1&time=1");
//				System.out.println("result------>>>"+result);
//				List<ProxyIpBean> ipBeans = ParseUtil.parseIpBeans(result);
//				
//				
////				ProxyIpBean bean = ipBeans.get(0);
////				// 更改代理
////				NetUtil.changeProxy(bean.ip,bean.port);
//				
////				// 获取ip
////				String proxyResult = NetUtil.getProxyIp(bean.ip,bean.port);
////				System.out.println("proxyResult------->>"+proxyResult);
//				
////				String realResult = NetUtil.getRealIp(bean.ip,bean.port);
////				System.out.println("realResult------->>"+realResult);
//				
//				
//				for (int i = 0; i < ipBeans.size(); i++) {
//					ProxyIpBean bean = ipBeans.get(i);
//					System.out.println("开始设置代理\n设置ip为------->>>"+bean.ip+"\n设置端口为------>>>"+bean.port);
//					String proxyResult = NetUtil.getProxyIp(bean.ip,bean.port);
//					System.out.println("proxyResult------->>"+proxyResult);
//					
////					String realResult = NetUtil.getRealIp(bean.ip,bean.port);
////					System.out.println("realResult------->>"+realResult);
//					
//					System.out.println(" ");
//					try {
//						Thread.sleep(3000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				
//			}
//		}).start();
//		
//		
//		ProxyIpBean ipBean = new ProxyIpBean();
//		ipBean.ip = "sdsdf:1652:4545:255";
//		Constance.newProxyIpBeanList.add(ipBean);
//		ProxyIpBean ipBean2 = Constance.newProxyIpBeanList.getFirst();
//		Constance.oldProxyIpBeanList.add(ipBean2);
//		Constance.newProxyIpBeanList.remove(ipBean2);
//		System.out.println("new----->>>"+Constance.newProxyIpBeanList.isEmpty());
//		System.out.println("old_ip----->>>"+Constance.oldProxyIpBeanList.getFirst().ip);
//		System.out.println("ipBean2----->>>"+ipBean2.ip);
		
		
//		try {
//			NetUtil.downLoadFromUrl("http://pic39.nipic.com/20140312/18085061_092729513131_2.jpg", "测试图片.jpg", "c:/test");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		AdTask adTask1 = new AdTask(Constance.TTGY_AD_UNIT_IDS, Constance.KDXF_APP_ID, Constance.KDXF_APP_NAME, Constance.KDXF_APP_PACKAGE_NAME, 1);
//		AdTask adTask2 = new AdTask(Constance.TTGY_AD_UNIT_IDS, Constance.KDXF_APP_ID, Constance.KDXF_APP_NAME, Constance.KDXF_APP_PACKAGE_NAME, 2);
//		AdTask adTask3 = new AdTask(Constance.TTGY_AD_UNIT_IDS, Constance.KDXF_APP_ID, Constance.KDXF_APP_NAME, Constance.KDXF_APP_PACKAGE_NAME, 3);
//		AdTask adTask4 = new AdTask(Constance.TTGY_AD_UNIT_IDS, Constance.KDXF_APP_ID, Constance.KDXF_APP_NAME, Constance.KDXF_APP_PACKAGE_NAME, 4);
//		AdTask adTask5 = new AdTask(Constance.TTGY_AD_UNIT_IDS, Constance.KDXF_APP_ID, Constance.KDXF_APP_NAME, Constance.KDXF_APP_PACKAGE_NAME, 5);
		
		ThreadPoolManager.getInstance().addTask(adTask1);
//		ThreadPoolManager.getInstance().addTask(adTask2);
//		ThreadPoolManager.getInstance().addTask(adTask3);
//		ThreadPoolManager.getInstance().addTask(adTask4);
//		ThreadPoolManager.getInstance().addTask(adTask5);
	}
}
