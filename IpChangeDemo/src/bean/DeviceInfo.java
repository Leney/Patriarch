package bean;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

/**
 * �ֻ��豸��Ϣʵ����
 * @author dell
 *
 */
public class DeviceInfo {
	public String imei;
    public String imsi;
    public String androidId;
    public String mac;
//    public String ip;
    public String model;
    /** ϵͳ�汾��*/
    public String osVersion;
    /** ��Ļ�ܶ�*/
    public String density;
    /** ������Ӫ��*/
    public String operator;
    /** �����user agent*/
    public String userAgent;
    /** �豸��Ļ���*/
    public int deviceScreenWidth;
    /** �豸��Ļ�߶�*/
    public int deviceScreenHeight;
    /** �豸����������*/
    public String vendor;
    /** ��������(0��δ֪��
     1��Ethernet�� 2��wifi��
     3���������磬δ֪����
     4���� 2G�� 5��������
     �磬 3G�� 6���������磬
     4G)*/
    public int net;
    /** ������ 0=������1=����*/
    public int orientation;
    /** ʹ������  zh-CN*/
    public String language;
    /**
     * �豸����
     * 1=δ֪ ��0=phone�� 1=pad ��2=pc ��3=tv ��4=wap
     */
    public int deviceType;
    
}
