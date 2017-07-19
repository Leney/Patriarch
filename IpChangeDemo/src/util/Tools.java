package util;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import bean.AdInfo;
import bean.DeviceInfo;
import bean.ProxyIpBean;
import manager.Constance;
import manager.db.DeviceDBManager;

public class Tools {

	/**
	 * ��ȡ����֥�����ip��Ϣ
	 */
	public static List<ProxyIpBean> getProxyIpList() {
		// ֥�����
//		String result = NetUtil.sendPost("http://http.zhimadaili.com/getip?",
//				"num=5&type=2&pro=&yys=0&port=1&time=1");
//		List<ProxyIpBean> ipBeans = ParseUtil.parseIpBeans(result);
		
//		// Ѷ����
//		String result = NetUtil.sendGet("http://www.xdaili.cn/ipagent/privateProxy/applyStaticProxy",
//				"count=1&spiderId=4fb3cc493e0340e98804629db8f4f0bd&returnType=2");
//		List<ProxyIpBean> ipBeans = ParseUtil.parseIpBeansFromXun(result);
		
		// ip����
		String result = NetUtil.sendGet("http://open.ipjldl.com/index.php/api/entry",
		"method=proxyServer.ipinfolist&quantity=10&province=&city=&anonymous=1&ms=1&service=0&protocol=1&wdsy=on&format=json&separator=1&separator_txt=");
		List<ProxyIpBean> ipBeans = ParseUtil.parseIpBeansFromIpJL(result);
		System.out.println("��ȡ������ip������---->>"+ipBeans.size());
		return ipBeans;
		
	}
	
	
	/**
	 * ���Ƹ���
	 * @param proValue
	 * @return
	 */
	public static boolean isClick(int proValue) {
		int common = new Random().nextInt(100);
		if(common<=proValue) {
			return true;
		}
		return false;
	}
	
	/**
     * ��ͨ������¼�����
     *
     * @param adInfo
     * @param ipBean
     * @param downX
     * @param downY
     * @param upX
     * @param upY
     */
    public static void doClick(final AdInfo adInfo, ProxyIpBean ipBean,int[] showAdWidthAndHeight,String threadName) {
        int length = adInfo.getClickUrls().length;
        for (int i = 0; i < length; i++) {
            String url = Tools.replaceAdClickUrl(adInfo.getClickUrls()[i], showAdWidthAndHeight);
            NetUtil.requestUrlByProxy(ipBean, url);
        }
        System.out.println("�������-->>"+adInfo.getAdType() + "--�̱߳��----->>"+threadName);
        // ����
        adInfo.setAdType(AdInfo.AD_TYPE_REDIRECT);
        if(TextUtils.equals(adInfo.getAdType(), AdInfo.AD_TYPE_REDIRECT)) {
        	// TODO ��ת���͵Ĺ��(Ϊ�����Ӷ����ʣ����ݹ��url ���������ģ������������Ҫ��Ҫ����һ�£�)
        	System.out.println("����ת���͵Ĺ�棡����");
        }else if (TextUtils.equals(adInfo.getAdType(), AdInfo.AD_TYPE_DOWNLOAD)) {
			// TODO �������͵Ĺ��
        	System.out.println("���������͵Ĺ�棡����");
        	// �ϱ�Ѷ�ɿ�ʼ���ص�����
        	int length2 = adInfo.getInstDownloadStartUrls().length;
        	System.out.println("�ϱ���ʼ���أ���");
            for (int i = 0; i < length2; i++) {
                NetUtil.requestUrlByProxy(ipBean, adInfo.getInstDownloadStartUrls()[i]);
            }
            
            // ��ʼ����
        	try {
				NetUtil.downLoadFromUrl(adInfo.getLandingUrl(), threadName + ".apk", "c:/download");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	System.out.println("�ϱ�������ɣ���");
        	// �ϱ�Ѷ�ɿ�ʼ������ɵ�����
        	int length3 = adInfo.getInstDownloadSuccUrls().length;
            for (int i = 0; i < length3; i++) {
                NetUtil.requestUrlByProxy(ipBean, adInfo.getInstDownloadSuccUrls()[i]);
            }
            
            System.out.println("�ϱ���ʼ��װ����");
         // �ϱ�Ѷ�ɿ�ʼ��װ������
        	int length4 = adInfo.getInstInstallStartUrls().length;
            for (int i = 0; i < length4; i++) {
                NetUtil.requestUrlByProxy(ipBean, adInfo.getInstInstallStartUrls()[i]);
            }
            
            // ����2��
            try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            
            System.out.println("�ϱ���װ��ɣ���");
         // �ϱ�Ѷ�ɰ�װ�ɹ�������
        	int length5 = adInfo.getInstInstallSuccUrls().length;
            for (int i = 0; i < length5; i++) {
                NetUtil.requestUrlByProxy(ipBean, adInfo.getInstInstallSuccUrls()[i]);
            }
		}else if (TextUtils.equals(adInfo.getAdType(), AdInfo.AD_TYPE_BRAND)) {
			// TODO Ʒ�����͵Ĺ��
			System.out.println("��Ʒ�����͵Ĺ�棡����");
		}
    }
	
	
	/**
     * �滻�ƴ�Ѷ���е�click_url
     *
     * @param url
     * @param downX
     * @param downY
     * @param upX
     * @param upY
     * @return
     */
    public static String replaceAdClickUrl(String url,int[] showAdWidthAndHeight) {
    	int width = showAdWidthAndHeight[0];
    	int height = showAdWidthAndHeight[1];
    	float downX = width * 0.456f;
    	float downY = height * 0.525f;
    	float upX = downX + 0.643f;
    	float upY = downY - 0.473f;
        url = url.replace("IT_CLK_PNT_DOWN_X", String.valueOf(downX));
        url = url.replace("IT_CLK_PNT_DOWN_Y", String.valueOf(downY));
        url = url.replace("IT_CLK_PNT_UP_X", String.valueOf(upX));
        url = url.replace("IT_CLK_PNT_UP_Y", String.valueOf(upY));
        return url;
    }
    
    /**
     * ��ȡ��ֵ֮��������
     *
     * @param min
     * @param max
     * @return
     */
    public static int randomMinMax(int min, int max) {
        if (min >= max) {
            return min;
        }
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }
    
    
    /**
     * ģ��Mac��ַ
     *
     * @return
     */
    public static String simulationMac() {
        char[] char1 = "abcdef".toCharArray();
        char[] char2 = "0123456789".toCharArray();
        StringBuffer mBuffer = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int t = new java.util.Random().nextInt(char1.length);
            int y = new java.util.Random().nextInt(char2.length);
            int key = new java.util.Random().nextInt(2);
            if (key == 0) {
                mBuffer.append(char2[y]).append(char1[t]);
            } else {
                mBuffer.append(char1[t]).append(char2[y]);
            }

            if (i != 5) {
                mBuffer.append(":");
            }
        }
        return mBuffer.toString();
    }
    
