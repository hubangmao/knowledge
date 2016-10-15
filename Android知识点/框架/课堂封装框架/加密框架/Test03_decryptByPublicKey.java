package password;

import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;

/**
 * 乙方按甲方给的公钥解密
 * 输入Test02_encypt加密的密文和Test01_CreatePublicKey生成的公钥。
 * 解密的文本是Test02_encypt输入的原文。
 */
public class Test03_decryptByPublicKey {
	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		System.out.println("输入密文:");
		String strCode=scanner.next();
		System.out.println("输入公钥:");
		String strPublicKey=scanner.next();
		//将字符串类型的公钥转换为字节数组
		byte[] publicKey=Base64.decodeBase64(strPublicKey);
		//将密文转换为字节数组
		byte[] code=Base64.decodeBase64(strCode);
		//解密数据  
        byte[] decode1;
		try {
			decode1 = RSACoder.decryptByPublicKey(code, publicKey);
			System.out.println("乙方解密后的数据：\n"+new String(decode1)); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
