package pw;

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

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class RSACoder {
	//�ǶԳ���Կ�㷨  
    public static final String KEY_ALGORITHM="RSA";  
    /** 
     * ��Կ���ȣ�DH�㷨��Ĭ����Կ������1024 
     * ��Կ���ȱ�����64�ı�������512��65536λ֮��
     * �޸�KEY_SIZE��ֵ���Բ�����ͬ�Ĺ�Կ-��Կ�� 
     * */  
    private static final int KEY_SIZE=512;  
    //��Կ  
    private static final String PUBLIC_KEY="RSAPublicKey";  
    //˽Կ  
    private static final String PRIVATE_KEY="RSAPrivateKey";  
    /** 
     * ��ʼ����Կ�� 
     * @return Map �׷���Կ��Map 
     * */  
    public static Map<String,Object> initKey(int keySize) throws Exception{  
        //ʵ������Կ������  
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance(KEY_ALGORITHM);  
        //��ʼ����Կ������  
//        keyPairGenerator.initialize(KEY_SIZE);
        if(keySize%64==0){
        	keyPairGenerator.initialize(keySize);
        }else{//����ʹ��Ĭ�ϵļ��ܳ���
        	keyPairGenerator.initialize(KEY_SIZE);
        }
        //������Կ��  
        KeyPair keyPair=keyPairGenerator.generateKeyPair();  
        //�׷���Կ  
        RSAPublicKey publicKey=(RSAPublicKey) keyPair.getPublic();  
        //�׷�˽Կ  
        RSAPrivateKey privateKey=(RSAPrivateKey) keyPair.getPrivate();//����Կ�洢��map��  
        Map<String,Object> keyMap=new HashMap<String,Object>();  
        keyMap.put(PUBLIC_KEY, publicKey);  
        keyMap.put(PRIVATE_KEY, privateKey);  
        return keyMap;  
    }  
    /** 
     * ˽Կ���� 
     * @param data���������� 
     * @param key ��Կ 
     * @return byte[] �������� 
     * */  
    public static byte[] encryptByPrivateKey(byte[] data,byte[] key) throws Exception{  
        //ȡ��˽Կ  
        PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(key);         
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);  
        //����˽Կ  
        PrivateKey privateKey=keyFactory.generatePrivate(pkcs8KeySpec);        
        //���ݼ���  
        Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);  
        return cipher.doFinal(data);  
    }  
    /** 
     * ��Կ���� 
     * @param data���������� 
     * @param key ��Կ 
     * @return byte[] �������� 
     * */  
    public static byte[] encryptByPublicKey(byte[] data,byte[] key) throws Exception{ 
        //ʵ������Կ����  
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);  
        //��ʼ����Կ  
        //��Կ����ת��  
        X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(key);  
        //������Կ  
        PublicKey pubKey=keyFactory.generatePublic(x509KeySpec);  
        //���ݼ���  
        Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);  
        return cipher.doFinal(data);  
    }  
    /** 
     * ˽Կ���� 
     * @param data ���������� 
     * @param key ��Կ 
     * @return byte[] ��������
     * */  
    public static byte[] decryptByPrivateKey(byte[] data,byte[] key) throws Exception{  
        //ȡ��˽Կ  
        PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);  
        //����˽Կ  
        PrivateKey privateKey=keyFactory.generatePrivate(pkcs8KeySpec);
        //���ݽ���  
        Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
        return cipher.doFinal(data);  
    }  
    /** 
     * ��Կ���� 
     * @param data ���������� 
     * @param key ��Կ 
     * @return byte[] �������� 
     * */  
    public static byte[] decryptByPublicKey(byte[] data,byte[] key) throws Exception{  
        //ʵ������Կ����  
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);  
        //��ʼ����Կ  
        //��Կ����ת��  
        X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(key);  
        //������Կ  
        PublicKey pubKey=keyFactory.generatePublic(x509KeySpec);  
        //���ݽ���  
        Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.DECRYPT_MODE, pubKey);  
        return cipher.doFinal(data);  
    }  
    /** 
     * ȡ��˽Կ 
     * @param keyMap ��Կmap 
     * @return byte[] ˽Կ 
     * */  
    public static byte[] getPrivateKey(Map<String,Object> keyMap){  
        Key key=(Key)keyMap.get(PRIVATE_KEY);  
        return key.getEncoded();  
    }  
    /** 
     * ȡ�ù�Կ 
     * @param keyMap ��Կmap 
     * @return byte[] ��Կ 
     * */  
    public static byte[] getPublicKey(Map<String,Object> keyMap) throws Exception{  
        Key key=(Key) keyMap.get(PUBLIC_KEY);  
        return key.getEncoded();  
    }  
    /** 
     * @param args 
     * @throws Exception  
     */  
    public static void main(String[] args) throws Exception {  
        //��ʼ����Կ  
        //������Կ��  
        Map<String,Object> keyMap=RSACoder.initKey(KEY_SIZE);  
        //��Կ  
        byte[] publicKey=RSACoder.getPublicKey(keyMap);  
        //˽Կ  
        byte[] privateKey=RSACoder.getPrivateKey(keyMap);  
        System.out.println("��Կ��"+Base64.encodeBase64String(publicKey));  
        String strPublicKey=Base64.encodeBase64String(publicKey);
        String strPrivateKey = Base64.encodeBase64String(privateKey);
        System.out.println("˽Կ��"+Base64.encodeBase64String(privateKey));  
        System.out.println("================��Կ�Թ������,�׷�����Կ�������ҷ�����ʼ���м������ݵĴ���=============");  
        String str="RSA���뽻���㷨";  
        System.out.println("/n===========�׷����ҷ����ͼ�������==============");  
        System.out.println("ԭ��:"+str);
        privateKey=Base64.decodeBase64(strPrivateKey);
        //�׷��������ݵļ���  
        byte[] code1=RSACoder.encryptByPrivateKey(str.getBytes(), privateKey); 
        System.out.println("���ܺ�����ݣ�"+Base64.encodeBase64String(code1));  
        System.out.println("===========�ҷ�ʹ�ü׷��ṩ�Ĺ�Կ�����ݽ��н���==============");  
        //�ҷ��������ݵĽ���  
        publicKey=Base64.decodeBase64(strPublicKey);
        byte[] decode1=RSACoder.decryptByPublicKey(code1, publicKey);  
        System.out.println("�ҷ����ܺ�����ݣ�"+new String(decode1)+"/n/n");  
        System.out.println("===========������в������ҷ���׷���������==============/n/n");  
        str="�ҷ���׷���������RSA�㷨";  
        System.out.println("ԭ��:"+str);  
        //�ҷ�ʹ�ù�Կ�����ݽ��м���  
        byte[] code2=RSACoder.encryptByPublicKey(str.getBytes(), publicKey);  
        System.out.println("===========�ҷ�ʹ�ù�Կ�����ݽ��м���==============");  
        System.out.println("���ܺ�����ݣ�"+Base64.encodeBase64String(code2));  
        System.out.println("=============�ҷ������ݴ��͸��׷�======================");  
        System.out.println("===========�׷�ʹ��˽Կ�����ݽ��н���==============");  
        //�׷�ʹ��˽Կ�����ݽ��н���  
        byte[] decode2=RSACoder.decryptByPrivateKey(code2, privateKey);
        System.out.println("�׷����ܺ�����ݣ�"+new String(decode2));  
    }  
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
