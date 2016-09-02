package org.utils;

import com.jngld.utils.EncryptUtil;
import com.jngld.utils.exception.UtilException;

import junit.framework.TestCase;

public class EncryptUtilTest extends TestCase {
    
    public void testEncrypt(){
        assertTrue(EncryptUtil.string2MD5("123qwe!@#").equals("78302615c8b79cac8df6d2607f8a83ee"));
        assertTrue(EncryptUtil.string2MD5("").equals("d41d8cd98f00b204e9800998ecf8427e"));
        assertTrue(EncryptUtil.string2SHA1("123qwe!@#").equals("8bb1e26cbd9503d776e5e578cb7ab436d677ce52"));
        assertTrue(EncryptUtil.string2SHA1("").equals("da39a3ee5e6b4b0d3255bfef95601890afd80709"));
        
        String key = "123qwe!@#";
        String string = "asdfghj";
        String password;
        try {
            password = EncryptUtil.aesStringEncode(string, key);
            assertTrue(string.equals(EncryptUtil.aesStringDecode(password, key)));
        } catch (Exception e) {
            assertTrue(e instanceof UtilException);
            e.printStackTrace();
        }
    }

    public void testException(){
        try {
            EncryptUtil.string2MD5(null);
        } catch (Exception e) {
            assertTrue(e instanceof RuntimeException);
        }
        try {
            EncryptUtil.string2SHA1(null);
        } catch (Exception e) {
            assertTrue(e instanceof RuntimeException);
        }
    }
}
