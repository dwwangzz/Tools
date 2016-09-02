package com.jngld.springutil;

import com.jngld.entity.FileUploadInfo;
import com.jngld.exception.SpringUtilException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 
 * @Description 文件上传下载工具类
 * @author (作者) youps-a
 * @date (开发日期) 2015年11月30日 上午10:00:50
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) 1.7
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 */
public class FileUploadUtil {
    
    private FileUploadUtil(){
        
    }

    /**
     * 
     * @Description 文件上传方法，使用文件原始名保存
     * @author youps-a
     * @date 2015年11月30日 下午2:36:58
     * @param Filedata      MultipartFile 类型文件数据
     * @param realPath      文件目标文件夹路径，不包含文件名
     * @return              FileUploadInfo 新上传文件信息
     * @throws IOException
     */
    public static FileUploadInfo Upload(MultipartFile Filedata,String realPath) throws IOException{
        if (null == Filedata || Filedata.isEmpty() || StringUtils.isEmpty(realPath)) {
            throw new SpringUtilException("Upload params must not be null");
        }
        
        String fileName = Filedata.getOriginalFilename();
        
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        return Upload(Filedata, realPath, fileName);
    }
    
    /**
     * 
     * @Description 文件上传方法，自定义文件名
     * @author youps-a
     * @date 2015年11月30日 下午2:33:12
     * @param Filedata      MultipartFile 类型文件数据
     * @param realPath      文件目标文件夹路径，不包含文件名
     * @param saveName      自定义保存为的文件名，不包含后缀，后缀会自动添加
     * @return              FileUploadInfo 新上传文件信息
     * @throws IOException
     */
    public static FileUploadInfo Upload(MultipartFile Filedata,String realPath,String saveName) throws IOException{
        if (null == Filedata || Filedata.isEmpty() || StringUtils.isEmpty(realPath)) {
            throw new SpringUtilException("Upload params must not be null");
        }
        
        File file1 = new File(realPath);
        //判断文件夹创建
        if (!file1.exists()) {
            file1.mkdirs();
        }
        
        //文件原始文件名
        String fileName = Filedata.getOriginalFilename();
        //文件类型
        String fileType = fileName.substring(fileName.lastIndexOf('.'), fileName.length());
        
        FileUploadInfo fileInfo = new FileUploadInfo();
        fileInfo.setFileOriginalname(fileName);
        fileInfo.setFileSize(Filedata.getSize());
        fileInfo.setFileType(fileType);
        
        if (StringUtils.isEmpty(saveName)) {
            saveName = fileName;
        }else {
            saveName += fileType;
        }
        
        File file = new File(realPath,saveName);
        //上传文件
        FileUtils.copyInputStreamToFile(Filedata.getInputStream(), file);
        
        fileInfo.setFilePath(file.getPath());
        fileInfo.setFileSaveName(file.getName());
        
        return fileInfo;
    }
    
    /**
     * 
     * @Description 通过request 获取 MultipartFile[]
     * @author youps-a
     * @date 2015年12月9日 上午8:56:37
     * @param request   HttpServletRequest
     * @return          MultipartFile 集合
     * @throws SpringUtilException  HttpServletRequest 类型转换异常
     */
    public static List<MultipartFile> getMultipartFiles(HttpServletRequest request) throws SpringUtilException{
        List<MultipartFile> files = new ArrayList<MultipartFile>();
        MultipartHttpServletRequest mhs = null;
        try {
            mhs = (MultipartHttpServletRequest) request;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SpringUtilException("HttpServletRequest 转换为MultipartHttpServletRequest 异常，请检查请求类型，是否包含文件", e);
        }
        if (null != mhs) {
            Map<String, MultipartFile> fileMap = mhs.getFileMap();
            for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
                MultipartFile mfFile = fileMap.get(entry.getKey());
                files.add(mfFile);
            }
        }
        return files;
    }
}
