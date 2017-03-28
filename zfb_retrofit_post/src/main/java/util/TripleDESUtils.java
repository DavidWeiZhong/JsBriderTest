package util;


import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * 瀵瑰瓧绗︿覆杩涜 3DES 鍔犲瘑/瑙ｅ瘑
 *
 * @author peng.zhang
 */
public class TripleDESUtils {

    private static final int KEY_SIZE = 24;  // 3DES 鍔犲瘑鐨� key 闀垮害蹇呴』鏄� 24 瀛楄妭銆�

    /**
     * 瀵瑰瓧绗︿覆鍔犲瘑
     *
     * @param key  key
     * @param text 瑕佸姞瀵嗙殑瀛楃涓�
     *
     * @return 鍔犲瘑鍚庣殑鍐呭
     */
    public static byte[] encrypt(String key, String text) {
        byte[] encrypted = null;

        try {
            Cipher encrypter = createCipher(key, Cipher.ENCRYPT_MODE);
            encrypted = encrypter.doFinal(text.getBytes("UTF-8"));
        } catch (Exception e) {
          e.printStackTrace();
        	//  throw new AppClientException(e);
        }
        return encrypted;
    }

    /**
     * 瑙ｅ瘑瀛楃涓�
     *
     * @param key       key
     * @param encrypted 琚姞瀵嗙殑鍐呭
     *
     * @return 瑙ｅ瘑鐨勫唴瀹�
     */
    public static String decrypt(String key, byte[] encrypted) {
        String decrypted = null;

        try {
            Cipher encrypter = createCipher(key, Cipher.DECRYPT_MODE);
            decrypted = new String(encrypter.doFinal(encrypted),"UTF-8");
        } catch (Exception e) {
          e.printStackTrace();
        	//  throw new AppClientException(e);
        }
        return decrypted;
    }

    private static Cipher createCipher(String key, int mode)
            throws InvalidKeyException, InvalidKeySpecException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {

        if (key.length() > KEY_SIZE) {
            throw new IllegalArgumentException("Key size must be under 24 characters.");
        } else {
            key = expandSize(key);
        }

        String algorithm = "DESede";
        String transformation = "DESede/CBC/PKCS5Padding";
        IvParameterSpec iv = new IvParameterSpec(new byte[8]);

        byte[] keyValue = key.getBytes();
        DESedeKeySpec keySpec = new DESedeKeySpec(keyValue);

        SecretKey secretKey = SecretKeyFactory.getInstance(algorithm).generateSecret(keySpec);
        Cipher encrypter = Cipher.getInstance(transformation);
        encrypter.init(mode, secretKey, iv);
        return encrypter;
    }

    private static String expandSize(String key) {
        StringBuilder stringBuilder = new StringBuilder(key);
        while (stringBuilder.length() < KEY_SIZE) {
            stringBuilder.append(" ");
        }
        key = stringBuilder.toString();
        return key;
    }

}
