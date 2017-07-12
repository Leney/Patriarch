package util.listener;

import org.json.JSONObject;

/**
 * Ѷ�ɹ���������������
 * @author dell
 *
 */
public interface OnLoadAdListener {
	/**
     * ���سɹ�(�첽����)
     */
    void onLoadSuccess(JSONObject resultObject,int[] showAdWidthAndHeight);

    /**
     * ����ʧ��(�첽����)
     */
    void onLoadFailed(String msg);

    /**
     * �������(�첽����)
     */
    void onNetError(String msg);
}
