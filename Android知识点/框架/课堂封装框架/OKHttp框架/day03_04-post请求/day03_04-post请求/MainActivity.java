package cn.ucai.day03_03_uploadfile;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import cn.ucai.day03_03_uploadfile.bean.MessageBean;
import cn.ucai.day03_03_uploadfile.bean.UserBean;
import cn.ucai.day03_03_uploadfile.utils.I;
import cn.ucai.day03_03_uploadfile.utils.OkHttpUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onUploadFile(View view) {
        //找到sd卡的pictures文件夹
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File file = new File(dir, "yaowei.jpg");
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>();
        utils.url(I.ROOT_SERVER)
                .addParam(I.KEY_REQUEST, I.REQUEST_UPLOAD_AVATAR)
                .addParam(I.User.USER_NAME, "yaowei")
                .addParam(I.Avatar.AVATAR_TYPE, "user_avatar")
                .addParam(I.Avatar.FILE_NAME, "yaowei.jpg")
                .addFile(file)
                .targetClass(MessageBean.class)
                .execute(new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        Toast.makeText(MainActivity.this, "上传头像成功", Toast.LENGTH_SHORT).show();
                        Log.i("main", result.toString());
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onLogin(View view) {
        OkHttpUtils<UserBean> utils=new OkHttpUtils<>();
        utils.url(I.ROOT_SERVER)
                .post()
                .addParam(I.KEY_REQUEST,I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME,"a")
                .addParam(I.User.PASSWORD,"a")
                .targetClass(UserBean.class)
                .execute(new OkHttpUtils.OnCompleteListener<UserBean>() {
                    @Override
                    public void onSuccess(UserBean result) {
                        Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(MainActivity.this, error , Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
