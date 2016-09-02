package com.jngld.entity;

/**
 * 
 * @Description 文件信息实体
 * @author (作者) youps-a
 * @date (开发日期) 2015年11月30日 上午10:00:36
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) 1.7
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 */
public class FileUploadInfo {

    // 文件保存名
    private String fileSaveName;
    // 文件原始名
    private String fileOriginalname;
    // 文件类型
    private String fileType;
    // 文件大小
    private long fileSize;
    // 文件路径
    private String filePath;

    public String getFileSaveName() {
        return fileSaveName;
    }

    public void setFileSaveName(String fileSaveName) {
        this.fileSaveName = fileSaveName;
    }

    public String getFileOriginalname() {
        return fileOriginalname;
    }

    public void setFileOriginalname(String fileOriginalname) {
        this.fileOriginalname = fileOriginalname;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "FileUploadInfo [fileSaveName=" + fileSaveName
                + ", fileOriginalname=" + fileOriginalname + ", fileType="
                + fileType + ", fileSize=" + fileSize + ", filePath="
                + filePath + "]";
    }

}