    /**
     * ��������ݿ��л�ȡ����
     * @return
     */
    public static DeviceInfo getDeviceInfoByRamdon(){
    	int id = randomMinMax(1, Constance.DB_TOTAL_DATA_COUNT);
    	DeviceInfo deviceInfo = DeviceDBManager.getInstance().queryDeviceInfo(id);
    	if(deviceInfo != null){
    		// ��ѯ��������
    		deviceInfo.mac = simulationMac();
    		deviceInfo.osVersion = Constance.OS_VERSIONS[Tools.randomMinMax(0, Constance.OS_VERSIONS.length - 1)];
    		deviceInfo.userAgent = Constance.USER_AGENTS[Tools.randomMinMax(0, Constance.USER_AGENTS.length - 1)].replace("OS_VERSION", deviceInfo.osVersion);
    		
    		deviceInfo.language = "zh-CN";
    		deviceInfo.deviceType = 0;
    		deviceInfo.density = Constance.DENSITY[Tools.randomMinMax(0, Constance.DENSITY.length - 1)];
    		// �����ȡ ��Ӫ�̱��
            deviceInfo.operator = Constance.OPERATORS[Tools.randomMinMax(0, Constance.OPERATORS.length - 1)];
            deviceInfo.net = 2;
            int[] screen_ressolution = Constance.SCREEN_RESSOLUTION[Tools.randomMinMax(0, Constance.SCREEN_RESSOLUTION.length - 1)];
            deviceInfo.deviceScreenWidth = screen_ressolution[0];
            deviceInfo.deviceScreenHeight = screen_ressolution[1];
            deviceInfo.orientation = 0;
            
            // TODO model �� vendor��ֵ��Ҫ����
    		deviceInfo.model = "meetuu%20G7";
            deviceInfo.vendor = "HuaWei";
    	}
    	return deviceInfo;
    }

}
