package util;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import bean.AdInfo;
import bean.ProxyIpBean;

public class Tools {

	/**
	 * 获取更多芝麻代理ip信息
	 */
	public static List<ProxyIpBean> getProxyIpList() {
		String result = NetUtil.sendPost("http://http.zhimadaili.com/getip?",
				"num=10&type=2&pro=&yys=0&port=1&time=1");
		List<ProxyIpBean> ipBeans = ParseUtil.parseIpBeans(result);
		System.out.println("获取到代理ip的条数---->>"+ipBeans.size());
//		Constance.newProxyIpBeanList.clear();
		
		return ipBeans;
		
	}
	
	
	/**
	 * 控制概率
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
     * 普通广告点击事件处理
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
        	// TODO 跳转类型的广告(为了增加二跳率，根据广告url 打开浏览器，模拟点击？二跳率要不要控制一下？)
        }else if (adInfo.getAdType() == AdInfo.AD_TYPE_DOWNLOAD) {
			// TODO 下载类型的广告
        	// 上报讯飞开始下载的链接
        	int length2 = adInfo.getInstDownloadStartUrls().length;
            for (int i = 0; i < length2; i++) {
                NetUtil.requestUrlByProxy(ipBean, adInfo.getInstDownloadStartUrls()[i]);
            }
            
            // 开始下载
        	try {
				NetUtil.downLoadFromUrl(adInfo.getLandingUrl(), threadName + ".apk", "c:/download");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	// 上报讯飞开始下载完成的链接
        	int length3 = adInfo.getInstDownloadSuccUrls().length;
            for (int i = 0; i < length3; i++) {
                NetUtil.requestUrlByProxy(ipBean, adInfo.getInstDownloadSuccUrls()[i]);
            }
            
         // 上报讯飞开始安装的链接
        	int length4 = adInfo.getInstInstallStartUrls().length;
            for (int i = 0; i < length4; i++) {
                NetUtil.requestUrlByProxy(ipBean, adInfo.getInstInstallStartUrls()[i]);
            }
            
            // 休眠2秒
            try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            
         // 上报讯飞安装成功的链接
        	int length5 = adInfo.getInstInstallSuccUrls().length;
            for (int i = 0; i < length5; i++) {
                NetUtil.requestUrlByProxy(ipBean, adInfo.getInstInstallSuccUrls()[i]);
            }
		}else if (adInfo.getAdType() == AdInfo.AD_TYPE_BRAND) {
			// TODO 品牌类型的广告
		}
    }
	
	
	/**
     * 替换科大讯飞中的click_url
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
