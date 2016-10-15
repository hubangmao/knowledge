package cn.ucai.day05_03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void on(View v){
        Log.i("main", "in" + "点击");
    }
    public void on1(View v){
        Log.i("main", "in" + "hu2被点击");
    }
}
