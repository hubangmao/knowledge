package password;

import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;
/**
 * ��˽Կ��ԭ�Ľ��м���
 * ��ü׷����ܺ��������Test03_decypt�н��н���.
 * @author yao
 */
public class Test02_encryptByPrivateKey {
	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		System.out.println("����ԭ��:");
		String str=scanner.nextLine();
		System.out.println("����˽Կ:");
		String strPrivateKey=scanner.nextLine();
		byte[] privateKey = Base64.decodeBase64(strPrivateKey);
		try {
			byte[] privateEncrypt = RSACoder.encryptByPrivateKey(str.getBytes(), privateKey);
			System.out.println("���ܺ������:");
			System.out.println(Base64.encodeBase64String(privateEncrypt));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
