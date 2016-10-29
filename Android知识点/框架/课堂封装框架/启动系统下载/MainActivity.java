package cn.ucai.test2;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;

/**
 * 模拟QQ下载安装
 */
public class MainActivity extends AppCompatActivity {
    private final String URL = "https://www.baidu.com/link?url=zZmL6Uf3wPzjOTRz0cS90anwVxDrzJe0AN6jfqmsj1-" +
            "TsMTImTfRXFYEyQaxNAc7INMEDi04BQoObCsUPDlYJe8Cavk_P6NRItxWCuxmdpq&wd" +
            "=&eqid=ad390bcc000aa5220000000558118aae";
    private BroadcastReceiver mBroadcastReceiver;
    private AlertDialog.Builder mBuilderDialog;
    private File mFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //调用系统下载
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_start_download:
                if (!Utils.isNetworkAvailable(this)) {
                    Utils.toast(this, "当前网络不可用");
                    return;
                }
                if (Utils.isWiFi(this)) {
                    SystemDownloadWiFi();
                    return;
                }
                if (Utils.isMobile(this)) {
                    isDownload();
                    SystemDownloadWiFi();
                }
                break;
            case R.id.but_is_download:
                Utils.toast(this, "正在使用流量下载");
                Utils.dismiss(mAlertDialog);
                SystemDownload();
                break;
            case R.id.but_no_download:
                Utils.toast(this, "已经取消移动网络环境下载");
                Utils.dismiss(mAlertDialog);
                break;
        }


    }

    //调用系统下载管理下载WiFi
    private void SystemDownloadWiFi() {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(URL));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "QQ.apk");

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("QQ");
        request.setDescription("QQ v5.6.6.3更新中");
        // 下载过程和下载完成后通知栏有通知消息。
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE | DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        DownloadManager downloadManager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
        long l = downloadManager.enqueue(request);
        listener(l);
    }

    //调用系统下载管理下载移动流量
    private void SystemDownload() {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(URL));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "QQ.apk");

        request.setTitle("QQ");
        request.setDescription("QQ v5.6.6.3更新中");
        // 下载过程和下载完成后通知栏有通知消息。
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE | DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        DownloadManager downloadManager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
        long l = downloadManager.enqueue(request);
        listener(l);
    }

    private void listener(final long Id) {
        // 注册广播监听系统的下载完成事件。
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long ID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (ID == Id) {
                    Toast.makeText(getApplicationContext(), "任务:" + Id + " 下载完成!", Toast.LENGTH_SHORT).show();
                    install();
                }
            }
        };
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    //下载完启动安装
    private void install() {
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(file + Environment.DIRECTORY_DOWNLOADS, "QQ.apk")), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
        }
    }

    public void isDownload() {
        mBuilderDialog = new AlertDialog.Builder(this);
        mBuilderDialog.setView(View.inflate(this, R.layout.dialog_layout, null));
        mAlertDialog = mBuilderDialog.create();
        mAlertDialog.show();
    }
}
