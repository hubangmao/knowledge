package password;

import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;

/**
 * �ҷ����׷����Ĺ�Կ����
 * ����Test02_encypt���ܵ����ĺ�Test01_CreatePublicKey���ɵĹ�Կ��
 * ���ܵ��ı���Test02_encypt�����ԭ�ġ�
 */
public class Test03_decryptByPublicKey {
	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		System.out.println("��������:");
		String strCode=scanner.next();
		System.out.println("���빫Կ:");
		String strPublicKey=scanner.next();
		//���ַ������͵Ĺ�Կת��Ϊ�ֽ�����
		byte[] publicKey=Base64.decodeBase64(strPublicKey);
		//������ת��Ϊ�ֽ�����
		byte[] code=Base64.decodeBase64(strCode);
		//��������  
        byte[] decode1;
		try {
			decode1 = RSACoder.decryptByPublicKey(code, publicKey);
			System.out.println("�ҷ����ܺ�����ݣ�\n"+new String(decode1)); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
