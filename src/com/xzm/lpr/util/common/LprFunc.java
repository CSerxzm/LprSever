package com.xzm.lpr.util.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LprFunc {
    public static void main(String[] args) {
        System.out.println(CalTime("2019-12-15 16:05:55", "2019-12-15 18:10:55"));
    }
    // 计算两个时间差返回小时。
    public static int CalTime(String date_in, String date_out) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        double hours = 0L;
        try {
            Date in = df.parse(date_in);
            Date out = df.parse(date_out);
            double diff = out.getTime() - in.getTime();// 这样得到的差值是微秒级别
            hours = diff / (1000 * 60 * 60);
        } catch (ParseException e) {
            System.out.println("抱歉，时间日期解析出错。");
        }
        return (int)Math.ceil(hours);
    }
}
