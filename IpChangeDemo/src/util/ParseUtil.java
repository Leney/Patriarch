package util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import bean.AdInfo;
import bean.ProxyIpBean;

public class ParseUtil {
	
	/**
	 * 解析芝麻代理ip信息
	 * @param result
	 * @return
	 */
	public static List<ProxyIpBean> parseIpBeans(String result){
		List<ProxyIpBean> list = new ArrayList<>();
		try {
			System.out.println("parseIpBeans result---->>>"+result);
			JSONObject jsonObject = new JSONObject(result);
			int code = jsonObject.getInt("code");
			if(code == 0){
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				int length = jsonArray.length();
				for (int i = 0; i < length; i++) {
					JSONObject ipObject = jsonArray.getJSONObject(i);
					ProxyIpBean ipBean = new ProxyIpBean();
					ipBean.ip = ipObject.getString("ip");
					ipBean.port = ipObject.getInt("port");
					ipBean.expire_time_str = ipObject.getString("expire_time");
					ipBean.city = ipObject.getString("city");
					ipBean.isp = ipObject.getString("isp");
					list.add(ipBean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * 解析讯代理ip信息
	 * @param result
	 * @return
	 */
	public static List<ProxyIpBean> parseIpBeansFromXun(String result){
		List<ProxyIpBean> list = new ArrayList<>();
		try {
			System.out.println("parseIpBeans result---->>>"+result);
			JSONObject jsonObject = new JSONObject(result);
			int code = jsonObject.getInt("ERRORCODE");
			if(code == 0){
				JSONArray jsonArray = jsonObject.getJSONArray("RESULT");
				int length = jsonArray.length();
				for (int i = 0; i < length; i++) {
					JSONObject ipObject = jsonArray.getJSONObject(i);
					ProxyIpBean ipBean = new ProxyIpBean();
					ipBean.ip = ipObject.getString("ip");
					ipBean.port = ipObject.getInt("port");
					list.add(ipBean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
     * 获取科大讯飞广告数据
     * @param resultObject
     * @return
     */
    public static AdInfo getAdInfo(JSONObject resultObject) {
        AdInfo adInfo = null;
        try {
            JSONArray batchMaArray = resultObject.getJSONArray("batch_ma");
            adInfo = new AdInfo();
            int length = batchMaArray.length();
            String adType = resultObject.getString("adtype");
            for (int i = 0; i < length; i++) {
                JSONObject batchMaObject = batchMaArray.getJSONObject(i);
                adInfo.setAdType(adType);

                // 默认未点击
                adInfo.setClick(false);
                if(batchMaObject.has("ad_source_mark")){
                    adInfo.setAdSourceMark(batchMaObject.getString("ad_source_mark"));
                }
                if(batchMaObject.has("landing_url")){
                    adInfo.setLandingUrl(batchMaObject.getString("landing_url"));
                }
                if (batchMaObject.has("image")) {
                    adInfo.setImageUrl(batchMaObject.getString("image"));
                }
                if (batchMaObject.has("icon")) {
                    adInfo.setIcon(batchMaObject.getString("icon"));
                }
                if (batchMaObject.has("title")) {
                    adInfo.setTitle(batchMaObject.getString("title"));
                }
                if (batchMaObject.has("sub_title")) {
                    adInfo.setSubTile(batchMaObject.getString("sub_title"));
                }
                if (batchMaObject.has("right_icon_url")) {
                    adInfo.setRightIconUrl(batchMaObject.getString("right_icon_url"));
                }
                if(batchMaObject.has("deep_link")){
                    adInfo.setDeepLink(batchMaObject.getString("deep_link"));
                }

                JSONArray clickUrlArray = batchMaObject.getJSONArray("click_url");
                int clickLength = clickUrlArray.length();
                adInfo.setClickUrls(new String[clickLength]);
                for (int j = 0; j < clickLength; j++) {
                    // 获取click_url数组具体值
                    adInfo.getClickUrls()[j] = clickUrlArray.getString(j);
                }

                JSONArray imprUrlArray = batchMaObject.getJSONArray("impr_url");
                int imprLength = imprUrlArray.length();
                adInfo.setImprUrls(new String[imprLength]);
                for (int j = 0; j < imprLength; j++) {
                    // 获取impr_url数组具体值
                    adInfo.getImprUrls()[j] = imprUrlArray.getString(j);
                }

                if (TextUtils.equals(AdInfo.AD_TYPE_DOWNLOAD, adType)) {
                    // 如果是下载类型的广告
                    adInfo.setPackageName(batchMaObject.getString("package_name"));

                    // 开始安装时上报的url
                    JSONArray installStartArray = batchMaObject.getJSONArray
                            ("inst_installstart_url");
                    int installStartLength = installStartArray.length();
                    adInfo.setInstInstallStartUrls(new String[installStartLength]);
                    for (int j = 0; j < installStartLength; j++) {
                        adInfo.getInstInstallStartUrls()[j] = installStartArray.getString(j);
                    }

                    // 安装成功时上报的url
                    JSONArray installSuccArray = batchMaObject.getJSONArray
                            ("inst_installsucc_url");
                    int installSuccessLength = installSuccArray.length();
                    adInfo.setInstInstallSuccUrls(new String[installSuccessLength]);
                    for (int j = 0; j < installSuccessLength; j++) {
                        adInfo.getInstInstallSuccUrls()[j] = installSuccArray.getString(j);
                    }

                    // 开始下载时上报的url
                    JSONArray downloadStartArray = batchMaObject.getJSONArray
                            ("inst_downstart_url");
                    int downloadStartLength = downloadStartArray.length();
                    adInfo.setInstDownloadStartUrls(new String[downloadStartLength]);
                    for (int j = 0; j < downloadStartLength; j++) {
                        adInfo.getInstDownloadStartUrls()[j] = downloadStartArray.getString(j);
                    }

                    // 下载成功时上报的url
                    JSONArray downloadSuccArray = batchMaObject.getJSONArray
                            ("inst_downsucc_url");
                    int downloadSuccLength = downloadSuccArray.length();
                    adInfo.setInstDownloadSuccUrls(new String[downloadSuccLength]);
                    for (int j = 0; j < downloadSuccLength; j++) {
                        adInfo.getInstDownloadSuccUrls()[j] = downloadSuccArray.getString(j);
                    }
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
            adInfo = null;
        }
        return adInfo;
    }
}
