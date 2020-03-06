package com.xzm.lpr.aip;

import java.net.URLEncoder;

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

    public static String getlicensePlate(String filePath) {
    	//return licensePlate(filePath);
    	return "苏A0CP56";
    }
    
    //public static void main(String[] args) {
    //    System.out.println(licensePlate("E:\\ADesktop\\car.jpg"));
    //}
    
}