package util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.GZIPInputStream;

import org.json.JSONObject;

import bean.DeviceInfo;
import bean.ProxyIpBean;
import sun.misc.BASE64Encoder;
import util.listener.OnLoadAdListener;

public class NetUtil {
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
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
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 取消代理
			Properties prop = System.getProperties();
			prop.remove("http.proxyHost");
			prop.remove("http.proxyPort");
			prop.remove("https.proxyHost");  
	        prop.remove("https.proxyPort");
//			System.getProperties().put("proxySet", "true");
//			// 设置http访问要使用的代理服务器的地址
//			prop.setProperty("http.proxyHost", ipBean.ip);
//			// 设置http访问要使用的代理服务器的端口
//			prop.setProperty("http.proxyPort", ipBean.port + "");
			
			
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			String encodeString = new String(result.getBytes(), "UTF-8");
			result = encodeString;
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
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

//	/**
//	 * 更改代理
//	 * 
//	 * @return
//	 */
//	public static void changeProxy(String proxyUrl, int port) {
//		Properties prop = System.getProperties();
//		// 设置http访问要使用的代理服务器的地址
//		prop.setProperty("http.proxyHost", proxyUrl);
//		// 设置http访问要使用的代理服务器的端口
//		prop.setProperty("http.proxyPort", port + "");
//		// 设置不需要通过代理服务器访问的主机，可以使用*通配符，多个地址用|分隔
//		prop.setProperty("http.nonProxyHosts", "localhost|192.168.0.*");
//
//		// // 设置安全访问使用的代理服务器地址与端口
//		// // 它没有https.nonProxyHosts属性，它按照http.nonProxyHosts 中设置的规则访问
//		// prop.setProperty("https.proxyHost", "183.45.78.31");
//		// prop.setProperty("https.proxyPort", "443");
//		// // 使用ftp代理服务器的主机、端口以及不需要使用ftp代理服务器的主机
//		// prop.setProperty("ftp.proxyHost", "183.45.78.31");
//		// prop.setProperty("ftp.proxyPort", "21");
//		// prop.setProperty("ftp.nonProxyHosts", "localhost|192.168.0.*");
//		// // socks代理服务器的地址与端口
//		// prop.setProperty("socksProxyHost", "183.45.78.31");
//		// prop.setProperty("socksProxyPort", "1080");
//		// // 设置登陆到代理服务器的用户名和密码
//		// Authenticator.setDefault(new MyAuthenticator("userName",
//		// "Password"));
//
//		System.out.println("设置代理完成！！！");
//	}

//	static class MyAuthenticator extends Authenticator {
//		private String user = "";
//		private String password = "";
//
//		public MyAuthenticator(String user, String password) {
//			this.user = user;
//			this.password = password;
//		}
//
//		protected PasswordAuthentication getPasswordAuthentication() {
//			return new PasswordAuthentication(user, password.toCharArray());
//		}
//	}

//	public static String requestByProxy(String requestUrl, String proxyUrl, int proxyPort) {
//
//		String result = "";
//		try {
//			URL url = new URL("http://ws.voiceads.cn/ad/request");
//			// /创建代理服务器
//			InetSocketAddress addr = new InetSocketAddress(proxyUrl, proxyPort);
//			// Proxy proxy = new Proxy(Proxy.Type.SOCKS, addr); // Socket 代理
//			Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
//			// Authenticator.setDefault(new MyAuthenticator("username",
//			// "password"));// 设置代理的用户和密码
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);// 设置代理访问
//
//			// 发送POST请求必须设置如下两行
//			connection.setDoOutput(true);
//			connection.setRequestMethod("POST");
//			connection.setRequestProperty("Content-Type", "text/xml");
//
//			// 设置讯飞消息头
//			connection.setRequestProperty("X-protocol-ver", "2.0");
//
//			InputStreamReader in = new InputStreamReader(connection.getInputStream());
//			BufferedReader reader = new BufferedReader(in);
//			String line;
//			while ((line = reader.readLine()) != null) {
//				result += line;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			result = "";
//		}
//		return result;
//	}

