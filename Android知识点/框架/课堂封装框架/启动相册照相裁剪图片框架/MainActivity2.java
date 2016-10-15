package cn.ucai.day05_05;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.io.File;

import cn.ucai.day05_05.listener.OnGetAvatarListener;

public class MainActivity2 extends AppCompatActivity {
    OnGetAvatarListener monGetAvatarListener;
    ImageView mivAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mivAvatar = (ImageView) findViewById(R.id.ivAvatar);
    }

    public void onClick(View view) {
        View parent = findViewById(R.id.layout_parent);
        //启动裁剪
        monGetAvatarListener = new OnGetAvatarListener(this, parent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        monGetAvatarListener.setAvatar(requestCode, data, mivAvatar);
    }

}
