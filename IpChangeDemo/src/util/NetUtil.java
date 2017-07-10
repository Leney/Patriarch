package util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONObject;

import bean.DeviceInfo;
import bean.ProxyIpBean;
import sun.misc.BASE64Encoder;
import util.listener.OnLoadAdListener;

public class NetUtil {
	/**
	 * ��ָ��URL����GET����������
	 * 
	 * @param url
	 *            ���������URL
	 * @param param
	 *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
	 * @return URL ������Զ����Դ����Ӧ���
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// �򿪺�URL֮�������
			URLConnection connection = realUrl.openConnection();
			// ����ͨ�õ���������
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// ����ʵ�ʵ�����
			connection.connect();
			// ��ȡ������Ӧͷ�ֶ�
			Map<String, List<String>> map = connection.getHeaderFields();
			// �������е���Ӧͷ�ֶ�
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// ���� BufferedReader����������ȡURL����Ӧ
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("����GET��������쳣��" + e);
			e.printStackTrace();
		}
		// ʹ��finally�����ر�������
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * ��ָ�� URL ����POST����������
	 * 
	 * @param url
	 *            ��������� URL
	 * @param param
	 *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
	 * @return ������Զ����Դ����Ӧ���
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// �򿪺�URL֮�������
			URLConnection conn = realUrl.openConnection();
			// ����ͨ�õ���������
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// ����POST�������������������
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// ��ȡURLConnection�����Ӧ�������
			out = new PrintWriter(conn.getOutputStream());
			// �����������
			out.print(param);
			// flush������Ļ���
			out.flush();
			// ����BufferedReader����������ȡURL����Ӧ
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			String encodeString = new String(result.getBytes(), "UTF-8");
			result = encodeString;
		} catch (Exception e) {
			System.out.println("���� POST ��������쳣��" + e);
			e.printStackTrace();
		}
		// ʹ��finally�����ر��������������
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * ���Ĵ���
	 * 
	 * @return
	 */
	public static void changeProxy(String proxyUrl, int port) {
		Properties prop = System.getProperties();
		// ����http����Ҫʹ�õĴ���������ĵ�ַ
		prop.setProperty("http.proxyHost", proxyUrl);
		// ����http����Ҫʹ�õĴ���������Ķ˿�
		prop.setProperty("http.proxyPort", port + "");
		// ���ò���Ҫͨ��������������ʵ�����������ʹ��*ͨ����������ַ��|�ָ�
		prop.setProperty("http.nonProxyHosts", "localhost|192.168.0.*");

		// // ���ð�ȫ����ʹ�õĴ����������ַ��˿�
		// // ��û��https.nonProxyHosts���ԣ�������http.nonProxyHosts �����õĹ������
		// prop.setProperty("https.proxyHost", "183.45.78.31");
		// prop.setProperty("https.proxyPort", "443");
		// // ʹ��ftp������������������˿��Լ�����Ҫʹ��ftp���������������
		// prop.setProperty("ftp.proxyHost", "183.45.78.31");
		// prop.setProperty("ftp.proxyPort", "21");
		// prop.setProperty("ftp.nonProxyHosts", "localhost|192.168.0.*");
		// // socks����������ĵ�ַ��˿�
		// prop.setProperty("socksProxyHost", "183.45.78.31");
		// prop.setProperty("socksProxyPort", "1080");
		// // ���õ�½��������������û���������
		// Authenticator.setDefault(new MyAuthenticator("userName",
		// "Password"));

		System.out.println("���ô�����ɣ�����");
	}

	static class MyAuthenticator extends Authenticator {
		private String user = "";
		private String password = "";

		public MyAuthenticator(String user, String password) {
			this.user = user;
			this.password = password;
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(user, password.toCharArray());
		}
	}

	public static String requestByProxy(String requestUrl, String proxyUrl, int proxyPort) {

		String result = "";
		try {
			URL url = new URL("http://ws.voiceads.cn/ad/request");
			// /�������������
			InetSocketAddress addr = new InetSocketAddress(proxyUrl, proxyPort);
			// Proxy proxy = new Proxy(Proxy.Type.SOCKS, addr); // Socket ����
			Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http ����
			// Authenticator.setDefault(new MyAuthenticator("username",
			// "password"));// ���ô�����û�������
			HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);// ���ô������

			// ����POST�������������������
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "text/xml");

