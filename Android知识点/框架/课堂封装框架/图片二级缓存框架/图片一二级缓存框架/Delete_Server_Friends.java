package cn.ucai.test_f5.utlis;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * 删除服务端好友
 */
public class Delete_Server_Friends {
    static boolean b;

    public static boolean deleteRequest(final String user, final String deleteUser) {
        new Thread() {
            @Override
            public void run() {
                StringBuffer sb = new StringBuffer();
                sb.append(I.SERVER_URL1)
                        .append(I.REQUEST_DELETE_CONTACT + "&")
                        .append(I.Contact.USER_NAME + "=")
                        .append(user + "&")
                        .append(I.Contact.CU_NAME + "=")
                        .append(deleteUser);
                InputStream in = null;
                try {
                    URL url = new URL(sb + "");
                    Log.i("main", "删除好友Url检查" + sb.toString());
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.connect();
                    in = con.getInputStream();
                    byte[] bArr = new byte[1024 * 4];
                    int len;
                    while ((len = in.read(bArr)) != -1) {
                        sb.append(new String(bArr, 0, len));
                    }
                    Log.i("main", "服务端响应消息" + sb.toString());
                    b = true;
                } catch (ProtocolException e) {
                    b = false;
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (in != null) {
                            in.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        return b;
    }
}
