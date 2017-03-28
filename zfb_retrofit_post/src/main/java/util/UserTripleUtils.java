package util;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * (描述)
 *
 * @author peng.zhang
 */
public class UserTripleUtils {

    /**
     * 加密
     * @param value
     * @param key
     * @return
     */
    public static String encrypt(String key,String value)
    {

        byte[] encrypt = TripleDESUtils.encrypt(key, value);

        return Base64.encodeBytes(encrypt);
    }

    /**
     * 解密
     * @param key
     * @param value
     * @return
     */
    public static String decrypt (String key ,String value) throws Exception
    {

        return TripleDESUtils.decrypt(key, Base64.decode(value.getBytes()));
    }

    public static void main(String[] args) throws Exception {
        String u = UserTripleUtils.encrypt("1dCAuVGybzrH17fKM", "15603051837");
       u = URLEncoder.encode(u, "utf-8");
        System.out.println(u);

       String descUrl = URLDecoder.decode("CuJ4OfM%2FSLTNin2utkJ8sw%3D%3D", "utf-8");
           descUrl = UserTripleUtils.decrypt("1dCAuVGybzrH17fKM", descUrl);
        System.out.println(descUrl);
    }

}
