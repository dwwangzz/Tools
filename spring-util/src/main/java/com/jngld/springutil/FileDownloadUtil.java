package com.jngld.springutil;

import com.jngld.exception.SpringUtilException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 
 * @Description 文件下载工具类
 * @author (作者) youps-a
 * @date (开发日期) 2015年11月30日 上午10:00:50
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) 1.7
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 */
public class FileDownloadUtil {
    
    private FileDownloadUtil(){
        
    }
    
    /**
     * 
     * @Description 文件下载方法
     * @author youps-a
     * @date 2015年11月30日 下午2:38:09
     * @param response
     * @param realPath      服务器文件存放地址，包含路径及文件名 例：c:\\upload\aa.xml
     * @return              true 成功，false 失败
     * @throws UnsupportedEncodingException
     */
    public static boolean Download(HttpServletResponse response, String realPath) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(realPath)) {
            throw new SpringUtilException("Download params must not be null");
        }
        return Download(response, realPath, null);
    }
    
    /**
     * 
     * @Description 文件下载方法，自定义下载文件名，包含文件名及后缀
     * @author youps-a
     * @date 2015年11月30日 下午2:40:59
     * @param response
     * @param realPath      服务器文件存放地址，包含路径及文件名 例：c:\\upload\aa.xml
     * @param customName    自定义文件名 例：模板.xml
     * @return              true 成功，false 失败
     * @throws UnsupportedEncodingException
     */
    public static boolean Download(HttpServletResponse response,String realPath,String customName) throws UnsupportedEncodingException{
        ServletOutputStream os = null;
        response.reset();
        
        File file = new File(realPath);
        if (StringUtils.isEmpty(customName)) {
            customName = file.getName();
        }
        //设置文件名
        response.setHeader("Content-Disposition", "attachment; fileName="+new String(customName.getBytes("GBK"), "ISO8859-1"));
        //设置文件大小
        response.addHeader("Content-Length", file.length()+"");
        //设置文件类型
        response.setContentType("application/octet-stream; charset=utf-8");
        
        if(file.exists()){
            try {
                os = response.getOutputStream();
                FileUtils.copyFile(file, os);
                os.flush();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != os) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        
        return false;
    }

}
