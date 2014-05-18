package org.creation.webtask.iicall;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.creation.webtask.common.CommonData;
import org.creation.webtask.common.MD5;

public class UserLogin {
	
	static String codeUrl="http://www1.iicall.com/index.php/main/code";
	static String loginUrl="http://www1.iicall.com/index.php/main/login";
	static String logoutUrl="http://www1.iicall.com/index.php/main/logout";
	
	static String user="13995495987";
	static String pwd="w1c2l33390";
	static String sex = "1";//性别，0=>女，1=>男
	
	private static HashMap<String, String> userLogin(HashMap<String, String> info) {
		HashMap<String, String> result=new HashMap<String, String>();
		
		try {
			codeUrl = Request.Get(codeUrl).execute().returnContent().asString();
			codeUrl=codeUrl.substring(codeUrl.indexOf("http"));
			BufferedImage image = ImageIO.read(new URL(codeUrl));
			String code = JOptionPane.showInputDialog(new ImageIcon(image),"输入验证码：");
			if (code!=null) {
				info.put("code",code);
				
				RequestBuilder builder=RequestBuilder.post().setUri(new URI(loginUrl));
				for (Iterator<String> iterator = info.keySet().iterator(); iterator.hasNext();) {
					String key = (String)iterator.next();
					builder.addParameter(key, info.get(key));
				}
				HttpUriRequest login=builder.build();
				
				BasicCookieStore cookieStore = new BasicCookieStore();
			    CloseableHttpClient httpclient = HttpClients.custom()
			            .setDefaultCookieStore(cookieStore)
			            .build();
			    
				CloseableHttpResponse response2 = httpclient.execute(login);
			    try {
			        HttpEntity entity = response2.getEntity();

			        System.out.println("Login form get: " + response2.getStatusLine());
			        System.out.println(EntityUtils.toString(entity,"UTF-8"));
			        EntityUtils.consume(entity);

			        System.out.println("Post logon cookies:");
			        List<Cookie> cookies = cookieStore.getCookies();
			        if (cookies.isEmpty()) {
			            System.out.println("None");
			        } else {
			            for (int i = 0; i < cookies.size(); i++) {
			                System.out.println("- " + cookies.get(i).toString());
			            }
			        }
			    } finally {
			        response2.close();
			    }
			}else {
				System.out.println("未输入验证码，略过操作...");
			}
		} catch (ClientProtocolException e) {
			result.put("state", "false");
			e.printStackTrace();
		} catch (MalformedURLException e) {
			result.put("state", "false");
			e.printStackTrace();
		} catch (ParseException e) {
			result.put("state", "false");
			e.printStackTrace();
		} catch (IOException e) {
			result.put("state", "false");
			e.printStackTrace();
		} catch (URISyntaxException e) {
			result.put("state", "false");
			e.printStackTrace();
		}
		return null;
	}
	
//	private static return_type name() {
//		
//	}
	
	/**
	 * 获取登录URL
	 * @param context
	 * @param user
	 * @param pwd
	 * @return
	 */
	private static String getLoginUrl(String user, String pwd) {
		String imei = "456156451534587";//默认IMEI串
		String imsi = "460020914541001";//默认IMSI串
		StringBuilder sb = new StringBuilder();
		sb.append("http://n.a.aliaotian.net/user/login.php?hwstatus=0&hwip=&ver=android(2.3.8)&imei=");
		sb.append(imei);
		sb.append("&imsi=");
		sb.append(imsi);
		sb.append("&username=");
		sb.append(user);
		sb.append("&pwd=");
		sb.append(MD5.md5("nvasd4JDS*(^$#" + user + pwd + "@$^"));
		return sb.toString();
	}
	
