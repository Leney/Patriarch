package manager.thread;

import bean.DeviceInfo;
import bean.ProxyIpBean;
import manager.Constance;
import util.NetUtil;
import util.Tools;

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

	// /**
	// * 当前线程的标识
	// * 数字类型 如：1
	// */
	// private int threadNum;

	/**
	 * 此线程需要刷的广告位集合
	 */
	private String[] adUnitIds;

	/**
	 * 此线程需要数的广告展示的宽和高
	 */
	private int[][] adWidthAndHeight;

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

	public AdTask(String[] adUnitIds, int[][] adWidthAndHeight, String appId, String appName, String packageName) {
		// this.deviceInfo = deviceInfo;
		this.adUnitIds = adUnitIds;
		this.adWidthAndHeight = adWidthAndHeight;
		this.appId = appId;
		this.appName = appName;
		this.packageName = packageName;
		// this.threadNum = threadNum;
	}
	
	private void init() {
		// 标识是否是使用新的集合中的ip信息
		if(Constance.newProxyIpBeanList.isEmpty()) {
			// 如果集合中为空，则表明新的ip已经用完
			if(Constance.oldProxyIpBeanList.size()>1000) {
				// 如果有效的集合中，已经满了1000条了，则再次使用之前保存的有效的ip信息
				this.ipBean = Constance.oldProxyIpBeanList.getFirst();
				isUseNew = true;
			}else {
				// 如果有效的集合中，还未装满1000条数据，则到芝麻代理中获取新的一波ip信息
				Tools.getProxyIpList();
				if(Constance.newProxyIpBeanList.isEmpty()) {
					// 从芝麻后台获取不到ip信息了
					// 重新再走一遍流程
					init();
					return;
				}
				this.ipBean = Constance.newProxyIpBeanList.getFirst();
				isUseNew = false;
			}
		}else {
			// 新的ip集合中还有数据
			this.ipBean = Constance.newProxyIpBeanList.getFirst();
			isUseNew = true;
		}
		
		if(this.ipBean == null) {
			init();
			return;
		}
		
		
		if(isUseNew) {
			// 只有在使用新的集合中的ip信息时才去检测ip是否是有效的
			if(NetUtil.isValidProxyIp(ipBean)) {
				// 将有效的ipBean加入到有效集合中去
				Constance.oldProxyIpBeanList.add(ipBean);
				// 再移除掉新的ip集合中的数据
				Constance.newProxyIpBeanList.remove(ipBean);
				// TODO 从数据库中获取手机设备信息
//				this.deviceInfo = 
			}else {
				// 如果是无效的  则再初始化一次，获取ipBean和DeviceInfo
				init();
			}
		}else {
			// 使用的是有效集合中的数据
			
		}
		
	}

	@Override
	public void run() {
		while(true) {
			
			// 获取ipBean信息
			if(isUseNew) {
				// 使用新的代理ip集合中的数据
				if(Constance.newProxyIpBeanList.isEmpty()) {
					// 如果集合中为空，则表明新的ip已经用完
					// 则从芝麻获取一批新的代理ip数据
					Tools.getProxyIpList();
					if(Constance.newProxyIpBeanList.isEmpty()) {
						// 从芝麻后台获取不到ip信息了
						// 重新再走一遍流程
						continue;
					}
				}
				this.ipBean = Constance.newProxyIpBeanList.getFirst();
				if(this.ipBean == null) {
					continue;
				}
			}else {
				// 使用的是旧的有效的ip集合里的数据
				if(Constance.oldProxyIpBeanList.isEmpty()) {
					// 旧的集合中没有数据了,使用完了
					isUseNew = true;
					continue;
				}else {
					this.ipBean = Constance.oldProxyIpBeanList.getFirst();
					if(this.ipBean == null) {
						continue;
					}
				}
			}
			
			// 检测ipBean是否有效
			if(isUseNew) {
				if(NetUtil.isValidProxyIp(ipBean)) {
					// 有效的ip
					// 添加到有效的ip集合中去
					Constance.oldProxyIpBeanList.add(ipBean);
					Constance.ipDeviceInfoMap.put(ipBean.ip, deviceInfo);
					// 移除新的ip集合中的数据
					Constance.newProxyIpBeanList.remove(ipBean);
				}else {
					// 无效的ip
					ipBean = null;
					continue;
				}
			}else {
				// 是从旧的ip信息集合中
				if(Constance.oldProxyIpBeanList.isEmpty()) {
					isUseNew = true;
					continue;
				}
			}
			
			
			
			// 获取deviceInfo
			if(isUseNew) {
				// 从新的ip集合中获取数据
				// TODO 在这里从数据库中获取设备信息
//				this.deviceInfo = 
				if(this.deviceInfo == null) {
					ipBean = null;
					continue;
				}
			}else {
				// 从旧的ip集合中获取数据
				this.deviceInfo = Constance.ipDeviceInfoMap.get(ipBean.ip);
				if(deviceInfo == null) {
					ipBean = null;
					continue;
				}
			}
			
			
			
			
			
			// 所有的逻辑都处理完成之后
			if(isUseNew) {
				if(Constance.newProxyIpBeanList.isEmpty() && Constance.oldProxyIpBeanList.size() > 1000) {
					// 设置从旧的ip信息集合中获取数据
					isUseNew = false;
				}
			}else {
				Constance.oldProxyIpBeanList.remove(ipBean);
				Constance.ipDeviceInfoMap.remove(ipBean.ip);
			}
			
			// 将数据置空
			this.ipBean = null;
			this.deviceInfo = null;
		}
		
		

		if (Constance.isUseNewIp.get()) {
			// 是用从芝麻代理获取的新的代理ip信息
			if (!Constance.newProxyIpBeanList.isEmpty()) {
				// 新的代理ip集合中没有了代理ip信息了
				if (Constance.oldProxyIpBeanList.size() > 1000) {
					// 保存的有效的代理ip已经大于了1000条 则去使用之前保存的有效的代理ip信息，有效利用代理ip信息
					Constance.isUseNewIp.set(false);
				} else {
					// 重新去获取更多的代理ip信息
					Tools.getProxyIpList();
				}
			} else {
				// 新的代理ip集合中还有代理ip信息数据
				// 获取需要使用的代理ip信息对象
				ProxyIpBean tempIpBean = Constance.newProxyIpBeanList.getFirst();
				// 检测代理ip是否有效
				if (NetUtil.isValidProxyIp(tempIpBean)) {
					// 是有效的代理ip
					this.ipBean = tempIpBean;

					// TODO 在这里从数据库中获取设备信息
					// TODO
					DeviceInfo tempDeviceInfo = new DeviceInfo();

					if (tempDeviceInfo == null) {
						// 直接返回还是再次去获取手机设备信息数据？？？
						return;
					}
					// 将有效的代理ip信息添加到集合中去
					Constance.validProxyIpBeanList.add(tempIpBean);
					// 将代理ip信息和手机设备信息添加到对应的Map集合中去，以备之后再次使用
					Constance.ipDeviceInfoMap.put(tempIpBean.ip, tempDeviceInfo);
					this.deviceInfo = tempDeviceInfo;
				} else {
					// 不是有效的代理ip,则移除当前的代理ip信息
					Constance.newProxyIpBeanList.remove(tempIpBean);
				}
			}
		} else {
			// 是使用之前保存的有效的代理ip信息
			if (!Constance.validProxyIpBeanList.isEmpty()) {
				this.ipBean = Constance.validProxyIpBeanList.getFirst();
				this.deviceInfo = Constance.ipDeviceInfoMap.get(ipBean.ip);
			} else {
				// 保存的有效代理ip信息已经使用完
				// 重新去获取更多的代理ip信息
				Tools.getProxyIpList();
				Constance.isUseNewIp.set(true);
			}
		}

		// final int length = adUnitIds.length;
		// for (int i = 0; i < length; i++) {
		// int[] adWidthHeight = adWidthAndHeight[i];
		// if (adWidthHeight == null) {
		// return;
		// }
		//
		// final int curIndex = i;
		// NetUtil.requestKDXFAdInfos(deviceInfo, ipBean, adUnitIds[i],
		// adWidthHeight[0], adWidthHeight[1],
		// i == 0 ? true : false, appId, appName, packageName, new
		// OnLoadAdListener() {
		//
		// @Override
		// public void onLoadSuccess(JSONObject resultObject, int adWidth, int
		// adHeight) {
		// // 广告请求成功
		// AdInfo adInfo = ParseUtil.getAdInfo(resultObject);
		// if (adInfo == null) {
		// return;
		// }
		// // 上报展示数据成功 请求上报链接
		// NetUtil.requestUrlsByProxy(ipBean,adInfo.getImprUrls());
		//
		// // 设置百分之6的点击率
		// if (Tools.isClick(6)) {
		// // 如果随机数的长度是 4 或者 5 则等一段时间进行点击上报
		//
		// Log.i("llj", "休眠一段时间 准备上报点击url");
		// int sleepTimeRandom = Tools.randomMinMax(1000, 6000);
		// try {
		// // 休眠随机的时间
		// Thread.sleep(sleepTimeRandom);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// // 设置上报点击
		// Tools.doClick(adInfo, Constance.curDeviceInfo, adWidth, adHeight);
		// }
		//
		// if (curIndex == length - 1) {
		// // 最后一次循环请求完毕 此次设备的所有广告请求完毕
		// switch (threadNum) {
		// case 1:
		// Constance.IS_FIRST_THREAD_DONE = true;
		// break;
		// case 2:
		// Constance.IS_SECOND_THREAD_DONE = true;
		// break;
		// case 3:
		// Constance.IS_THIRED_THREAD_DONE = true;
		// break;
		// }
		// }
		//
		// if (Constance.IS_FIRST_THREAD_DONE && Constance.IS_SECOND_THREAD_DONE
		// && Constance.IS_THIRED_THREAD_DONE) {
		// // 3个线程都跑完了
		// // TODO 通知切换ip
		// }
		// }
		//
		// @Override
		// public void onNetError(String msg) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void onLoadFailed(String msg) {
		// if (curIndex == length - 1) {
		// // 最后一次循环请求完毕 此次设备的所有广告请求完毕
		// switch (threadNum) {
		// case 1:
		// Constance.IS_FIRST_THREAD_DONE = true;
		// break;
		// case 2:
		// Constance.IS_SECOND_THREAD_DONE = true;
		// break;
		// case 3:
		// Constance.IS_THIRED_THREAD_DONE = true;
		// break;
		// }
		// }
		//
		// if (Constance.IS_FIRST_THREAD_DONE && Constance.IS_SECOND_THREAD_DONE
		// && Constance.IS_THIRED_THREAD_DONE) {
		// // 3个线程都跑完了
		// // TODO 通知切换ip
		// }
		// }
		// });
		// }
	}
}
