package cn.ucai.test2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * 模拟QQ下载安装
 */
public class MainActivity extends AppCompatActivity {
    private ImageView mImageView;
    OnSetAvatarListener mOnSetAvatarListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.iv_test);
    }

    //调用系统下载
    public void onClick(View v) {
        mOnSetAvatarListener = new OnSetAvatarListener(this, R.id.relative, "fileName");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        mOnSetAvatarListener.setAvatar(requestCode, data, mImageView);
    }
}
