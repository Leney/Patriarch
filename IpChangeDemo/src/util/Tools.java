package util;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import bean.AdInfo;
import bean.ProxyIpBean;

public class Tools {

	/**
	 * ��ȡ����֥�����ip��Ϣ
	 */
	public static List<ProxyIpBean> getProxyIpList() {
		String result = NetUtil.sendPost("http://http.zhimadaili.com/getip?",
				"num=10&type=2&pro=&yys=0&port=1&time=1");
		List<ProxyIpBean> ipBeans = ParseUtil.parseIpBeans(result);
		System.out.println("��ȡ������ip������---->>"+ipBeans.size());
//		Constance.newProxyIpBeanList.clear();
		
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
        
        if(adInfo.getAdType() == AdInfo.AD_TYPE_REDIRECT) {
        	// TODO ��ת���͵Ĺ��(Ϊ�����Ӷ����ʣ����ݹ��url ���������ģ������������Ҫ��Ҫ����һ�£�)
        }else if (adInfo.getAdType() == AdInfo.AD_TYPE_DOWNLOAD) {
			// TODO �������͵Ĺ��
        	// �ϱ�Ѷ�ɿ�ʼ���ص�����
        	int length2 = adInfo.getInstDownloadStartUrls().length;
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
        	
        	// �ϱ�Ѷ�ɿ�ʼ������ɵ�����
        	int length3 = adInfo.getInstDownloadSuccUrls().length;
            for (int i = 0; i < length3; i++) {
                NetUtil.requestUrlByProxy(ipBean, adInfo.getInstDownloadSuccUrls()[i]);
            }
            
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
            
         // �ϱ�Ѷ�ɰ�װ�ɹ�������
        	int length5 = adInfo.getInstInstallSuccUrls().length;
            for (int i = 0; i < length5; i++) {
                NetUtil.requestUrlByProxy(ipBean, adInfo.getInstInstallSuccUrls()[i]);
            }
		}else if (adInfo.getAdType() == AdInfo.AD_TYPE_BRAND) {
			// TODO Ʒ�����͵Ĺ��
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

}
