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
	
	/** 统计请求讯飞的次数*/
	private int time;

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
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			// 获取ipBean信息
			if(isUseNew) {
				System.out.println("使用的是新的集合中的数据！！！线程编号---->>>"+threadNum);
				// 使用新的代理ip集合中的数据
				if(newProxyIpBeanList.isEmpty()) {
					System.out.println("新集合中的数据是空的！！");
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
					System.out.println("从新集合中的获取到的ipBean数据是空的！！");
					newProxyIpBeanList.removeFirst();
					continue;
				}
			}else {
				System.out.println("使用的是旧的集合中的数据！！！线程编号---->>>"+threadNum);
				// 使用的是旧的有效的ip集合里的数据
				if(oldProxyIpBeanList.isEmpty() || ipDeviceInfoMap.isEmpty()) {
					// 旧的集合中没有数据了,使用完了
					System.out.println("旧的集合中没有数据了,使用完了！！");
					isUseNew = true;
					continue;
				}else {
					this.ipBean = oldProxyIpBeanList.getFirst();
					if(this.ipBean == null) {
						System.out.println("从旧的集合中获取到的ipBean为空！！！");
						oldProxyIpBeanList.removeFirst();
						continue;
					}
				}
			}
			
			
			// ipBean为空  就重新来，进入下一次循环
			if(this.ipBean == null) {
				System.out.println("新集合中的数据是空的！！");
				continue;
			}
			// 检测ipBean是否有效
			if(isUseNew) {
				if(NetUtil.isValidProxyIp(ipBean)) {
					// 有效的ip
					System.out.println("此ip有效---->>"+ipBean.ip);
					
					//TODO 从数据库中获取手机设备信息
					this.deviceInfo = new DeviceInfo();
					deviceInfo.androidId = "114826349dd17ab0";
					deviceInfo.imei = "863288034320306";
					deviceInfo.mac = "00:71:f3:47:3c:ad";
					deviceInfo.userAgent = "Mozilla/5.0%20(Linux;%20U;%20Android%204.2.2;%20zh-CN;%20U59GT-C4B%20Build/JDQ39)%20AppleWebKit/537.36%20(KHTML,%20like%20Gecko)%20Version/4.0%20Chrome/40.0.2214.89%20UCBrowser/11.4.9.941%20Mobile%20Safari/537.36";
					deviceInfo.model = "meetuu%20G7";
					deviceInfo.language = "zh-CN";
					deviceInfo.deviceType = 0;
					deviceInfo.osVersion = "4.1";
					deviceInfo.density = "2.0";
					deviceInfo.operator = "46000";
					deviceInfo.net = 2;
					deviceInfo.deviceScreenWidth = 1080;
					deviceInfo.deviceScreenHeight = 1920;
					deviceInfo.orientation = 0;
					deviceInfo.vendor = "HuaWei";
					
				}else {
					// 无效的ip
					System.out.println("此ip无效---->>"+ipBean.ip);
					newProxyIpBeanList.removeFirst();
					ipBean = null;
					continue;
				}
			}else {
				// 是从旧的ip信息集合中
//				if(oldProxyIpBeanList.isEmpty()) {
//					isUseNew = true;
//					continue;
//				}
				if(ipDeviceInfoMap.isEmpty()) {
					oldProxyIpBeanList.clear();
					isUseNew = true;
					continue;
				}
				// 获取所对应的设备信息
				this.deviceInfo = ipDeviceInfoMap.get(ipBean.ip);
				if(deviceInfo == null) {
					ipDeviceInfoMap.clear();
					oldProxyIpBeanList.clear();
					isUseNew = true;
					continue;
				}
			}
			
			
			
			// deviceInfo为空  就重新来，进入下一次循环
			if(this.deviceInfo == null) {
				System.out.println("得到的设备信息deviceInfo是空的！！！");
				this.ipBean = null;
				continue;
			}
			
			
			for (int i = 0; i < adUnitIds.length; i++) {
				// 请求科大讯飞数据
				time ++;
				System.out.println("当前请求的讯飞的总次数----->>>"+time);
				NetUtil.requestKDXFAdInfos(deviceInfo, ipBean, Constance.TTGY_AD_WIDTHS_HEIGHT[i], adUnitIds[i], i == 0 ? true:false, appId, appName, packageName, new OnLoadAdListener() {
					
					@Override
					public void onNetError(String msg) {
					}
					
					@Override
					public void onLoadSuccess(JSONObject resultObject, int[] showAdWidthAndHeight) {
						System.out.println("有广告数据！！线程编号---->>>"+threadNum);
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
				if(newProxyIpBeanList.isEmpty() && oldProxyIpBeanList.size() >= 5) {
					// 设置从旧的ip信息集合中获取数据
					isUseNew = false;
					System.out.println("准备开始使用旧的集合中的数据信息！！！");
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
		System.out.println("adInfo == null------>>>"+(adInfo == null));
			if (adInfo == null) {
				return;
			}
			System.out.println("开始展示上报");
			// 上报展示数据成功 请求上报链接
			NetUtil.requestUrlsByProxy(ipBean,adInfo.getImprUrls());
			// 设置百分之6的点击率
//			if (Tools.isClick(6)) {
				// 如果随机数的长度是 4 或者 5 则等一段时间进行点击上报
				System.out.println("休眠一段时间 准备上报点击url");
//				// 这里暂时不做等待一段时间的操作
//				 int sleepTimeRandom = Tools.randomMinMax(1000, 6000);
//				 try {
//				 // 休眠随机的时间
//				 Thread.sleep(2000);
//				 } catch (InterruptedException e) {
//				 e.printStackTrace();
//				 }
			     // 设置上报点击
				 Tools.doClick(adInfo,this.ipBean,showAdWidthAndHeight,threadNum+"");
//			}
	}
}
