package password;

import java.util.Map;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;

/** Test01,Test02��Test03��RSACoder�ֽ�Ϊ�������裺
 *  Test01_CreatePublicKey�ࣺ���ɼ׷��Ĺ�Կ��˽Կ
 *  Test02_encypt�ࣺ�׷���˽Կ��ԭ�ļ���
 *  Test03_decypt�ࣺ��ü׷��Ĺ�Կ�ͼ��ܺ�����Ľ��н��ܡ�
 */
public class Test01_CreateKey {
	public static void main(String[] args) {
		Map<String, Object> keyMap;
		System.out.println("������ܳ���(���ڵ���512������512�ı���)");
		int keySize=new Scanner(System.in).nextInt();
		try {
			keyMap = RSACoder.initKey(keySize);
			byte[] privateKey = RSACoder.getPrivateKey(keyMap);
			String privateKeyStr = Base64.encodeBase64String(privateKey);
			System.out.println("˽Կ:");
			System.out.println(privateKeyStr);
			byte[] publicKey = RSACoder.getPublicKey(keyMap);
			String publicKeyStr = Base64.encodeBase64String(publicKey);
			System.out.println("��Կ:");
			System.out.println(publicKeyStr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
