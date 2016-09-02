package com.jngld.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.jngld.utils.exception.ParameterException;
import com.jngld.utils.exception.UtilException;


/**
 * 
 * @Description 加密工具类
 * @author (作者) youps-a
 * @date (开发日期) 2015年11月25日 下午1:56:35
 * @company (开发公司) 广联达软件股份有限公司
 * @copyright (版权) 本文件归广联达软件股份有限公司所有
 * @version (版本) V1.0
 * @since (该版本支持的JDK版本) 1.7
 * @modify (修改) 第N次修改：时间、修改人;修改说明
 * @Review (审核人) 审核人名称
 */
public class EncryptUtil {
    
    private static final String UTF_8 = "UTF-8";
    
    private static KeyGenerator kgen;
    
    /**私有化构造函数*/
    private EncryptUtil(){
        
    }
    
    static{
        try {
            kgen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @Description 字符串 MD5 加密，引用org.apache.commons.codec
     * @author youps-a
     * @date 2015年11月25日 下午1:56:12
     * @param inStr  需要加密的输入参数
     * @return       加密后的字符串
     */
    public static String string2MD5(String inStr){
        if (null == inStr) {
            throw new ParameterException("参数不能为null");
        }
        return DigestUtils.md5Hex(inStr);  
    }
    
    /**
     * 
     * @Description 字符串SHA-1加密，引用org.apache.commons.codec
     * @author youps-a
     * @date 2015年11月25日 下午1:59:29
     * @param inStr 需要加密的输入参数
     * @return      加密后的字符串
     */
    public static String string2SHA1(String inStr){
        if (null == inStr) {
            throw new ParameterException("参数不能为null");
        }
        return DigestUtils.sha1Hex(inStr);
    }

    /**
     * 
     * @Description 字符串 aes 加密
     * @author youps-a
     * @date 2015年11月25日 下午4:57:26
     * @param str  需加密字符串
     * @param key  加密密钥
     * @return     aes加密后的字符串
     * @throws UtilException 
     */
    public static String aesStringEncode(String str,String key) throws UtilException {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("The parameters must not be empty");
        }
		try {
            kgen.init(128, new SecureRandom(key.getBytes(UTF_8)));
            SecretKey skey = kgen.generateKey();
            byte[] raw = skey.getEncoded();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] bytes = cipher.doFinal(str.getBytes(UTF_8));
            return ConvertUtil.byte2HexString(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new UtilException("字符串 aes 加密 异常", e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new UtilException("字符串 aes 加密 异常", e);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw new UtilException("字符串 aes 加密 异常", e);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new UtilException("字符串 aes 加密 异常", e);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new UtilException("字符串 aes 加密 异常", e);
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new UtilException("字符串 aes 加密 异常", e);
        }
		
    }

    /**
     * 
     * @Description 字符串aes 解密
     * @author youps-a
     * @date 2015年11月25日 下午4:58:03
     * @param str  要解密的字符串
     * @param key  解密密钥
     * @return     aes解密后的字符串
     * @throws UtilException 
     */
    public static String aesStringDecode(String str,String key) throws UtilException {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("The parameters must not be empty");
        }
        
        try {
            kgen.init(128, new SecureRandom(key.getBytes(UTF_8)));
            SecretKey skey = kgen.generateKey();
            byte[] raw = skey.getEncoded();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] bytes = cipher.doFinal(ConvertUtil.hexString2Byte(str));
            return new String(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new UtilException("字符串 aes 解密 异常", e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new UtilException("字符串 aes 解密 异常", e);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw new UtilException("字符串 aes 解密 异常", e);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new UtilException("字符串 aes 解密 异常", e);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new UtilException("字符串 aes 解密 异常", e);
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new UtilException("字符串 aes 解密 异常", e);
        }
        
    }

    /**
     * 
     * @Description 文件aes 加密
     * @author youps-a
     * @date 2015年11月25日 下午4:59:06
     * @param srcFilePath  要加密的源文件
     * @param destFilePath 加密到的文件
     * @param key          加密密钥
     * @throws UtilException 
     */
    public static void aesFileEncode(String srcFilePath, String destFilePath, String key) throws UtilException {
        if (StringUtils.isEmpty(srcFilePath) || StringUtils.isEmpty(destFilePath) || StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("The parameters must not be empty");
        }
        
        InputStream in = null;
        OutputStream out = null;

        try {
            kgen.init(128, new SecureRandom(key.getBytes(UTF_8)));
            SecretKey skey = kgen.generateKey();
            byte[] raw = skey.getEncoded();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            
            in = new FileInputStream(srcFilePath);
            out = new FileOutputStream(FileUtil.getNewValidateFile(destFilePath));
            crypto(in, out, cipher);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new UtilException("文件aes 加密 异常",e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new UtilException("文件aes 加密 异常",e);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw new UtilException("文件aes 加密 异常",e);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new UtilException("文件aes 加密 异常",e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new UtilException("文件aes 加密 异常,文件未找到",e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UtilException("文件aes 加密  IO异常",e);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new UtilException("文件aes 加密 异常",e);
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }
    }

    /**
     * 
     * @Description 文件aes 解密
     * @author youps-a
     * @date 2015年11月25日 下午4:59:59
     * @param srcFilePath  需解密的文件
     * @param destFilePath 解密到的文件
     * @param key          解密密钥
     * @throws UtilException 
     */
    public static void aesFileDecode(String srcFilePath, String destFilePath, String key) throws UtilException {
        if (StringUtils.isEmpty(srcFilePath) || StringUtils.isEmpty(destFilePath) || StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("The parameters must not be empty");
        }
        
        InputStream in = null;
        OutputStream out = null;
        try {
            kgen.init(128, new SecureRandom(key.getBytes(UTF_8)));
            SecretKey skey = kgen.generateKey();
            byte[] raw = skey.getEncoded();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            
            in = new FileInputStream(srcFilePath);
            out = new FileOutputStream(FileUtil.getNewValidateFile(destFilePath));
            crypto(in, out, cipher);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new UtilException("文件aes 解密 异常",e);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw new UtilException("文件aes 解密 异常",e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new UtilException("文件aes 解密 异常",e);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new UtilException("文件aes 解密 异常",e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new UtilException("文件aes 解密 异常,文件未找到",e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new UtilException("文件aes 解密  IO异常",e);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new UtilException("文件aes 解密 异常",e);
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }
        
    }

    /**
     * 
     * @Description 文件加解密复制
     * @author youps-a
     * @date 2015年12月8日 上午10:44:46
     * @param in        输入流
     * @param out       输出流
     * @param cipher    加解密算法类
     * @throws IOException
     * @throws GeneralSecurityException
     */
    private static void crypto(InputStream in, OutputStream out, Cipher cipher) throws IOException, GeneralSecurityException {
        int blockSize = cipher.getBlockSize() * 1024;
        int outputSize = cipher.getOutputSize(blockSize);

        byte[] inBytes = new byte[blockSize];
        byte[] outBytes = new byte[outputSize];

        int inLength = -1;
        boolean more = true;
        while (more) {
            inLength = in.read(inBytes);
            if (inLength == blockSize) {
                int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
                out.write(outBytes, 0, outLength);
            } else {
                more = false;
            }
        }
        if (inLength > 0) {
            outBytes = cipher.doFinal(inBytes, 0, inLength);
        } else {
            outBytes = cipher.doFinal();
        }
        out.write(outBytes);
    }


    public static void main(String[] args) throws UtilException {
        long time1 = System.currentTimeMillis();
//        String str = "";
//        System.out.println(string2SHA1(str));
//        System.out.println(string2MD5(str));
//        
//        System.out.println(aesStringEncode("你好","123"));
//        System.out.println(aesStringDecode("DD2355CC5E2424D4D7F378E5E0831892","123"));
//        aesFileEncode("E:/练习.zip", "E:/aaaa/练习加密.zip", "123456");
        EncryptUtil.aesFileDecode("E:/aaaa/练习加密.zip", "E:/aaaa/练习解密.zip", "123456");
//        System.out.println(aesStringEncode("fd5a","123"));
//        try {
////			System.out.println(aesStringEncode("d4s5a64ds4a5d64a56d46sa45da4d6a45f6af4w6","f4d5s64f6s5"));
////			System.out.println(aesStringDecode("A080380616E514521DAC69303D78E681B58497941736ADAD213E799D91687CAD1B814AD6E12E2D76EA8F17CFC7A66A07","ds4a56d456sa"));
//		} catch (UtilException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        long time2 = System.currentTimeMillis();
        System.out.println(time2-time1);
        
    }
   
    
}
