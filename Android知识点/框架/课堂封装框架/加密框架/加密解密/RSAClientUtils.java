package cn.hxxd.utils;

import org.apache.commons.codec.binary.Base64;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.crypto.Cipher;

//RSA客户端加密
public class RSAClientUtils {

	/**
	 * 公钥加密
	 * 
	 * @param data需要加密的字符串
	 * @param strPublickKey
	 *            公钥
	 * @return 返回加密字符 加密异常返回null
	 */
	public static String getEncryptStr(String data, String strPublickKey) {
		String KEY_ALGORITHM = "RSA";
		byte[] privateKey = Base64.decodeBase64(strPublickKey);
		data += new Random().nextInt(10);
		try {
			// 实例化密钥工厂
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			// 密钥材料转换
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(privateKey);
			// 产生公钥
			PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
			// 数据加密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			return Base64.encodeBase64String(cipher.doFinal(data.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @about Md5加密
	 * @param buffer
	 * @return
	 */
	public final static String getMD5Str(byte[] buffer) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
}
