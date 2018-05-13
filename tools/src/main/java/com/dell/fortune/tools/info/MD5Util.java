package com.dell.fortune.tools.info;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 81256 on 2018/4/13.
 */

public class MD5Util {
    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    protected static MessageDigest sMessageDigest = null;

    static {
        try {
            sMessageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    //本地文件
    public static String getFileMD5String(File file) throws IOException, NoSuchAlgorithmException {
        InputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int numRead = 0;
        while ((numRead = fis.read(buffer)) > 0) {
            sMessageDigest.update(buffer, 0, numRead);
        }
        fis.close();
        return bufferToHex(sMessageDigest.digest());
    }

    //字符串MD5
    public static String getStringMD5(String str) {
        byte[] buffer = str.getBytes();
        sMessageDigest.update(buffer);
        return bufferToHex(sMessageDigest.digest());
    }

    //网络图片
    public static String getMD5(String URLName) {
        String name = "";
        try {
            URL url = new URL(URLName);
            InputStream inputStream = new BufferedInputStream(url.openStream());
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(bytes)) > 0) {
                sMessageDigest.update(bytes, 0, len);
            }
            name = MD5Util.bufferToHex(sMessageDigest.digest());
            inputStream.close();
        } catch (MalformedURLException e) {
//            LogUtil.getLogger().warn(e);
        } catch (IOException e) {
//            LogUtil.getLogger().warn(e);
        }
        return name;
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换
        // 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
        char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }


}
