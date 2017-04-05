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
 * @author (作者) youps-a
 * @version (版本) V1.0
 * @Description 文件上传下载工具类
 * @date (开发日期) 2015年11月30日 上午10:00:50
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 * @since (该版本支持的JDK版本) 1.7
 */
public class FileUploadUtil {

    private FileUploadUtil() {

    }

    /**
     * @param filedata MultipartFile 类型文件数据
     * @param realPath 文件目标文件夹路径，不包含文件名
     * @return FileUploadInfo 新上传文件信息
     * @throws IOException
     * @Description 文件上传方法，使用文件原始名保存
     * @author youps-a
     * @date 2015年11月30日 下午2:36:58
     */
    public static FileUploadInfo Upload(MultipartFile filedata, String realPath) throws IOException {
        if (null == filedata || filedata.isEmpty() || StringUtils.isEmpty(realPath)) {
            throw new SpringUtilException("Upload params must not be null");
        }

        String fileName = filedata.getOriginalFilename();

        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        return Upload(filedata, realPath, fileName);
    }

    /**
     * @param filedata MultipartFile 类型文件数据
     * @param realPath 文件目标文件夹路径，不包含文件名
     * @param saveName 自定义保存为的文件名，不包含后缀，后缀会自动添加
     * @return FileUploadInfo 新上传文件信息
     * @throws IOException
     * @Description 文件上传方法，自定义文件名
     * @author youps-a
     * @date 2015年11月30日 下午2:33:12
     */
    public static FileUploadInfo Upload(MultipartFile filedata, String realPath, String saveName) throws IOException {
        if (null == filedata || filedata.isEmpty() || StringUtils.isEmpty(realPath)) {
            throw new SpringUtilException("Upload params must not be null");
        }

        File file1 = new File(realPath);
        //判断文件夹创建
        if (!file1.exists()) {
            file1.mkdirs();
        }

        //文件原始文件名
        String fileName = filedata.getOriginalFilename();
        //文件类型
        String fileType = fileName.substring(fileName.lastIndexOf('.'), fileName.length());

        FileUploadInfo fileInfo = new FileUploadInfo();
        fileInfo.setFileOriginalname(fileName);
        fileInfo.setFileSize(filedata.getSize());
        fileInfo.setFileType(fileType);

        if (StringUtils.isEmpty(saveName)) {
            saveName = fileName;
        } else {
            saveName += fileType;
        }

        File file = new File(realPath, saveName);
        //上传文件
        FileUtils.copyInputStreamToFile(filedata.getInputStream(), file);

        fileInfo.setFilePath(file.getPath());
        fileInfo.setFileSaveName(file.getName());

        return fileInfo;
    }

    /**
     * @param request HttpServletRequest
     * @return MultipartFile 集合
     * @throws SpringUtilException HttpServletRequest 类型转换异常
     * @Description 通过request 获取 MultipartFile[]
     * @author youps-a
     * @date 2015年12月9日 上午8:56:37
     */
    public static List<MultipartFile> getMultipartFiles(HttpServletRequest request) throws SpringUtilException {
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
