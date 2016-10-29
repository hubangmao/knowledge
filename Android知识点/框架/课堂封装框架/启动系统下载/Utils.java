package cn.ucai.test2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/10/27.
 */

public class Utils {
    //判断网络连接是否可用
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    //判断WiFi是否打开
    public static boolean isWiFi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    //判断移动数据是否打开
    public static boolean isMobile(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    private static Toast toast;

    public static void toast(final Context context, String string) {
        if (toast == null) {
            toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
        } else {
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(string);
        }
        toast.show();
    }

    public static void dismiss(AlertDialog alertDialog) {
        if (!isNull(alertDialog)) {
            alertDialog.dismiss();
        }
    }

    public static boolean isNull(Object object) {
        if (object == null) {
            return true;
        } else {
            return false;
        }
    }
}
