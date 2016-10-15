package password;

import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;
/**
 * 用私钥对原文进行加密
 * 获得甲方加密后的密文在Test03_decypt中进行解密.
 * @author yao
 */
public class Test02_encryptByPrivateKey {
	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		System.out.println("输入原文:");
		String str=scanner.nextLine();
		System.out.println("输入私钥:");
		String strPrivateKey=scanner.nextLine();
		byte[] privateKey = Base64.decodeBase64(strPrivateKey);
		try {
			byte[] privateEncrypt = RSACoder.encryptByPrivateKey(str.getBytes(), privateKey);
			System.out.println("加密后的密文:");
			System.out.println(Base64.encodeBase64String(privateEncrypt));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
