package manager.thread;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import bean.AdInfo;
import bean.DeviceInfo;
import bean.ProxyIpBean;
import manager.Constance;
import util.NetUtil;
import util.ParseUtil;
import util.Tools;
import util.listener.OnLoadAdListener;

/**
 * 刷广告任务线程类
 * 
 * @author dell
 *
 */
public class AdTask implements Runnable {

	/**
	 * 设备信息
	 */
	private DeviceInfo deviceInfo;

	/**
	 * 代理ip信息
	 */
	private ProxyIpBean ipBean;

	 /**
	 * 当前线程的标识
	 * 数字类型 如：1
	 */
	 private int threadNum;

	/**
	 * 此线程需要刷的广告位集合
	 */
	private String[] adUnitIds;

	/**
	 * 讯飞后台应用对应的appId
	 */
	private String appId;

	/**
	 * 讯飞后台应用对应的应用名称
	 */
	private String appName;

	/**
	 * 讯飞后台应用对应的包名
	 */
	private String packageName;
	
	/**
	 * 标识当前是否是使用的新的集合中的ip信息
	 */
	private boolean isUseNew = true;
	
	
	/**
	 * 新获取的代理ip集合
	 */
	private LinkedList<ProxyIpBean> newProxyIpBeanList = new LinkedList<>();
	
	/**
	 * 已使用过一次的代理ip集合
	 */
	private LinkedList<ProxyIpBean> oldProxyIpBeanList = new LinkedList<>();
	
	/** ip使用过一次之后，上一次请求讯飞时所对应的设备信息集合(主要是为了增加pv/uv值,当再次使用某个ip请求讯飞时,保证和上次请求讯飞的设备信息保持一致)*/
	public static Map<String, DeviceInfo> ipDeviceInfoMap = new Hashtable<>();

	public AdTask(String[] adUnitIds, String appId, String appName, String packageName,int threadNum) {
		// this.deviceInfo = deviceInfo;
		this.adUnitIds = adUnitIds;
//		this.adWidthAndHeight = adWidthAndHeight;
		this.appId = appId;
		this.appName = appName;
		this.packageName = packageName;
		this.threadNum = threadNum;
	}
	
	@Override
	public void run() {
		while(true) {
			
			// 获取ipBean信息
			if(isUseNew) {
				// 使用新的代理ip集合中的数据
				if(newProxyIpBeanList.isEmpty()) {
					// 如果集合中为空，则表明新的ip已经用完
					// 则从芝麻获取一批新的代理ip数据
					List<ProxyIpBean> ipBeans = Tools.getProxyIpList();
					newProxyIpBeanList.addAll(ipBeans);
					if(newProxyIpBeanList.isEmpty()) {
						// 从芝麻后台获取不到ip信息了
						// 重新再走一遍流程
						continue;
					}
				}
				this.ipBean = newProxyIpBeanList.getFirst();
				if(this.ipBean == null) {
					newProxyIpBeanList.removeFirst();
					continue;
				}
			}else {
				// 使用的是旧的有效的ip集合里的数据
				if(oldProxyIpBeanList.isEmpty()) {
					// 旧的集合中没有数据了,使用完了
					isUseNew = true;
					continue;
				}else {
					this.ipBean = oldProxyIpBeanList.getFirst();
					if(this.ipBean == null) {
						oldProxyIpBeanList.removeFirst();
						continue;
					}
				}
			}
			
			
			// ipBean为空  就重新来，进入下一次循环
			if(this.ipBean == null) {
				continue;
			}
			
			
			// 检测ipBean是否有效
			if(isUseNew) {
				if(NetUtil.isValidProxyIp(ipBean)) {
					// 有效的ip
					System.out.println("有效ip---->>"+ipBean.ip);
					
					//TODO 从数据库中获取手机设备信息
//					this.deviceInfo = 
					
					
				}else {
					// 无效的ip
					ipBean = null;
					continue;
				}
			}else {
				// 是从旧的ip信息集合中
				if(oldProxyIpBeanList.isEmpty()) {
					isUseNew = true;
					continue;
				}
				// 获取所对应的设备信息
				this.deviceInfo = ipDeviceInfoMap.get(ipBean.ip);
			}
			
			
			
			// deviceInfo为空  就重新来，进入下一次循环
			if(deviceInfo == null) {
				continue;
			}
			
			
			for (int i = 0; i < adUnitIds.length; i++) {
				NetUtil.requestKDXFAdInfos(deviceInfo, ipBean, Constance.TTGY_AD_WIDTHS_HEIGHT[i], adUnitIds[i], i == 0 ? true:false, appId, appName, packageName, new OnLoadAdListener() {
					
					@Override
					public void onNetError(String msg) {
					}
					
					@Override
					public void onLoadSuccess(JSONObject resultObject, int[] showAdWidthAndHeight) {
						disposeGetAdSuccess(resultObject, showAdWidthAndHeight);
					}
					
					@Override
					public void onLoadFailed(String msg) {
					}
				});
			}
			
			
			// 所有的逻辑都处理完成之后
			if(isUseNew) {
				// 添加到有效的ip集合中去
				oldProxyIpBeanList.add(ipBean);
				ipDeviceInfoMap.put(ipBean.ip, deviceInfo);
				// 移除新的ip集合中的数据
				newProxyIpBeanList.remove(ipBean);
			}else {
				// 移除旧的
				oldProxyIpBeanList.removeFirst();
				ipDeviceInfoMap.remove(ipBean.ip);
			}
			
			// 判断下一次循环需使用哪个集合中的数据(新的还是旧的)
			if(isUseNew) {
				if(newProxyIpBeanList.isEmpty() && oldProxyIpBeanList.size() > 1000) {
					// 设置从旧的ip信息集合中获取数据
					isUseNew = false;
				}
			}else {
				if(oldProxyIpBeanList.isEmpty()) {
					ipDeviceInfoMap.clear();
					isUseNew = true;
				}
			}
			
			// 将数据置空
			this.ipBean = null;
			this.deviceInfo = null;
		}
	}
	
	
	
	/**
	 * 处理请求讯飞广告成功之后的操作(有广告数据)
	 * @param resultObject
	 * @param showAdWidthAndHeight
	 */
	private void disposeGetAdSuccess(JSONObject resultObject,int[] showAdWidthAndHeight) {
		// 广告请求成功
		AdInfo adInfo = ParseUtil.getAdInfo(resultObject);
			if (adInfo == null) {
				return;
			}
			// 上报展示数据成功 请求上报链接
			NetUtil.requestUrlsByProxy(ipBean,adInfo.getImprUrls());
			// 设置百分之6的点击率
			if (Tools.isClick(6)) {
				// 如果随机数的长度是 4 或者 5 则等一段时间进行点击上报
				System.out.println("休眠一段时间 准备上报点击url");
				// 这里暂时不做等待一段时间的操作
//				 int sleepTimeRandom = Tools.randomMinMax(1000, 6000);
//				 try {
//				 // 休眠随机的时间
//				 Thread.sleep(sleepTimeRandom);
//				 } catch (InterruptedException e) {
//				 e.printStackTrace();
//				 }
			     // 设置上报点击
				 Tools.doClick(adInfo,this.ipBean,showAdWidthAndHeight,threadNum+"");
			}
	}
}