//	/**
//	 * 获取代理ip地址
//	 */
//	public static synchronized String getProxyIp(String proxyIp, int proxyPort) {
//
//		// 设置代理
//		// System.setProperty("http.proxySet", "true");
//		// System.setProperty("http.proxyHost", proxyIp);
//		// System.setProperty("http.proxyPort", proxyPort + "");
//
//		HttpURLConnection urlConnection = null;
//		String result = "";
//		InputStream inputStream = null;
//		InputStreamReader inReader = null;
//		BufferedReader reader = null;
//		try {
//			URL url = new URL("http://xd.livevvv.com/kdxf/ip");
//
//			// // 封装代理連接主机IP与端口号。
//			// InetSocketAddress inetAddress = new InetSocketAddress(proxyIp,
//			// proxyPort);
//			// // 根据URL链接获取代理类型，本链接适用于TYPE.HTTP
//			// java.net.Proxy.Type proxyType =
//			// java.net.Proxy.Type.valueOf(url.getProtocol().toUpperCase());
//			// java.net.Proxy javaProxy = new java.net.Proxy(proxyType,
//			// inetAddress);
//
//			Properties prop = System.getProperties();
//			System.getProperties().put("proxySet", "true");
//			// 设置http访问要使用的代理服务器的地址
//			prop.setProperty("http.proxyHost", proxyIp);
//			// 设置http访问要使用的代理服务器的端口
//			prop.setProperty("http.proxyPort", proxyPort + "");
//
//			urlConnection = (HttpURLConnection) url.openConnection();
//			// urlConnection = (HttpURLConnection)
//			// url.openConnection(javaProxy);
//
//			String authentication = "15118042006:lljaifeifei0816"; // 用户名密码
//			String encodedLogin = new BASE64Encoder().encode(authentication.getBytes()); // 编码
//			urlConnection.setRequestProperty("Proxy-Authorization", " Basic " + encodedLogin);
//
//			urlConnection.setDoInput(true);
//
//			urlConnection.setConnectTimeout(10000);
//			urlConnection.setReadTimeout(10000);
//			urlConnection.setRequestMethod("POST");
//
//			inputStream = urlConnection.getInputStream();
//			inReader = new InputStreamReader(inputStream);
//			reader = new BufferedReader(inReader);
//			String line;
//			while ((line = reader.readLine()) != null) {
//				result += line;
//			}
//			result = new String(result.getBytes(), "utf-8");
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			result = "";
//		} finally {
//			urlConnection.disconnect();
//			try {
//				if (inputStream != null) {
//					inputStream.close();
//				}
//				if (inReader != null) {
//					inReader.close();
//				}
//				if (reader != null) {
//					// try {
//					reader.close();
//					// } catch (Exception e) {
//					// // TODO Auto-generated catch block
//					// e.printStackTrace();
//					// }
//				}
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//
//		}
//		return result;
//	}

