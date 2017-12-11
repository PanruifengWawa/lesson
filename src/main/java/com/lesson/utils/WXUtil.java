package com.lesson.utils;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

public class WXUtil {
	private static String accessToken = null;
	private static Long lastTokenTime = null;
	private static String jsapiTicket = null;
	
	private static String appId = "wx57a5397f38abd12a";
	private static String appSecret = "7e346619e15fd4855e2b86920e4951b8";
	
	private static boolean ifRefresh() {
		boolean flag = false;
		if (accessToken == null || jsapiTicket ==null  || lastTokenTime == null) {
			System.out.println("no last token");
			flag = true;
		} else {
			long time_expire = (System.currentTimeMillis() - lastTokenTime)/1000;
			System.out.println("time expire: " + time_expire);
			if (time_expire >= 3600) {
				System.out.println("token time expired");
				flag = true;
			} else {
				System.out.println("token time not expired");
				flag = false;
			}
		}
		
		return flag;
	}
	
	private static String getAccessToken() {
		String result = null;
		String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret;
		String response = HttpRequestUtil.sendGet(accessTokenUrl);
		if(response == null) {
			result = "获取access token失败";
		} else {
			System.out.println("get access token: " + response);
			try {
				JSONObject json = new JSONObject(response);
				String accessTokenResponse = null;
				String errMsgResponse = null;
				if (json.has("access_token")) {
					accessTokenResponse = json.getString("access_token");
				}
				if (json.has("errmsg")) {
					errMsgResponse = json.getString("errmsg");
				}

				if(accessTokenResponse != null) {
					accessToken = accessTokenResponse;
				} else {
					result = errMsgResponse;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				result = "access token返回格式错误";
			}
		}
		
		return result;
		
	}
	
	private static String getJSTicket() {
		String result = null;
		String jsTicketUrl = "http://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=" + accessToken;
		String response = HttpRequestUtil.sendGet(jsTicketUrl);
		if(response == null) {
			result = "获取js ticket失败";
		} else {
			System.out.println("get js ticket: " + response);
			try {
				JSONObject json = new JSONObject(response);
				String ticketResponse = null;
				String errMsgResponse = null;
				if (json.has("ticket")) {
					ticketResponse = json.getString("ticket");
				}
				if (json.has("errmsg")) {
					errMsgResponse = json.getString("errmsg");
				}
				if(ticketResponse != null) {
					jsapiTicket = ticketResponse;
				} else {
					result = errMsgResponse;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				result = "js ticket返回格式错误";
			}
		}
		
		return result;
	}
	
	public static Map<String, String> getJSConfig(String url) {
		Map<String, String> ret = null;
		String result1 = null;
		String result2 = null;
		if (ifRefresh()) {
			result1 = getAccessToken();
			result2 = getJSTicket();
			lastTokenTime = System.currentTimeMillis();
		}
		if(result1 != null || result2 != null) {
			ret = new HashMap<String, String>();
			if (result1 != null) {
				ret.put("errorCode", result1);
			} else {
				ret.put("errorCode", result2);
			}
		} else {
			ret = sign(url);
			ret.put("errorCode", "ok");
			
			System.out.println("get js config: " + ret);
			ret.remove("jsapi_ticket");
		}
		return ret;
	}
	

    private static Map<String, String> sign(String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapiTicket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapiTicket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
	
	
	

}
