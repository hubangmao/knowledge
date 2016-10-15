package cn.ucai.day8_03looper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ProgressBar mBar;
    TextView mtvProgress;

    Handler mMainThreadHander;
    Handler mWorkThreadHandler;

    private static final int DOWNLOAD_START=0;
    private static final int DOWNLOADING=1;
    private static final int DOWNLOAD_FINISH=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initMainThreadHandler();
        createWorkThread();
    }

    public void onClick(View view) {
        //向工作线程的消息队列发送开始下载的消息
        mWorkThreadHandler.sendEmptyMessage(DOWNLOAD_START);
    }
    /**
     * 创建用于模拟下载的工作线程
     */
    private void createWorkThread() {
        mMainThreadHander.sendEmptyMessage(DOWNLOAD_START);
        new Thread(){
            @Override
            public void run() {
                //创建为当前(工作)线程服务的Looper对象
                Looper.prepare();
                //创建为工作线程服务端Handler
                mWorkThreadHandler=new Handler(){

                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case DOWNLOAD_START:
                                for(int i=1;i<=100;i++) {
                                    SystemClock.sleep(30);
                                    Message msg2=Message.obtain();//// Message类封装消息发到堆中先进先出
                                    msg2.what=DOWNLOADING;//what 消息类型
                                    msg2.arg1=i;
                                    //消息发到主线程
                                    mMainThreadHander.sendMessage(msg2);//从消息队列里拿到消息发到主线程
                                }
                                mMainThreadHander.sendEmptyMessage(DOWNLOAD_FINISH);
                                break;
                        }
                    }
                };
                //遍历消息队列
                Looper.loop();

            }
        }.start();
    }

    private void initMainThreadHandler() {
        mMainThreadHander=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case DOWNLOAD_START:
                        Toast.makeText(MainActivity.this,"开始下载",Toast.LENGTH_SHORT).show();
                        break;
                    case DOWNLOADING:
                        mtvProgress.setText(msg.arg1 + "%");
                        mBar.setProgress(msg.arg1);
                        break;
                    case DOWNLOAD_FINISH:
                        Toast.makeText(MainActivity.this,"下载完成",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    private void initView() {
        mtvProgress = (TextView) findViewById(R.id.tvProgress);
        mBar = (ProgressBar) findViewById(R.id.pb);
    }
}
