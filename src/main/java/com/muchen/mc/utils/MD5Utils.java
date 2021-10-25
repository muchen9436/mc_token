package com.muchen.mc.utils;

import java.security.MessageDigest;

/**
 * @author xiaoyu
 * @description md5工具类
 * @date 2021/7/26
 * @since 1.0.0
 **/
public class MD5Utils {

    private static final String[] HEX_DIGITS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    private static String byteArrayToHexString(byte[] b)
    {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
        {
            resultSb.append(byteToHexString(b[i]));
        }

        return resultSb.toString();
    }

    private static String byteToHexString(byte b)
    {
        int n = b;
        if (n < 0)
        {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    public static String md5Encode(String origin)
    {
        String resultString = null;
        try
        {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");

            resultString = byteArrayToHexString(md.digest(resultString.getBytes()));

        }
        catch (Exception exception)
        {
        }
        return resultString;
    }

    public static String md5Encode(byte[] origin)
    {
        String resultString = null;
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(origin));
        }
        catch (Exception exception)
        {
        }
        return resultString;
    }

    public static String create16Md5Id(String key){
    	StringBuffer buf = null ;
    	try {
              MessageDigest md = MessageDigest.getInstance("MD5");
              md.update(key.getBytes());
              byte[] b = md.digest();
              int i;
              buf = new StringBuffer("");
              for (int offset = 0; offset < b.length; offset++) {
                      i = b[offset];
                      if (i < 0) {
                          i += 256;
                      }
                      if (i < 16) {
                          buf.append("0");
                      }
                      buf.append(Integer.toHexString(i));
              }
            // 16位的加密
            return  buf.toString().substring(8, 24);
	      } catch (Exception e)  {
	         e.printStackTrace();
            return "";
	      }
    }

    public static String md5Encode(String origin, String chartset)
    {
        String resultString = null;
        try
        {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");

            resultString = byteArrayToHexString(md.digest(resultString.getBytes(chartset)));
        }
        catch (Exception exception)
        {
        }
        return resultString;
    }

    public MD5Utils()
    {
    }
    
    public static void main(String[] args) {
		System.out.println(md5Encode("123"));
	}

}