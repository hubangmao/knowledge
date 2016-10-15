package password;

import java.util.Map;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;

/** Test01,Test02和Test03将RSACoder分解为三个步骤：
 *  Test01_CreatePublicKey类：生成甲方的公钥和私钥
 *  Test02_encypt类：甲方用私钥对原文加密
 *  Test03_decypt类：获得甲方的公钥和加密后的密文进行解密。
 */
public class Test01_CreateKey {
	public static void main(String[] args) {
		Map<String, Object> keyMap;
		System.out.println("输入加密长度(大于等于512，且是512的倍数)");
		int keySize=new Scanner(System.in).nextInt();
		try {
			keyMap = RSACoder.initKey(keySize);
			byte[] privateKey = RSACoder.getPrivateKey(keyMap);
			String privateKeyStr = Base64.encodeBase64String(privateKey);
			System.out.println("私钥:");
			System.out.println(privateKeyStr);
			byte[] publicKey = RSACoder.getPublicKey(keyMap);
			String publicKeyStr = Base64.encodeBase64String(publicKey);
			System.out.println("公钥:");
			System.out.println(publicKeyStr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
