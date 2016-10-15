package cn.ucai.day08_02handler;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView mtvProgress;
    TextView mtvProgress1;
    /**
     * 主线程和工作线程之间收发消息
     */
    Handler mHanlder;
    Handler mHanlder1;
    static final int DOWNLOAD_START = 0;
    static final int DOWNLOADING = 1;
    static final int DOWNLOAD_FINISH = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initHandler();

    }

    private void initHandler() {
        mtvProgress = (TextView) findViewById(R.id.tvProgress);
        mtvProgress1 = (TextView) findViewById(R.id.tvProgress1);
        /**第一步new 一个handler*/

        mHanlder = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case DOWNLOAD_START:
                        Toast.makeText(MainActivity.this, "下载开始", Toast.LENGTH_LONG).show();
                        break;
                    case DOWNLOADING:
                        mtvProgress.setText(msg.arg1 + "%");
                        break;
                    case DOWNLOAD_FINISH:
                        Toast.makeText(MainActivity.this, "下载结束", Toast.LENGTH_LONG).show();
                        break;
                }

            }
        };


    }
//第一种方法
    public void onClick(View view) {

        new Thread() {
            @Override
            public void run() {
                mHanlder.sendEmptyMessage(DOWNLOAD_FINISH);
                for (int i = 11; i < 101; i++) {
                    SystemClock.sleep(30);
                    Message msg = Message.obtain();
                    msg.what = DOWNLOADING;
                    msg.arg1 = i;
                    mHanlder.sendMessage(msg);
                }
                mHanlder.sendEmptyMessage(DOWNLOAD_FINISH);
            }
        }.start();
    }
//第二种方法
    public void onClick1(View view) {
        mHanlder1   = new Handler();
        new Thread() {
            int i;
            @Override
            public void run() {
                /*
                像主线程发消息2
                 */
                mtvProgress1.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "下载开始", Toast.LENGTH_LONG).show();
                    }
                });
                mHanlder.sendEmptyMessage(DOWNLOAD_FINISH);
                for (i = 11; i < 101; i++) {
                    mHanlder1.post(new Runnable() {
                        @Override
                        public void run() {
                            SystemClock.sleep(20);
                            mtvProgress1.setText(i + "%");

                        }
                    });


                }
                mHanlder1.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "下载结束", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }.start();
        Toast.makeText(MainActivity.this, "邦哥研究", Toast.LENGTH_LONG).show();

    }
}