//	/**
//	 * 获取真实ip地址
//	 */
//	public static String getRealIp(String proxyIp, int proxyPort) {
//
//		// HttpURLConnection urlConnection = null;
//		// String result = "";
//		// BufferedReader reader = null;
//		// try {
//		// URL url = new URL("http://ip.chinaz.com/getip.aspx");
//		//
//		// // 设置代理
//		// Properties prop = System.getProperties();
//		// System.getProperties().put("proxySet", "true");
//		// // 设置http访问要使用的代理服务器的地址
//		// prop.setProperty("http.proxyHost", proxyIp);
//		// // 设置http访问要使用的代理服务器的端口
//		// prop.setProperty("http.proxyPort", proxyPort + "");
//		//
//		// urlConnection = (HttpURLConnection) url.openConnection();
//		//
//		// String authentication = "username:password"; // 用户名密码
//		//// String encodedLogin = new
//		// BASE64Encoder().encode(authentication.getBytes()); // 编码
//		// urlConnection.setRequestProperty("Proxy-Authorization", " Basic " +
//		// authentication);
//		//
//		// urlConnection.setDoInput(true);
//		//
//		// urlConnection.setConnectTimeout(10000);
//		// urlConnection.setReadTimeout(10000);
//		// urlConnection.setRequestMethod("POST");
//		//
//		//
//		// reader = new BufferedReader(new
//		// InputStreamReader(urlConnection.getInputStream()));
//		// String line;
//		// while ((line = reader.readLine()) != null) {
//		// result += line;
//		// System.out.println("line2--->>" + line);
//		// }
//		// result = new String(result.getBytes(), "utf-8");
//		// } catch (Exception e) {
//		// e.printStackTrace();
//		// result = "";
//		// } finally {
//		// urlConnection.disconnect();
//		// if (reader != null) {
//		// try {
//		// reader.close();
//		// } catch (Exception e) {
//		// // TODO Auto-generated catch block
//		// e.printStackTrace();
//		// }
//		// }
//		// }
//		//
//		// return result;
//
//		// 设置代理
//		// System.setProperty("http.proxySet", "true");
//		// System.setProperty("http.proxyHost", proxyIp);
//		// System.setProperty("http.proxyPort", proxyPort + "");
//
//		HttpURLConnection urlConnection = null;
//		String result = "";
//		InputStream inputStream = null;
//		InputStreamReader inReader = null;
//		BufferedReader reader = null;
//		try {
//			URL url = new URL("http://ip.chinaz.com/getip.aspx");
//
//			// // 封装代理連接主机IP与端口号。
//			// InetSocketAddress inetAddress = new InetSocketAddress(proxyIp,
//			// proxyPort);
//			// // 根据URL链接获取代理类型，本链接适用于TYPE.HTTP
//			// java.net.Proxy.Type proxyType =
//			// java.net.Proxy.Type.valueOf(url.getProtocol().toUpperCase());
//			// java.net.Proxy javaProxy = new java.net.Proxy(proxyType,
//			// inetAddress);
//
//			Properties prop = System.getProperties();
//			System.getProperties().put("proxySet", "true");
//			// 设置http访问要使用的代理服务器的地址
//			prop.setProperty("http.proxyHost", proxyIp);
//			// 设置http访问要使用的代理服务器的端口
//			prop.setProperty("http.proxyPort", proxyPort + "");
//
//			urlConnection = (HttpURLConnection) url.openConnection();
//			// urlConnection = (HttpURLConnection)
//			// url.openConnection(javaProxy);
//
//			String authentication = "username:password"; // 用户名密码
//			// String encodedLogin = new
//			// BASE64Encoder().encode(authentication.getBytes()); // 编码
//			urlConnection.setRequestProperty("Proxy-Authorization", " Basic " + authentication);
//
//			urlConnection.setDoInput(true);
//
//			urlConnection.setConnectTimeout(10000);
//			urlConnection.setReadTimeout(10000);
//			urlConnection.setRequestMethod("POST");
//
//			inputStream = urlConnection.getInputStream();
//			inReader = new InputStreamReader(inputStream, "UTF-8");
//			reader = new BufferedReader(inReader);
//			String line;
//			while ((line = reader.readLine()) != null) {
//				result += line;
//			}
//			result = new String(result.getBytes(), "utf-8");
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			result = "";
//		} finally {
//			urlConnection.disconnect();
//			try {
//				if (inputStream != null) {
//					inputStream.close();
//				}
//				if (inReader != null) {
//					inReader.close();
//				}
//				if (reader != null) {
//					// try {
//					reader.close();
//					// } catch (Exception e) {
//					// // TODO Auto-generated catch block
//					// e.printStackTrace();
//					// }
//				}
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//
//		}
//		return result;
//	}

	/**
	 * 检测是否有效的代理ip
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
			// 设置http访问要使用的代理服务器的地址
			prop.setProperty("http.proxyHost", ipBean.ip);
			// 设置http访问要使用的代理服务器的端口
			prop.setProperty("http.proxyPort", ipBean.port + "");

			urlConnection = (HttpURLConnection) url.openConnection();

			String authentication = "15118042006:lljaifeifei0816"; // 用户名密码
			String encodedLogin = new BASE64Encoder().encode(authentication.getBytes()); // 编码
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
			
			JSONObject object = new JSONObject(result);
			String ip = object.getString("ip");
			if(TextUtils.equals(ip, ipBean.ip)) {
				// 是有效的额代理ip
				isValia = true;
			}else {
				// 无效的代理ip
				isValia = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			isValia = false;
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
	 * 通过代理请求一个链接(讯飞上报)
	 */
	public static void requestUrlByProxy(ProxyIpBean ipBean, String reportUrl) {
		HttpURLConnection urlConnection = null;
//		String result = "";
//		BufferedReader reader = null;
		System.out.println("上报链接---->>>"+reportUrl);
		try {
			URL url = new URL(reportUrl);
			Properties prop = System.getProperties();
			System.getProperties().put("proxySet", "true");
			// 设置http访问要使用的代理服务器的地址
			prop.setProperty("http.proxyHost", ipBean.ip);
			// 设置http访问要使用的代理服务器的端口
			prop.setProperty("http.proxyPort", ipBean.port + "");

			urlConnection = (HttpURLConnection) url.openConnection();

			String authentication = "15118042006:lljaifeifei0816"; // 用户名密码
			String encodedLogin = new BASE64Encoder().encode(authentication.getBytes()); // 编码
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
	 * 通过代理请求多个链接(讯飞上报)
	 */
	public static void requestUrlsByProxy(ProxyIpBean ipBean, String[] reportUrls) {
		int length = reportUrls.length;
		for (int i = 0; i < length; i++) {
			requestUrlByProxy(ipBean, reportUrls[i]);
		}
	}

	/**
	 * 获取讯飞广告数据
	 */
	public static void requestKDXFAdInfos(DeviceInfo deviceInfo, ProxyIpBean ipBean,int[] showAdWidthAndHeight, String adUnitId,boolean isBoot, String appId, String appName, String packageName,
			OnLoadAdListener listener) {

		HttpURLConnection urlConnection = null;
		String result = "";
		BufferedReader reader = null;
		try {

			JSONObject requestBodyJson = new JSONObject();
			requestBodyJson.put("tramaterialtype", "json");

			// 广告位id
			requestBodyJson.put("adunitid", adUnitId);
			// 是否支持deepLink
			// requestBodyJson.put("is_support_deeplink",0);
			// 广告位宽度
			requestBodyJson.put("adw", showAdWidthAndHeight[0]);
			// 广告位高度
			requestBodyJson.put("adh", showAdWidthAndHeight[1]);

			// 是否支持deepLink 0=不支持，1=支持
			requestBodyJson.put("is_support_deeplink", 1);
			// 设备类型 -1=未知，0=phone,1=pad,2=pc,3=tv, 4=wap
			requestBodyJson.put("devicetype", deviceInfo.deviceType);
			// 操作系统类型
			requestBodyJson.put("os", "Android");
			// 操作系统版本号
			requestBodyJson.put("osv", deviceInfo.osVersion);
			requestBodyJson.put("adid", deviceInfo.androidId);
			requestBodyJson.put("imei", deviceInfo.imei);
			requestBodyJson.put("mac", deviceInfo.mac);
			requestBodyJson.put("density", deviceInfo.density);
			requestBodyJson.put("operator", deviceInfo.operator);
			// userAgent
			requestBodyJson.put("ua", deviceInfo.userAgent);
			requestBodyJson.put("ts", System.currentTimeMillis());
			// 设备屏幕宽度
			requestBodyJson.put("dvw", deviceInfo.deviceScreenWidth);
			// 设备屏幕高度
			requestBodyJson.put("dvh", deviceInfo.deviceScreenHeight);
			// 横竖屏 0=竖屏，1=横屏
			requestBodyJson.put("orientation", deviceInfo.orientation);
			// 设备生产商
			requestBodyJson.put("vendor", deviceInfo.vendor);
			// 设备型号
			requestBodyJson.put("model", deviceInfo.model);
			requestBodyJson.put("net", deviceInfo.net);
			requestBodyJson.put("ip", ipBean.ip);
			// 使用语言
			requestBodyJson.put("lan", deviceInfo.language);

			// 是否开屏 1=开屏，0=非开屏
			requestBodyJson.put("isboot", isBoot ? 1 : 0);
			// 请求批量下发广告的数量，目前只能为”1”
			requestBodyJson.put("batch_cnt", "1");
			// appId 和讯飞后台保持一致
			requestBodyJson.put("appid", appId);
			// app名称 和讯飞后台保持一致
			requestBodyJson.put("appname", appName);
			// app包名 和讯飞后台保持一致
			requestBodyJson.put("pkgname", packageName);
			
			// 调试数据
			JSONObject debugObject = new JSONObject();
			//0，不限制 1，跳转类； 2，下载类;不指定的话，按值为 0 处理。
			debugObject.put("action_typ", 1);
			// 0，不限制；1 ，包含 landing_url 和deep_link ； 2 ， 仅 包 含landing_url ； 3 ， 仅 包 含deep_link。不指定的话，按值为 0 处理
			debugObject.put("landing_type", 2);
			requestBodyJson.put("debug", debugObject);

			URL url = new URL("http://ws.voiceads.cn/ad/request");

			Properties prop = System.getProperties();
			System.getProperties().put("proxySet", "true");
			// 设置http访问要使用的代理服务器的地址
			prop.setProperty("http.proxyHost", ipBean.ip);
			// 设置http访问要使用的代理服务器的端口
			prop.setProperty("http.proxyPort", ipBean.port + "");

			urlConnection = (HttpURLConnection) url.openConnection();

			String authentication = "15118042006:lljaifeifei0816"; // 用户名密码
			String encodedLogin = new BASE64Encoder().encode(authentication.getBytes()); // 编码
			urlConnection.setRequestProperty("Proxy-Authorization", " Basic " + encodedLogin);
			// 设置请求属性
			urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlConnection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			urlConnection.setRequestProperty("Charset", "UTF-8");
			// 连接,也可以不用明文connect，使用下面的httpConn.get
			// 设置讯飞需要添加的header
			urlConnection.setRequestProperty("X-protocol-ver", "2.0");
			urlConnection.setRequestProperty("Accept-Encoding", "gzip");
			urlConnection.setRequestProperty("Accept-Charset", "utf-8");
			urlConnection.setRequestProperty("Content-Type", "text/html; charset=utf-8");  

			// 设置参数
			urlConnection.setDoOutput(true); // 需要输出
			urlConnection.setDoInput(true); // 需要输入
			urlConnection.setUseCaches(false); // 不允许缓存

			urlConnection.setConnectTimeout(10000);
			urlConnection.setReadTimeout(10000);
			urlConnection.setRequestMethod("POST");

			// 连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
			urlConnection.connect();
			// 建立输入流，向指向的URL传入参数
			DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
			dos.writeBytes(requestBodyJson.toString());
			dos.flush();
			dos.close();

			// 获得响应状态
			int resultCode = urlConnection.getResponseCode();
			if (HttpURLConnection.HTTP_OK == resultCode) {
				// 讯飞有数据返回
				StringBuffer sb = new StringBuffer();
				String readLine = new String();
				GZIPInputStream gis =new GZIPInputStream(urlConnection.getInputStream());
				
				
//				reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
				reader = new BufferedReader(new InputStreamReader(gis,"utf-8"));
				while ((readLine = reader.readLine()) != null) {
					sb.append(readLine).append("\n");
//					System.out.println("utf-8--->>>"+new String(readLine.getBytes(),"utf-8"));
				}
				reader.close();
				result = sb.toString();
				System.out.println("讯飞广告数据返回result---->>>" + result);

				try {
					JSONObject resultObject = new JSONObject(result);
					if (resultObject.getInt("rc") == 70200) {
						// 请求广告成功、下发广告成功
						listener.onLoadSuccess(resultObject, showAdWidthAndHeight);
					} else {
						// 连接到服务器成功，但出现一些错误，下发广告失败
						listener.onLoadFailed(result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("服务器报错，无法响应，返回码---->>>" + resultCode);
				// 这里rspPacket返回的actions 是请求TAG
				result = "服务器报错，无法响应，返回码---->>>" + resultCode;
				listener.onLoadFailed(result);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "请求讯飞广告出现异常！";
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
	
	
    
    /** 
     * 从网络Url中下载文件 
     * @param urlStr 
     * @param fileName 
     * @param savePath 
     * @throws IOException 
     */  
    public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{  
        URL url = new URL(urlStr);    
        
//        Properties prop = System.getProperties();
//		System.getProperties().put("proxySet", "true");
//		// 设置http访问要使用的代理服务器的地址
//		prop.setProperty("http.proxyHost", ipBean.ip);
//		// 设置http访问要使用的代理服务器的端口
//		prop.setProperty("http.proxyPort", ipBean.port + "");
		
        // 取消代理
        Properties prop = System.getProperties();
        prop.remove("http.proxyHost");
        prop.remove("http.proxyPort");
        prop.remove("https.proxyHost");  
        prop.remove("https.proxyPort");
		
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
        
//        String authentication = "15118042006:lljaifeifei0816"; // 用户名密码
//		String encodedLogin = new BASE64Encoder().encode(authentication.getBytes()); // 编码
//		conn.setRequestProperty("Proxy-Authorization", " Basic " + encodedLogin);
		
                //设置超时间为3秒  
        conn.setConnectTimeout(3*1000);  
//        //防止屏蔽程序抓取而返回403错误  
//        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
  
        //得到输入流  
        InputStream inputStream = conn.getInputStream();    
        //获取自己数组  
        byte[] getData = readInputStream(inputStream);      
  
        //文件保存位置  
        File saveDir = new File(savePath);  
        if(!saveDir.exists()){  
            saveDir.mkdir();  
        }  
        File file = new File(saveDir+File.separator+fileName);      
        FileOutputStream fos = new FileOutputStream(file);       
        fos.write(getData);   
        if(fos!=null){  
            fos.close();    
        }  
        if(inputStream!=null){  
            inputStream.close();  
        }  
  
  
        System.out.println("info:"+url+" download success");   
  
    }  
    
    /** 
     * 从输入流中获取字节数组 
     * @param inputStream 
     * @return 
     * @throws IOException 
     */  
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {    
        byte[] buffer = new byte[1024];    
        int len = 0;    
        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
        while((len = inputStream.read(buffer)) != -1) {    
            bos.write(buffer, 0, len);    
        }    
        bos.close();    
        return bos.toByteArray();    
    } 
  

}
