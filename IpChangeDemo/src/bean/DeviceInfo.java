package bean;

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
}