			// ����Ѷ����Ϣͷ
			connection.setRequestProperty("X-protocol-ver", "2.0");

			InputStreamReader in = new InputStreamReader(connection.getInputStream());
			BufferedReader reader = new BufferedReader(in);
			String line;
			while ((line = reader.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "";
		}
		return result;
	}

	/**
	 * ��ȡ����ip��ַ
	 */
	public static String getProxyIp(String proxyIp, int proxyPort) {

		// ���ô���
		// System.setProperty("http.proxySet", "true");
		// System.setProperty("http.proxyHost", proxyIp);
		// System.setProperty("http.proxyPort", proxyPort + "");

		HttpURLConnection urlConnection = null;
		String result = "";
		InputStream inputStream = null;
		InputStreamReader inReader = null;
		BufferedReader reader = null;
		try {
			URL url = new URL("http://xd.livevvv.com/kdxf/ip");

			// // ��װ�����B������IP��˿ںš�
			// InetSocketAddress inetAddress = new InetSocketAddress(proxyIp,
			// proxyPort);
			// // ����URL���ӻ�ȡ�������ͣ�������������TYPE.HTTP
			// java.net.Proxy.Type proxyType =
			// java.net.Proxy.Type.valueOf(url.getProtocol().toUpperCase());
			// java.net.Proxy javaProxy = new java.net.Proxy(proxyType,
			// inetAddress);

			Properties prop = System.getProperties();
			System.getProperties().put("proxySet", "true");
			// ����http����Ҫʹ�õĴ���������ĵ�ַ
			prop.setProperty("http.proxyHost", proxyIp);
			// ����http����Ҫʹ�õĴ���������Ķ˿�
			prop.setProperty("http.proxyPort", proxyPort + "");

			urlConnection = (HttpURLConnection) url.openConnection();
			// urlConnection = (HttpURLConnection)
			// url.openConnection(javaProxy);

			String authentication = "15118042006:lljaifeifei0816"; // �û�������
			String encodedLogin = new BASE64Encoder().encode(authentication.getBytes()); // ����
			urlConnection.setRequestProperty("Proxy-Authorization", " Basic " + encodedLogin);

			urlConnection.setDoInput(true);

			urlConnection.setConnectTimeout(10000);
			urlConnection.setReadTimeout(10000);
			urlConnection.setRequestMethod("POST");

			inputStream = urlConnection.getInputStream();
			inReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inReader);
			String line;
			while ((line = reader.readLine()) != null) {
				result += line;
			}
			result = new String(result.getBytes(), "utf-8");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "";
		} finally {
			urlConnection.disconnect();
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (inReader != null) {
					inReader.close();
				}
				if (reader != null) {
					// try {
					reader.close();
					// } catch (Exception e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
		return result;
	}

	/**
	 * ��ȡ��ʵip��ַ
	 */
	public static String getRealIp(String proxyIp, int proxyPort) {

		// HttpURLConnection urlConnection = null;
		// String result = "";
		// BufferedReader reader = null;
		// try {
		// URL url = new URL("http://ip.chinaz.com/getip.aspx");
		//
		// // ���ô���
		// Properties prop = System.getProperties();
		// System.getProperties().put("proxySet", "true");
		// // ����http����Ҫʹ�õĴ���������ĵ�ַ
		// prop.setProperty("http.proxyHost", proxyIp);
		// // ����http����Ҫʹ�õĴ���������Ķ˿�
		// prop.setProperty("http.proxyPort", proxyPort + "");
		//
		// urlConnection = (HttpURLConnection) url.openConnection();
		//
		// String authentication = "username:password"; // �û�������
		//// String encodedLogin = new
		// BASE64Encoder().encode(authentication.getBytes()); // ����
		// urlConnection.setRequestProperty("Proxy-Authorization", " Basic " +
		// authentication);
		//
		// urlConnection.setDoInput(true);
		//
		// urlConnection.setConnectTimeout(10000);
		// urlConnection.setReadTimeout(10000);
		// urlConnection.setRequestMethod("POST");
		//
		//
		// reader = new BufferedReader(new
		// InputStreamReader(urlConnection.getInputStream()));
		// String line;
		// while ((line = reader.readLine()) != null) {
		// result += line;
		// System.out.println("line2--->>" + line);
		// }
		// result = new String(result.getBytes(), "utf-8");
		// } catch (Exception e) {
		// e.printStackTrace();
		// result = "";
		// } finally {
		// urlConnection.disconnect();
		// if (reader != null) {
		// try {
		// reader.close();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }
		//
		// return result;

		// ���ô���
		// System.setProperty("http.proxySet", "true");
		// System.setProperty("http.proxyHost", proxyIp);
		// System.setProperty("http.proxyPort", proxyPort + "");

		HttpURLConnection urlConnection = null;
		String result = "";
		InputStream inputStream = null;
		InputStreamReader inReader = null;
		BufferedReader reader = null;
		try {
			URL url = new URL("http://ip.chinaz.com/getip.aspx");

			// // ��װ�����B������IP��˿ںš�
			// InetSocketAddress inetAddress = new InetSocketAddress(proxyIp,
			// proxyPort);
			// // ����URL���ӻ�ȡ�������ͣ�������������TYPE.HTTP
			// java.net.Proxy.Type proxyType =
			// java.net.Proxy.Type.valueOf(url.getProtocol().toUpperCase());
			// java.net.Proxy javaProxy = new java.net.Proxy(proxyType,
			// inetAddress);

			Properties prop = System.getProperties();
			System.getProperties().put("proxySet", "true");
			// ����http����Ҫʹ�õĴ���������ĵ�ַ
			prop.setProperty("http.proxyHost", proxyIp);
			// ����http����Ҫʹ�õĴ���������Ķ˿�
			prop.setProperty("http.proxyPort", proxyPort + "");

			urlConnection = (HttpURLConnection) url.openConnection();
			// urlConnection = (HttpURLConnection)
			// url.openConnection(javaProxy);

			String authentication = "username:password"; // �û�������
			// String encodedLogin = new
			// BASE64Encoder().encode(authentication.getBytes()); // ����
			urlConnection.setRequestProperty("Proxy-Authorization", " Basic " + authentication);

			urlConnection.setDoInput(true);

			urlConnection.setConnectTimeout(10000);
			urlConnection.setReadTimeout(10000);
			urlConnection.setRequestMethod("POST");

			inputStream = urlConnection.getInputStream();
			inReader = new InputStreamReader(inputStream, "UTF-8");
			reader = new BufferedReader(inReader);
			String line;
			while ((line = reader.readLine()) != null) {
				result += line;
			}
			result = new String(result.getBytes(), "utf-8");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "";
		} finally {
			urlConnection.disconnect();
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (inReader != null) {
					inReader.close();
				}
				if (reader != null) {
					// try {
					reader.close();
					// } catch (Exception e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
		return result;
	}

	/**
	 * ����Ƿ���Ч�Ĵ���ip
	 */
	public static boolean isValidProxyIp(ProxyIpBean ipBean) {

		boolean isValia = false;
		HttpURLConnection urlConnection = null;
		String result = "";
		InputStream inputStream = null;
		InputStreamReader inReader = null;
		BufferedReader reader = null;
		try {
			URL url = new URL("http://xd.livevvv.com/kdxf/ip");
			Properties prop = System.getProperties();
			System.getProperties().put("proxySet", "true");
			// ����http����Ҫʹ�õĴ���������ĵ�ַ
			prop.setProperty("http.proxyHost", ipBean.ip);
			// ����http����Ҫʹ�õĴ���������Ķ˿�
			prop.setProperty("http.proxyPort", ipBean.port + "");

			urlConnection = (HttpURLConnection) url.openConnection();

			String authentication = "15118042006:lljaifeifei0816"; // �û�������
			String encodedLogin = new BASE64Encoder().encode(authentication.getBytes()); // ����
			urlConnection.setRequestProperty("Proxy-Authorization", " Basic " + encodedLogin);

			urlConnection.setDoInput(true);

			urlConnection.setConnectTimeout(10000);
			urlConnection.setReadTimeout(10000);
			urlConnection.setRequestMethod("POST");

			inputStream = urlConnection.getInputStream();
			inReader = new InputStreamReader(inputStream, "UTF-8");
			reader = new BufferedReader(inReader);
			String line;
			while ((line = reader.readLine()) != null) {
				result += line;
			}
			result = new String(result.getBytes(), "utf-8");
			// TODO ������ȡ��ǰip�Ľ�� �ж��Ƿ����ô���ɹ�

		} catch (Exception e) {
			e.printStackTrace();
			result = "";
		} finally {
			urlConnection.disconnect();
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (inReader != null) {
					inReader.close();
				}
				if (reader != null) {
					reader.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
		return isValia;
	}

	/**
	 * ͨ����������һ������(Ѷ���ϱ�)
	 */
	public static void requestUrlByProxy(ProxyIpBean ipBean, String reportUrl) {
		HttpURLConnection urlConnection = null;
//		String result = "";
//		BufferedReader reader = null;
		try {
			URL url = new URL(reportUrl);
			Properties prop = System.getProperties();
			System.getProperties().put("proxySet", "true");
			// ����http����Ҫʹ�õĴ���������ĵ�ַ
			prop.setProperty("http.proxyHost", ipBean.ip);
			// ����http����Ҫʹ�õĴ���������Ķ˿�
			prop.setProperty("http.proxyPort", ipBean.port + "");

			urlConnection = (HttpURLConnection) url.openConnection();

			String authentication = "15118042006:lljaifeifei0816"; // �û�������
			String encodedLogin = new BASE64Encoder().encode(authentication.getBytes()); // ����
			urlConnection.setRequestProperty("Proxy-Authorization", " Basic " + encodedLogin);

			urlConnection.setDoInput(true);

			urlConnection.setConnectTimeout(10000);
			urlConnection.setReadTimeout(10000);
			urlConnection.setRequestMethod("POST");
			urlConnection.connect();

//			reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
//			String line;
//			while ((line = reader.readLine()) != null) {
//				result += line;
//			}
//			result = new String(result.getBytes(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
//			result = "";
		} finally {
			urlConnection.disconnect();
//			try {
//				if (reader != null) {
//					reader.close();
//				}
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}

		}
	}
	
	/**
	 * ͨ����������������(Ѷ���ϱ�)
	 */
	public static void requestUrlsByProxy(ProxyIpBean ipBean, String[] reportUrls) {
		int length = reportUrls.length;
		for (int i = 0; i < length; i++) {
			requestUrlByProxy(ipBean, reportUrls[i]);
		}
	}

	/**
	 * ����Ѷ�� ��ȡ�������
	 */
	public static void requestKDXFAdInfos(DeviceInfo deviceInfo, ProxyIpBean ipBean, String adUnitId, int showAdWidth,
			int showAdHeight, boolean isBoot, String appId, String appName, String packageName,
			OnLoadAdListener listener) {

		HttpURLConnection urlConnection = null;
		String result = "";
		BufferedReader reader = null;
		try {

			JSONObject requestBodyJson = new JSONObject();
			requestBodyJson.put("tramaterialtype", "json");

			// ���λid
			requestBodyJson.put("adunitid", adUnitId);
			// �Ƿ�֧��deepLink
			// requestBodyJson.put("is_support_deeplink",0);
			// ���λ���
			requestBodyJson.put("adw", showAdWidth);
			// ���λ�߶�
			requestBodyJson.put("adh", showAdHeight);

			// �Ƿ�֧��deepLink 0=��֧�֣�1=֧��
			requestBodyJson.put("is_support_deeplink", 1);
			// �豸���� -1=δ֪��0=phone,1=pad,2=pc,3=tv, 4=wap
			requestBodyJson.put("devicetype", 0);
			// ����ϵͳ����
			requestBodyJson.put("os", "Android");
			// ����ϵͳ�汾��
			requestBodyJson.put("osv", deviceInfo.osVersion);
			requestBodyJson.put("adid", deviceInfo.androidId);
			requestBodyJson.put("imei", deviceInfo.imei);
			requestBodyJson.put("mac", deviceInfo.mac);
			requestBodyJson.put("density", deviceInfo.density);
			requestBodyJson.put("operator", deviceInfo.operator);
			// userAgent
			requestBodyJson.put("ua", deviceInfo.userAgent);
			requestBodyJson.put("ts", System.currentTimeMillis());
			// �豸��Ļ���
			requestBodyJson.put("dvw", deviceInfo.deviceScreenWidth);
			// �豸��Ļ�߶�
			requestBodyJson.put("dvh", deviceInfo.deviceScreenHeight);
			// ������ 0=������1=����
			requestBodyJson.put("orientation", deviceInfo.orientation);
			// �豸������
			requestBodyJson.put("vendor", deviceInfo.vendor);
			// �豸�ͺ�
			requestBodyJson.put("model", deviceInfo.model);
			requestBodyJson.put("net", deviceInfo.net);
			requestBodyJson.put("ip", ipBean.ip);
			// ʹ������
			requestBodyJson.put("lan", deviceInfo.language);

			// �Ƿ��� 1=������0=�ǿ���
			requestBodyJson.put("isboot", isBoot ? 1 : 0);
			// ���������·�����������Ŀǰֻ��Ϊ��1��
			requestBodyJson.put("batch_cnt", "1");
			// appId ��Ѷ�ɺ�̨����һ��
			requestBodyJson.put("appid", appId);
			// app���� ��Ѷ�ɺ�̨����һ��
			requestBodyJson.put("appname", appName);
			// app���� ��Ѷ�ɺ�̨����һ��
			requestBodyJson.put("pkgname", packageName);

			URL url = new URL("http://ws.voiceads.cn/ad/request");

			Properties prop = System.getProperties();
			System.getProperties().put("proxySet", "true");
			// ����http����Ҫʹ�õĴ���������ĵ�ַ
			prop.setProperty("http.proxyHost", ipBean.ip);
			// ����http����Ҫʹ�õĴ���������Ķ˿�
			prop.setProperty("http.proxyPort", ipBean.port + "");

			urlConnection = (HttpURLConnection) url.openConnection();

			String authentication = "15118042006:lljaifeifei0816"; // �û�������
			String encodedLogin = new BASE64Encoder().encode(authentication.getBytes()); // ����
			urlConnection.setRequestProperty("Proxy-Authorization", " Basic " + encodedLogin);
			// ������������
			urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlConnection.setRequestProperty("Connection", "Keep-Alive");// ά�ֳ�����
			urlConnection.setRequestProperty("Charset", "UTF-8");
			// ����,Ҳ���Բ�������connect��ʹ�������httpConn.get
			// ����Ѷ����Ҫ��ӵ�header
			urlConnection.setRequestProperty("X-protocol-ver", "2.0");

			// ���ò���
			urlConnection.setDoOutput(true); // ��Ҫ���
			urlConnection.setDoInput(true); // ��Ҫ����
			urlConnection.setUseCaches(false); // ��������

			urlConnection.setConnectTimeout(10000);
			urlConnection.setReadTimeout(10000);
			urlConnection.setRequestMethod("POST");

			// ����,Ҳ���Բ�������connect��ʹ�������httpConn.getOutputStream()���Զ�connect
			urlConnection.connect();
			// ��������������ָ���URL�������
			DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
			dos.writeBytes(requestBodyJson.toString());
			dos.flush();
			dos.close();

			// �����Ӧ״̬
			int resultCode = urlConnection.getResponseCode();
			if (HttpURLConnection.HTTP_OK == resultCode) {
				// Ѷ�������ݷ���
				StringBuffer sb = new StringBuffer();
				String readLine = new String();
				reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
				while ((readLine = reader.readLine()) != null) {
					sb.append(readLine).append("\n");
				}
				reader.close();
				result = sb.toString();

				try {
					JSONObject resultObject = new JSONObject(result);
					if (resultObject.getInt("rc") == 70200) {
						// ������ɹ����·����ɹ�
						listener.onLoadSuccess(resultObject, showAdWidth, showAdHeight);
					} else {
						// ���ӵ��������ɹ���������һЩ�����·����ʧ��
						listener.onLoadFailed(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				System.out.println("Ѷ�ɹ�����ݷ���result---->>>" + result);
			} else {
				System.out.println("�����������޷���Ӧ��������---->>>" + resultCode);
				// ����rspPacket���ص�actions ������TAG
				result = "�����������޷���Ӧ��������---->>>" + resultCode;
				listener.onLoadFailed(result);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "����Ѷ�ɹ������쳣��";
			listener.onLoadFailed(result);
		} finally {
			urlConnection.disconnect();
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
		// return result;
	}

}
