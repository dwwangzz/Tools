package com.jngld.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jngld.utils.entity.IpInfo;
import com.jngld.utils.entity.IpLookUp;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * IP辅助类
 * @author (作者) xus-a
 * @version (版本) V1.0
 * @date (开发日期) 2015年11月26日 上午10:01:21
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 * @since (该版本支持的JDK版本) 1.7
 */
public class IpUtil {
    /**
     * 调用百度验证IP接口的apikey,可以随时更换的，现在用的是个人的Key
     */
    private static final String BAIDU_APIKEY = "1d87fdaa69e17312cb8b56e3d07dbd86";

    /**
     * 百度IP地址查询接口地址
     */
    private static final String BAIDU_IP_LOOKUP_SERVICE_URL = "http://apis.baidu.com/apistore/iplookupservice/iplookup";

    /**
     * 得到国家
     * @param ip ip地址
     * @return 国家名称
     * @throws IOException
     * @author xus-a
     * @date 2015年11月27日 上午11:19:59
     */
    public static String getCountry(String ip) throws IOException {
        String s = ipLookUpService(ip).getCountry();
        return ipLookUpService(ip).getCountry();
    }

    /**
     * 得到省份
     * @param ip ip地址
     * @return 省名称
     * @throws IOException
     * @author xus-a
     * @date 2015年11月27日 上午11:20:32
     */
    public static String getProvince(String ip) throws IOException {
        return ipLookUpService(ip).getProvince();
    }

    /**
     * 得到城市
     * @param ip ip地址
     * @return 城市名称
     * @throws IOException
     * @author xus-a
     * @date 2015年11月27日 上午11:20:48
     */
    public static String getCity(String ip) throws IOException {
        return ipLookUpService(ip).getCity();
    }

    /**
     * 得到区县
     * @param ip ip地址
     * @return 区县名称
     * @throws IOException
     * @author xus-a
     * @date 2015年11月27日 上午11:20:52
     */
    public static String getDistrict(String ip) throws IOException {
        return ipLookUpService(ip).getDistrict();
    }

    /**
     * 得到运营商
     * @param ip ip地址
     * @return 运营商名
     * @throws IOException
     * @author xus-a
     * @date 2015年11月27日 上午11:20:56
     */
    public static String getCarrier(String ip) throws IOException {
        return ipLookUpService(ip).getCarrier();
    }

    /**
     * 获得ip地址的所有详细信息
     * @param ip ip地址
     * @return ipInfo ip信息实体
     * @throws IOException
     * @author xus-a
     * @date 2015年11月26日 下午2:24:45
     */
    public static IpInfo getIpAllInfo(String ip) throws IOException {
        IpInfo ipInfo = ipLookUpService(ip);
        return ipInfo;
    }

    /**
     * 获得用户的真实IP地址
     * @param request
     * @return 真实IP地址字符串(如果request为null则返回"unknown"字符串)
     * @author xus-a
     * @date 2015年11月27日 上午11:22:12
     */
    public static String getIpAddress(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ip) || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StringUtils.isBlank(ip) || ip.equalsIgnoreCase("unknown")) {
            ip = request.getRemoteAddr();
        }
        if (StringUtils.equals(ip, "0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    /**
     * 调用百度的api接口查询用户IP所在的省
     * @param ip 需要查询的ip地址所在的省份
     * @return 省份名(如果没有查到会返回一个空的字符串), ip详情实体(需判断类型再处理)
     * @throws IOException
     * @author xus-a
     * @date 2015年11月26日 上午10:01:42
     */
    private static IpInfo ipLookUpService(String ip) throws IOException {
        BufferedReader reader = null;
        String getJson = null;
        StringBuffer sbf = new StringBuffer();
        String httpUrl = BAIDU_IP_LOOKUP_SERVICE_URL + "?" + "ip="
                + ip;
        InputStream is = null;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", BAIDU_APIKEY);
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append(System.lineSeparator());
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (is != null) {
                is.close();
            }
        }
        getJson = sbf.toString().replaceAll("None", "");
        //解析gson
        Gson gson = new Gson();
        IpLookUp ipLookUp = gson.fromJson(getJson, new TypeToken<IpLookUp>() {
        }.getType());
        if (null != ipLookUp) {
            //判断正常返回的情况
            if (ipLookUp.getErrNum() == 0) {
                return ipLookUp.getRetData();
            } else {
                return new IpInfo();
            }
        } else {
            return new IpInfo();
        }
    }

    /**
     * 从Request对象中获得客户端IP，处理了HTTP代理服务器和Nginx的反向代理截取了ip
     * @param request
     * @return ip
     * @author wangzz
     */
    public static String getLocalIp(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");

        String ip = null;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded.split(",")[0];
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                if (forwarded != null) {
                    forwarded = forwarded.split(",")[0];
                }
                ip = realIp + "/" + forwarded;
            }
        }
        return ip;
    }

    /**
     * 获取客户端ip地址(推荐)
     * @param request
     * @return ip
     * @author wangzz
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * 获取客户端ip地址
     * @param request
     * @author wangzz
     * @return ip
     */
    public static String getIp2(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");

        String ip = null;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded;
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                ip = realIp + "/" + forwarded.replaceAll(", " + realIp, "");
            }
        }
        return ip;
    }


    public static void main(String[] args) throws IOException {
        System.out.println(getCountry("192.168.0.1"));
        System.out.println(getProvince("124.128.33.106"));
     /* System.out.println(getCity("124.128.33.106"));
      System.out.println(getDistrict("124.128.33.106"));
      System.out.println(getCarrier("124.128.33.106"));
      IpInfo ipInfo = getIpAllInfo("124.128.33.106");*/
    }
}