	/**
	 * 获取签到页面的URL
	 * @param user
	 * @param pwd
	 * @return
	 */
	private static String getSigninPageUrl(String user, String pwd) {
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("http://220.231.194.251/dayup/index.php/dayup?username=");
			sb.append(user);
			sb.append("&pwd=");
			sb.append(MD5.md5(user + "$%^2cDFs3" + pwd));
			sb.append("&ver=2.3.8");
			sb.append("&perform=android&rel=android&linkid=0");
			sb.append("&ad=yes&adids=3&title=%E6%AF%8F%E6%97%A5%E7%AD%BE%E5%88%B0%28%E7%A7%AF%E5%B0%91%E6%88%90%E5%A4%9A%29&expire=&t_expire=&weibostat=3");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	/**
	 * 获取提交签到信息的URL
	 * @param str 签到页面的HTML代码
	 * @param user 用户名
	 * @return
	 */
	private static String getSigninSubmitUrl(String str, String user) {
		Pattern pattern = Pattern.compile("var sid.*=.*'(.+)';");
		Matcher matcher = pattern.matcher(str);
		String sid = MD5.md5(user);
		while (matcher.find()) {
			sid = matcher.group(1);
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("http://220.231.194.251/dayup/index.php/ajax_data?");
		sb.append("callback=?&u=");
		sb.append(user);
		sb.append("&v=2.3.8&p=android&l=0&w=1&sid=");
		sb.append(sid);
		return sb.toString();
	}
	
	private static void getSiginResult(String str) {
		String resultFlag = "false";
		String resultStr = "未知错误！";
		String sex="1";
		
		if (str!=null && str.length() > 0) {
			try {
				FileWriter writer=new FileWriter(new File("1.log"));
				writer.write(str);
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			JSONObject jsonObj = JSONObject.fromObject(str);
			String data = String.valueOf(jsonObj.getInt("data"));
			String randomnumber = data.substring(3);
			int statuscode = Integer.valueOf(data.substring(0, 3));
			switch (statuscode) {
				case 201:
					resultFlag = "false";
					resultStr = "您参数有误,请稍后重试.";
					break;
					
				case 203:
					resultFlag = "false";
					resultStr = "您尚未激活爱聊,无法完成签到.";
					break;
					
				case 204:
					resultFlag = "false";
					resultStr = "签到程序里模拟的爱聊软件版本过低.";
					break;
					
				case 206:
					resultFlag = "false";
					resultStr = "签到程序里模拟的爱聊软件版本过低——iPhone.";
					break;
					
				case 211:
					resultFlag = "true";
					resultStr = "签到成功!恭喜您成功获得" + randomnumber + "个聊豆.";
					break;
					
				case 212:
					resultFlag = "true";
					resultStr = "签到成功!恭喜您成功获得" + randomnumber + "个聊豆.\n酷~！升级VIP会员，每天可签到两次。";
					break;
					
				case 213:
					resultFlag = "true";
					resultStr = "您今天已签到过了，获得了" + randomnumber + "个聊豆,请明天再来吧.";
					break;
					
				case 214:
					resultFlag = "true";
					resultStr = "您今天已签到过了, 获得了" + randomnumber + "个聊豆.\n酷~！升级VIP会员，每天可签到两次。";
					break;
					
				case 215:
					resultFlag = "true";
					resultStr = "您今天已签到过了, 获得了" + randomnumber + "个聊豆.\n酷~！升级VIP会员，每天可签到两次。";
					break;
					
				case 216:
					resultFlag = "true";
					resultStr = "您今天已签到过了, 获得了" + randomnumber + "个聊豆.\n酷~！升级VIP会员，每天可签到两次。";
					break;
					
				case 221:
					resultFlag = "true";
					resultStr = "签到成功!恭喜您成功获得" + randomnumber + "个聊豆.";
					break;
					
				case 222:
					resultFlag = "true";
					resultStr = "签到成功!恭喜您成功获得" + randomnumber + "个聊豆.";
					break;
					
				case 223:
					resultFlag = "true";
					resultStr = "签到成功!恭喜您成功获得" + randomnumber + "个聊豆——iPhone";
					break;
					
				case 205:
					resultFlag = "true";
					resultStr = "您今天已签到过了\n获得了" + randomnumber + "个聊豆,请明天再来吧.";
					break;
					
				case 224:
					//android
					resultFlag = "false";
					resultStr = "签到失败！\n您本月内至少在爱聊软件内下载一款精品软件才能签到。";
					break;
					
				case 225:
					//iphone
					resultFlag = "false";
					resultStr = "签到失败！\n您本月内至少在爱聊软件内下载一款精品软件才能签到。";
					break;

				case 230:
					resultFlag = "false";
					resultStr = "签到成功！已获得" + randomnumber + "个聊豆，但要在爱聊软件内与男生玩真心话" + randomnumber + "分钟以上才可到账";
					break;

				case 231:
					resultFlag = "false";
					resultStr = "签到成功！已获得" + randomnumber + "个聊豆，但需要在爱聊软件内成功下载一款精品软件才可到账";
					break;

				case 232:
					resultFlag = "false";
					if (sex.equals("0")) {
						//女生权利
						resultStr = "您今天已签到过了\n获得了" + randomnumber + "个聊豆,但需要在爱聊软件内与男生玩真心话" + randomnumber + "分钟以上即可到账";
					} else {
						//男生权利
						resultStr = "您今天已签到过了\n获得了" + randomnumber + "个聊豆,但需要在成功下载一款精品软件才可到账";
					}
					break;

				case 233:
					resultFlag = "false";
					resultStr = "签到失败！网络繁忙,请重试!";
					break;

				default:
					resultFlag = "false";
					resultStr = "登录成功，但提交签到请求后返回未知数据";
					break;
			}
			
		} else {
			resultFlag = "false";
			resultStr = "登录成功，但提交签到请求时出现未知错误";
		}
		System.out.println(resultFlag+":"+resultStr);
	}
	
	/**
	 * 获取用户的性别
	 * 因为爱聊在签到完成后会根据性别来决定是否奖励签到时间
	 * @param str 登录页面的html代码
	 * @return
	 */
	private static String getUserSex(String str) {
		String tempSex = "1";
		Pattern pattern  = Pattern.compile("var sex.*=.*'(.+)';");
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			tempSex = matcher.group(1);
		}
		return tempSex;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		HashMap<String, String> postInfo=new HashMap<String, String>();
//		postInfo.put("reguser","13995495987");
//		postInfo.put("password","w1c2l33390");
//		postInfo.put("op","dologin");
//		userLogin(postInfo);
		
		//存放Cookies的HashMap
//		HashMap<String, String> cookies = new HashMap<String, String>();
		
		String loginUrl = getLoginUrl(user,pwd);
		String signinPageUrl = getSigninPageUrl(user,pwd);
		String signinSubmitUrl="";
		String content = "";
		try {
			Executor executor = Executor.newInstance();
			System.out.println(loginUrl);
			Response response=executor.execute(Request.Get(loginUrl).userAgent(CommonData.UA_ANDROID).connectTimeout(CommonData.TIME_OUT));
//			cookies.putAll(response);
			
//			content = response.returnContent().asString();
			content = EntityUtils.toString(response.returnResponse().getEntity(),"utf-8");
//			response.saveContent(new File(System.currentTimeMillis()+".log"));
			System.out.println(content);
			if (content.contains("登陆正确")) {
				//访问签到页面
				signinPageUrl = getSigninPageUrl(user, pwd);
				System.out.println(signinPageUrl);
				response=executor.execute(Request.Get(signinPageUrl).userAgent(CommonData.UA_ANDROID).connectTimeout(CommonData.TIME_OUT));
//				response.saveContent(new File(System.currentTimeMillis()+".log"));
				content = EntityUtils.toString(response.returnResponse().getEntity(),"utf-8");
				System.out.println(content);
				//获取用户性别
				sex = getUserSex(content);
				
				//提交签到请求
				signinSubmitUrl = getSigninSubmitUrl(content, user);
				System.out.println(signinSubmitUrl);
				
				response=executor.execute(Request.Get(signinSubmitUrl).userAgent(CommonData.UA_ANDROID).connectTimeout(CommonData.TIME_OUT));
				content = EntityUtils.toString(response.returnResponse().getEntity(),"utf-8");
				System.out.println(content);
				getSiginResult(content);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
