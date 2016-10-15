package cn.ucai.day07_02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    UserDao mDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onCreateDatabase(View view) {
        mDao = new UserDao(this, 1);
    }

    public void onQueryAll(View v) {
        final ArrayList<UserBean> list = mDao.queryAll();
        for (UserBean user : list) {
            Log.i("main", user.toString());
        }
    }

    public void onQueryByName(View view) {
        final ArrayList<UserBean> list = mDao.queryByName("菲");
        for (UserBean user : list) {
            Log.i("main", user.toString());
        }
    }

    public void onDelData(View view) {

    }

    public void onUpdateData(View view) {

    }

}
