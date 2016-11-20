package cn.hxxd.utils;

import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class RSAServerUtils {
	/**
	 * 私钥解密
	 * 
	 * @param strCode解密文件
	 * @param strPrivateKey
	 *            私钥
	 * @return 成功 返回解密字符串 失败返回null
	 */
	public static String getDecryptStr(String strCode, String strPrivateKey) {
		String KEY_ALGORITHM = "RSA";
		// 将字符串类型的公钥转换为字节数组
		byte[] publicKey = Base64.decodeBase64(strPrivateKey);
		// 将密文转换为字节数组
		byte[] code = Base64.decodeBase64(strCode);
		try {
			// 取得私钥
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(publicKey);
			// 实例化密钥工厂
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			// 生成私钥
			PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
			// 数据解密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return new String(cipher.doFinal(code));
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
