package com.example.frank.wuhanjikong.service;

import android.content.Context;


import com.example.frank.wuhanjikong.config.Path;
import com.example.frank.wuhanjikong.tools.StreamTool;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LoginService {
	/**
	 * 保存用户名和密码的业务方法
	 * @param context
	 * @param username 用户名
	 * @param password 密码
	 * @return true 保存成功 false 保存失败
	 */
	public static boolean saveInfo(Context context, String username, String password)//因为没有使用定义的变量就定义成static的方法这样的执行效率高
	{
		
		try {
			//File file=new File("/data/data/com.gongxin.login/info.dat");
			//context.getFilesDir();//帮助我们获取上下文文件目录/data/data/包名/files
			File file=new File(context.getFilesDir(), "info.dat");
			FileOutputStream fos=new FileOutputStream(file);
			//context.openFileOutput(name, mode);
			fos.write((username+"##"+password).getBytes());
			fos.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public static boolean saveToken(Context context, String token){
		try {
			//File file=new File("/data/data/com.gongxin.login/info.dat");
			//context.getFilesDir();//帮助我们获取上下文文件目录/data/data/包名/files
			System.out.println("saveToken进来了");
			File file=new File(context.getFilesDir(), "token.dat");
			FileOutputStream fos=new FileOutputStream(file);
			//context.openFileOutput(name, mode);
			fos.write(token.getBytes());
			fos.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 获取用户名 密码
	 * @param context
	 * @return
	 */
	public static Map<String, String> getSaveIfo(Context context)
	{
		Map<String, String> map;
		try {
			File file=new File(context.getFilesDir(), "info.dat");
			FileInputStream fis=new FileInputStream(file);
			BufferedReader br=new BufferedReader(new InputStreamReader(fis));
			String str=br.readLine();
			String[] infos=str.split("##");
			map = new LinkedHashMap<String,String>();
			map.put("用户名", infos[0]);
			map.put("密码", infos[1]);
			fis.close();
			return map;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	public static Map<String,String> getSaveToken(Context context){
		Map<String, String> map;
		try {
			File file=new File(context.getFilesDir(), "token.dat");
			FileInputStream fis=new FileInputStream(file);
			BufferedReader br=new BufferedReader(new InputStreamReader(fis));
			String str=br.readLine();
			map = new LinkedHashMap<String,String>();
			map.put("token", str);
			fis.close();
			return map;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


	public static String LoginByHttpClientPost(String username, String password, String token){
		try {
			//1打开一个浏览器
			BasicHttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
			HttpConnectionParams.setSoTimeout(httpParams, 10000);
			HttpClient client=new DefaultHttpClient();
			//2输入地址
			String path= Path.URLPATH;
			HttpPost httpPost=new HttpPost(path);
			httpPost.setHeader("token",token);
			//3指定要提交的数据实体
			List<NameValuePair> parameters=new ArrayList<NameValuePair>();
			parameters.add(new BasicNameValuePair("loginName", username));
			parameters.add(new BasicNameValuePair("password",password));
			httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
			
			HttpResponse httpRes=client.execute(httpPost);
			int code=httpRes.getStatusLine().getStatusCode();
			if(code==200){
				InputStream is=httpRes.getEntity().getContent();
				String text= StreamTool.streamtoText(is, "utf-8");
				return text;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "登录失败";
		}
		return "登录失败";
		
	}












	/*public static String loginByGet(String username,String password)
	{
		try {
			String path="http://192.168.191.1:8080/day7/servlet/LoginController?username="+username+"&password="+password;
			URL url=new URL(path);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			String charset="utf-8";
			int code=conn.getResponseCode();
			if(code==200){
				//请求成功
				String data= StreamTool.streamtoText(conn.getInputStream(), charset);
				if(data==null)
					return "输入流为空";
				else
					return data;
			}else{
				return "网络请求异常1";
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "网络请求异常2";
		}

	}

	public static String  LoginByPost(String username,String password)
	{

	try {//使用笔记本建立的局域网时需要关闭防火墙
		String path="http://192.168.191.1:8080/day7/servlet/LoginController";
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		StringBuffer sb=new StringBuffer();
	    sb.append("username=").append(URLEncoder.encode(username, "utf-8")).append("&password=").append(URLEncoder.encode(password, "utf-8"));
		String data=sb.toString();
		System.out.println(data);
		conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", data.length()+"");

		//post 是浏览器把数据写给服务器 要允许写数据
		conn.setDoOutput(true);
		conn.setDoInput(true);
		OutputStream os;
		os = conn.getOutputStream();
		os.write(data.getBytes("utf-8"));
		String charset="utf-8";
		int code = conn.getResponseCode();
		if(code==200){
			String data2 = StreamTool.streamtoText(conn.getInputStream(), charset);
			if(data2==null)
				return "输入流为空";
			else
				return data2;
		}else{
			System.out.println("code:"+code);
			return "网络请求异常1";
		}
	} catch (Exception e) {
		return "网络请求异常2";
	}

	}
	*/




}
 