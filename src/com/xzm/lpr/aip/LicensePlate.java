package com.xzm.lpr.aip;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.json.JSONObject;

public class LicensePlate {

    public static String licensePlate(String filePath) {
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/license_plate";
        try {
        	
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getAuth("g9g8EUtMj2H5jiTNdzSWcByj", "GwIZ8yB0rOHzXYosEVDuxfeXFrhTGUKG");

            String result = HttpUtil.post(url, accessToken, param);
            JSONObject jsonObj_temp = new JSONObject(result);
            JSONObject obj= (JSONObject) jsonObj_temp.get("words_result");
            String number = (String) obj.get("number");
            return number;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
     * 		字符串编码格式的转化
     */
    public static byte[] getUTF8BytesFromGBKString(String gbkStr) {  
        int n = gbkStr.length();  
        byte[] utfBytes = new byte[3 * n];  
        int k = 0;  
        for (int i = 0; i < n; i++) {  
            int m = gbkStr.charAt(i);  
            if (m < 128 && m >= 0) {  
                utfBytes[k++] = (byte) m;  
                continue;  
            }  
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));  
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));  
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));  
        }  
        if (k < utfBytes.length) {  
            byte[] tmp = new byte[k];  
            System.arraycopy(utfBytes, 0, tmp, 0, k);  
            return tmp;  
        }  
        return utfBytes;  
    }


    public static String getlicensePlate(String filePath) {
    	//百度api    
    	//return licensePlate(filePath);
    	
    	//自己程序解决部分
		String exe = "python";
		String command = "E:\\Python\\lpr\\lpr.py";
		String[] cmdArr = new String[] {exe,command,filePath};
		Process process;
		try {
			process = Runtime.getRuntime().exec(cmdArr);
			process.waitFor();
			if(process.exitValue()==0) {
				BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
	            String line = null;
	            line = in.readLine();
	            in.close();	
	            return new String(getUTF8BytesFromGBKString(line), "UTF-8");
			}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "ERROR";
    }
/*   
  用于系统端口测试 
 *         
    public static void main(String[] args) {
        System.out.println(getlicensePlate("E:\\Python\\LP\\chepai\\1.jpg"));
    }
 */
     
}