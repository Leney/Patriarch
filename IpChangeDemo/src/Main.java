import manager.Constance;
import manager.db.DeviceDBManager;
import manager.thread.AdTask;
import manager.thread.ThreadPoolManager;

public class Main {

	public static void main(String[] args) {
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// String result = NetUtil.sendPost("http://http.dztwo.com/getip?",
		// "num=10&type=2&pro=&city=0&yys=0&port=1&time=1");
		// System.out.println("result------>>>"+result);
		// List<ProxyIpBean> ipBeans = ParseUtil.parseIpBeans(result);
		//
		//
		//// ProxyIpBean bean = ipBeans.get(0);
		//// // 更改代理
		//// NetUtil.changeProxy(bean.ip,bean.port);
		//
		//// // 获取ip
		//// String proxyResult = NetUtil.getProxyIp(bean.ip,bean.port);
		//// System.out.println("proxyResult------->>"+proxyResult);
		//
		//// String realResult = NetUtil.getRealIp(bean.ip,bean.port);
		//// System.out.println("realResult------->>"+realResult);
		//
		//
		// for (int i = 0; i < ipBeans.size(); i++) {
		// ProxyIpBean bean = ipBeans.get(i);
		// System.out.println("开始设置代理\n设置ip为------->>>"+bean.ip+"\n设置端口为------>>>"+bean.port);
		// String proxyResult = NetUtil.getProxyIp(bean.ip,bean.port);
		// System.out.println("proxyResult------->>"+proxyResult);
		//
		//// String realResult = NetUtil.getRealIp(bean.ip,bean.port);
		//// System.out.println("realResult------->>"+realResult);
		//
		// System.out.println(" ");
		// try {
		// Thread.sleep(3000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		//
		// }
		// }).start();
		//
		//
		// ProxyIpBean ipBean = new ProxyIpBean();
		// ipBean.ip = "sdsdf:1652:4545:255";
		// Constance.newProxyIpBeanList.add(ipBean);
		// ProxyIpBean ipBean2 = Constance.newProxyIpBeanList.getFirst();
		// Constance.oldProxyIpBeanList.add(ipBean2);
		// Constance.newProxyIpBeanList.remove(ipBean2);
		// System.out.println("new----->>>"+Constance.newProxyIpBeanList.isEmpty());
		// System.out.println("old_ip----->>>"+Constance.oldProxyIpBeanList.getFirst().ip);
		// System.out.println("ipBean2----->>>"+ipBean2.ip);

		// try {
		// NetUtil.downLoadFromUrl("http://pic39.nipic.com/20140312/18085061_092729513131_2.jpg",
		// "测试图片.jpg", "c:/test");
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		Constance.DB_TOTAL_DATA_COUNT = DeviceDBManager.getInstance().getTotalCount();
		System.out.println("数据库数据条数------》》"+Constance.DB_TOTAL_DATA_COUNT);

		AdTask adTask1 = new AdTask(Constance.TTGY_AD_UNIT_IDS, Constance.KDXF_APP_ID, Constance.KDXF_APP_NAME,
				Constance.KDXF_APP_PACKAGE_NAME, 1);
		AdTask adTask2 = new AdTask(Constance.TTGY_AD_UNIT_IDS, Constance.KDXF_APP_ID, Constance.KDXF_APP_NAME,
				Constance.KDXF_APP_PACKAGE_NAME, 2);
		// AdTask adTask3 = new AdTask(Constance.TTGY_AD_UNIT_IDS,
		// Constance.KDXF_APP_ID, Constance.KDXF_APP_NAME,
		// Constance.KDXF_APP_PACKAGE_NAME, 3);
		// AdTask adTask4 = new AdTask(Constance.TTGY_AD_UNIT_IDS,
		// Constance.KDXF_APP_ID, Constance.KDXF_APP_NAME,
		// Constance.KDXF_APP_PACKAGE_NAME, 4);
		// AdTask adTask5 = new AdTask(Constance.TTGY_AD_UNIT_IDS,
		// Constance.KDXF_APP_ID, Constance.KDXF_APP_NAME,
		// Constance.KDXF_APP_PACKAGE_NAME, 5);

		ThreadPoolManager.getInstance().addTask(adTask1);
//		ThreadPoolManager.getInstance().addTask(adTask2);
		// ThreadPoolManager.getInstance().addTask(adTask3);
		// ThreadPoolManager.getInstance().addTask(adTask4);
		// ThreadPoolManager.getInstance().addTask(adTask5);

		// System.out.println("数据库总条数---->>>"+DeviceDBManager.getInstance().getTotalCount());
		// DeviceInfo deviceInfo =
		// DeviceDBManager.getInstance().queryDeviceInfo(5648);
		// System.out.println("imei---->>>"+deviceInfo.imei);
		// System.out.println("androidId---->>>"+deviceInfo.androidId);

		// Constance.DB_TOTAL_DATA_COUNT =
		// DeviceDBManager.getInstance().getTotalCount();
		//
		// System.out.println("mac----->>>"+Tools.simulationMac());
		
		
//		Main muDemo = new Main();
//        try {
//            muDemo.showURL();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
	}
	
	
//	public void showURL() throws IOException {
//
//        // 第一种：获取类加载的根路径   D:\git\daotie\daotie\target\classes
//        File f = new File(this.getClass().getResource("/").getPath());
//        System.out.println(f);
//
//        // 获取当前类的所在工程路径; 如果不加“/”  获取当前类的加载目录  D:\git\daotie\daotie\target\classes\my
//        File f2 = new File(this.getClass().getResource("").getPath());
//        System.out.println(f2);
//
//        // 第二种：获取项目路径    D:\git\daotie\daotie
//        File directory = new File("");// 参数为空
//        String courseFile = directory.getCanonicalPath();
//        System.out.println(courseFile);
//
//
//        // 第三种：  file:/D:/git/daotie/daotie/target/classes/
//        URL xmlpath = this.getClass().getClassLoader().getResource("");
//        System.out.println(xmlpath);
//
//
//        // 第四种： D:\git\daotie\daotie
//        System.out.println(System.getProperty("user.dir"));
//        /*
//         * 结果： C:\Documents and Settings\Administrator\workspace\projectName
//         * 获取当前工程路径
//         */
//
//        // 第五种：  获取所有的类路径 包括jar包的路径
//        System.out.println(System.getProperty("java.class.path"));
//
//    }
}
