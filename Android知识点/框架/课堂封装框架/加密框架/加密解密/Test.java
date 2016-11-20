package cn.hxxd.utils;

public class Test {
	// 公钥
	static String strPublicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALQKqzNkSPtLgDkbejM4RioemyNZtZAIB0PHuS8ue1NIp9GgYqkr4EsnVHJApy04hlicDOHJWW5nL4WcZxKbJ/UCAwEAAQ==";
	// 私钥
	static String strPrivateKey = "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAtAqrM2RI+0uAORt6MzhGKh6bI1m1kAgHQ8e5Ly57U0in0aBiqSvgSydUckCnLTiGWJwM4clZbmcvhZxnEpsn9QIDAQABAkAO8ofOJ3hLrLQDib12fSiOHKZio/MH9zKrNOyx9xj0DANd+VuMr7qPHNvh7v+sq9PNlEsv/kFOeTI6RgaB8VytAiEA63Q7EBz0uSnPeH5l7/rnd0y+cQJfoqvROhz2iR0nmlsCIQDDwJcrxruWaU9v+29dlNz2s8h+jATrF7kSdrQ6RdG37wIgGfmiom5vWRYRu1tGZ6koa/Ldd54pasrFtEGJuSqsOc0CIHbwMen7WkdNvWKBF2144blUC2I4mVRumZjKXz945LApAiAuqGYZHD1m5L9nIxfywnSa+EpGnll4Ovs/EDeQkthaDQ==";
	//解密密文
	static String values = "f1OQ+2GxbQLb8+iA/8QexC71m2y4FkH0cfEibCJAlecjgvRToa+UTUkoYe3Vh5nUQ8y+bH8r/TkBikDzgie0vg==";

	public static void main(String[] args) {
		long l = System.currentTimeMillis();
		String code = RSAClientUtils.getEncryptStr("胡周", strPublicKey);
		System.out.println(
				"加密文件=" + code + 
				"\n秘钥长度=" + strPrivateKey.length() + 
				"\n加密时间=" + (System.currentTimeMillis() - l));
		
		l = System.currentTimeMillis();
		String code1 = RSAServerUtils.getDecryptStr(values, strPrivateKey);
		System.out.println("加密文件长度=" + values.length() + 
				"\n解密文件=" + code1 + 
				"\n公钥长度=" + strPublicKey.length()
				+"\n解密时间="+(System.currentTimeMillis()-l));		

	}

}
