package util.listener;

import org.json.JSONObject;

/**
 * 讯飞广告请求网络监听器
 * @author dell
 *
 */
public interface OnLoadAdListener {
	/**
     * 加载成功(异步方法)
     */
    void onLoadSuccess(JSONObject resultObject,int[] showAdWidthAndHeight);

    /**
     * 加载失败(异步方法)
     */
    void onLoadFailed(String msg);

    /**
     * 网络错误(异步方法)
     */
    void onNetError(String msg);
}
